package lk.ijse.ormsmhtc.bo.custom.impl;



import lk.ijse.ormsmhtc.dao.custom.impl.TherapistProgramDAOImpl;
import lk.ijse.ormsmhtc.dto.Custom;
import lk.ijse.ormsmhtc.dto.TherapistProgramDto;
import lk.ijse.ormsmhtc.entity.TherapistProgram;
import lk.ijse.ormsmhtc.entity.TherapistProgramId;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TherapistProgramBOImpl {
    TherapistProgramDAOImpl therapistProgramDAO = new TherapistProgramDAOImpl();

    public boolean saveTherapistProgram(String programId, String therapistId, String day, LocalTime startTime, LocalTime endTime) {
//        TherapyProgram therapyProgram = new TherapyProgram();
//        therapyProgram.setId(therapistProgramDto.getProgramId());
//        Therapist therapist = new Therapist();
//        therapist.setId(therapistProgramDto.getTherapistId());
        TherapistProgramId therapistProgramId = new TherapistProgramId(therapistId,programId);
        return therapistProgramDAO.save(therapistProgramId,therapistId,programId,day,startTime,endTime);
    }

    public ArrayList<TherapistProgramDto> getAllTherapistProgram() {
        List<TherapistProgram> therapistPrograms = therapistProgramDAO.getAll();
        ArrayList<TherapistProgramDto> therapistProgramDtos = new ArrayList<>();
        for (TherapistProgram therapistProgram: therapistPrograms){
            TherapistProgramDto therapistProgramDto = new TherapistProgramDto(
                    therapistProgram.getProgramId().getId(),
                    therapistProgram.getTherapistId().getId(),
                    therapistProgram.getDay(),
                    therapistProgram.getStartTime(),
                    therapistProgram.getEndTime()
            );
            therapistProgramDtos.add(therapistProgramDto);
        }
        return therapistProgramDtos;
    }

    public boolean updateTherapistProgram(String programId, String therapistId, String day, LocalTime startTime, LocalTime endTime) {
        TherapistProgramId therapistProgramId = new TherapistProgramId(therapistId,programId);
        return therapistProgramDAO.update(therapistProgramId,therapistId,programId,day,startTime,endTime);
    }

    public boolean deleteTherapistProgram(String programId, String therapistId) {
        TherapistProgramId therapistProgramId = new TherapistProgramId(therapistId,programId);
        return therapistProgramDAO.delete(therapistProgramId);
    }

    public ArrayList<String> getTherapistId(String programId) {
        ArrayList<TherapistProgram> therapistIds = therapistProgramDAO.getAllByID(programId);
        ArrayList<String> therapyID = new ArrayList<>();
        for (TherapistProgram therapyProgram : therapistIds){
            therapyID.add(therapyProgram.getTherapistId().getId());
        }
        return therapyID;
    }

    public ArrayList<Custom> getAvailableTime(String therapistID) {
        List<TherapistProgram> programList = therapistProgramDAO.getDataWiseTherapist(therapistID);
        ArrayList<Custom> results = new ArrayList<>();

        // Define the full day time range
        LocalTime startOfDay = LocalTime.of(8, 0); // 8:00 AM
        LocalTime endOfDay = LocalTime.of(23, 59); // 11:59 PM

        // Create a list to hold the booked time slots
        List<Custom> bookedSlots = new ArrayList<>();

        // Populate booked time slots from the program list
        for (TherapistProgram program : programList) {
            LocalTime startTime = program.getStartTime();
            LocalTime endTime = program.getEndTime();
            String day = program.getDay(); // Assuming there's a method to get the day

            bookedSlots.add(new Custom(startTime, endTime, day));
        }

        // Check for each day of the week (assuming you want to check all days)
        for (String day : getAllDaysOfWeek()) {
            // Filter booked slots for the current day
            List<Custom> bookedSlotsForDay = new ArrayList<>();
            for (Custom booked : bookedSlots) {
                if (booked.getDay().equals(day)) {
                    bookedSlotsForDay.add(booked);
                }
            }

            // Find available slots for the day
            List<Custom> availableSlots = findAvailableSlots(startOfDay, endOfDay, bookedSlotsForDay);

            // If no available slots, add the full day as an available slot
            if (availableSlots.isEmpty()) {
                availableSlots.add(new Custom(startOfDay, endOfDay, day));
            }

            // Add available slots to results
            for (Custom slot : availableSlots) {
                results.add(new Custom(day, slot.toString()));
            }
        }

        return results;
    }

    private List<Custom> findAvailableSlots(LocalTime startOfDay, LocalTime endOfDay, List<Custom> bookedSlots) {
        List<Custom> availableSlots = new ArrayList<>();
        LocalTime currentStart = startOfDay;

        // Sort booked slots by start time
        bookedSlots.sort((a, b) -> a.getStart().compareTo(b.getStart()));

        for (Custom booked : bookedSlots) {
            // If there's a gap between the current start and the booked start, add it as an available slot
            if (currentStart.isBefore(booked.getStart())) {
                availableSlots.add(new Custom(currentStart, booked.getStart()));
            }
            // Move the current start to the end of the booked slot if it overlaps or is adjacent
            currentStart = booked.getEnd().isAfter(currentStart) ? booked.getEnd() : currentStart;
        }

        // Check if there's time left after the last booked slot
        if (currentStart.isBefore(endOfDay)) {
            availableSlots.add(new Custom(currentStart, endOfDay));
        }

        return availableSlots;
    }

    private List<String> getAllDaysOfWeek() {
        return List.of("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
    }

}
