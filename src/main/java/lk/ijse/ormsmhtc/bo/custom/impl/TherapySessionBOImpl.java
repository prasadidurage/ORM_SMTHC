package lk.ijse.ormsmhtc.bo.custom.impl;



import lk.ijse.ormsmhtc.dao.custom.impl.TherapistProgramDAOImpl;
import lk.ijse.ormsmhtc.dao.custom.impl.TherapySessionDAOImpl;
import lk.ijse.ormsmhtc.dto.Custom;
import lk.ijse.ormsmhtc.dto.TherapySessionDTO;
import lk.ijse.ormsmhtc.entity.TherapistProgram;
import lk.ijse.ormsmhtc.entity.TherapySession;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TherapySessionBOImpl {
    TherapySessionDAOImpl therapySessionDAO = new TherapySessionDAOImpl();
    TherapistProgramDAOImpl therapistProgramDAO = new TherapistProgramDAOImpl();

    public ArrayList<TherapySessionDTO> getAll() {
        ArrayList<TherapySession> therapySessions = therapySessionDAO.getAll();
        ArrayList<TherapySessionDTO> therapySessionDTOS = new ArrayList<>();
        for (TherapySession therapySession : therapySessions) {
            TherapySessionDTO therapySessionDTO = new TherapySessionDTO(
                    therapySession.getId(),
                    therapySession.getDate(),
                    therapySession.getStartTime(),
                    therapySession.getEndTime(),
                    therapySession.getTherapist().getId(),
                    therapySession.getPatient().getId(),
                    therapySession.getTherapyProgram().getId()
            );
            therapySessionDTOS.add(therapySessionDTO);
        }
        return therapySessionDTOS;
    }

    public String getNextSessionId() {
        String lastId = therapySessionDAO.getLastId();
        if (lastId != null) {
            String subString = lastId.substring(2);
            int lastIndex = Integer.parseInt(subString);
            int nextIndex = lastIndex + 1;
            return String.format("TS%03d", nextIndex);
        }
        return "TS001";
    }

    public ArrayList<Custom> getAvailableTime(String programId, String therapistId) {
        ArrayList<TherapistProgram> therapistPrograms = (ArrayList<TherapistProgram>) therapistProgramDAO.getAllByIDS(programId, therapistId);
        ArrayList<TherapySession> therapySessions = (ArrayList<TherapySession>) therapySessionDAO.getAllByTherapistId(therapistId);
        ArrayList<Custom> freeTimeSlots = new ArrayList<>();

        ArrayList<Custom> availableTime = new ArrayList<>();
        for (TherapistProgram therapistProgram : therapistPrograms) {
            Time start = Time.valueOf(therapistProgram.getStartTime());
            Time end = Time.valueOf(therapistProgram.getEndTime());

            String formattedStartTime = start.toString().substring(0, 5);
            String formattedEndTime = end.toString().substring(0, 5);
            String timeSlot = formattedStartTime + " - " + formattedEndTime;

            String day = therapistProgram.getDay();
            availableTime.add(new Custom(day,timeSlot));
        }

        ArrayList<Custom> sessionTimes = new ArrayList<>();
        for (TherapySession therapySession : therapySessions) {
            Time start = therapySession.getStartTime();
            Time end = therapySession.getEndTime();

            String formattedStartTime = start.toString().substring(0, 5);
            String formattedEndTime = end.toString().substring(0, 5);
            String timeSlot = formattedStartTime + " - " + formattedEndTime;

            Date date = new Date(therapySession.getDate().getTime());
            sessionTimes.add(new Custom(timeSlot, date));
        }

        ArrayList<Custom> timeAndDate = new ArrayList<>();
        for (Custom custom : availableTime) {
            String day = custom.getDay();
            String timeSlot = custom.getAvailableTimeList();
            timeAndDate = getAllDatesForDayOfMonth(day, timeSlot, timeAndDate);
        }
        for (Custom custom : timeAndDate) {
            String startTime = custom.getTime().split(" - ")[0].trim() + ":00";
            String endTime = custom.getTime().split(" - ")[1].trim() + ":00";

            Time fullStartTime = Time.valueOf(startTime); // e.g., "10:00:00"
            Time fullEndTime = Time.valueOf(endTime);

            List<Time[]> freeSlots = new ArrayList<>();
            freeSlots.add(new Time[]{fullStartTime, fullEndTime});

            for (TherapySession therapySession : therapySessions) {
                Date sessionDate = new Date(therapySession.getDate().getTime());
                if (custom.getDate().equals(sessionDate)) {
                    Time sessionStart = therapySession.getStartTime();
                    Time sessionEnd = therapySession.getEndTime();
                    freeSlots = adjustFreeSlots(freeSlots, sessionStart, sessionEnd);
                }
            }

            for (Time[] slot : freeSlots) {
                String freeTimeSlot = formatTime(slot[0]) + " - " + formatTime(slot[1]);
                freeTimeSlots.add(new Custom(custom.getDate(), freeTimeSlot));
            }
        }
        return freeTimeSlots;
    }

    private ArrayList<Custom> getAllDatesForDayOfMonth(String day, String timeSlot, ArrayList<Custom> timeAndDate) {
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfMonth = today.withDayOfMonth(1);
        LocalDate lastDayOfMonth = firstDayOfMonth.withDayOfMonth(firstDayOfMonth.lengthOfMonth());

        for (LocalDate date = firstDayOfMonth; !date.isAfter(lastDayOfMonth); date = date.plusDays(1)) {
            if (date.getDayOfWeek().name().equalsIgnoreCase(day.toUpperCase())) {
                timeAndDate.add(new Custom(Date.valueOf(date), timeSlot));
            }
        }
        return timeAndDate;
    }

    private static List<Time[]> adjustFreeSlots(List<Time[]> freeSlots, Time sessionStart, Time sessionEnd) {
        List<Time[]> adjustedSlots = new ArrayList<>();

        for (Time[] slot : freeSlots) {
            Time freeStart = slot[0];
            Time freeEnd = slot[1];

            if (sessionStart.after(freeEnd) || sessionEnd.before(freeStart)) {
                adjustedSlots.add(slot);
            } else {
                if (freeStart.before(sessionStart)) {
                    adjustedSlots.add(new Time[]{freeStart, sessionStart}); // Free time before the session
                }
                if (freeEnd.after(sessionEnd)) {
                    adjustedSlots.add(new Time[]{sessionEnd, freeEnd}); // Free time after the session
                }
            }
        }

        return adjustedSlots;
    }

    private static String formatTime(Time time) {
        // Convert Time to String in "hh:mm a" format
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        return sdf.format(time);
    }

    public boolean saveTherapySession(TherapySessionDTO therapySessionDTO) {
        TherapySession therapySession = new TherapySession(

        );
        return therapySessionDAO.save(therapySessionDTO.getId(),
                therapySessionDTO.getDate(),
                therapySessionDTO.getStartTime(),
                therapySessionDTO.getEndTime(),therapySessionDTO.getTherapistId(),
                therapySessionDTO.getPatientId(),
                therapySessionDTO.getTherapyProgramID());
    }

    public boolean updateTherapySession(TherapySessionDTO therapySessionDTO) {
        return therapySessionDAO.update(therapySessionDTO.getId(),
                therapySessionDTO.getPatientId(),
                therapySessionDTO.getTherapistId(),
                therapySessionDTO.getTherapyProgramID(),
                therapySessionDTO.getDate(),
                therapySessionDTO.getStartTime(),
                therapySessionDTO.getEndTime());
    }

    public boolean deleteSession(String sessionId) {
        return therapySessionDAO.delete(sessionId);
    }

    public ArrayList<String> getAllId() {
        ArrayList<TherapySessionDTO> therapySessions = getAll();
        ArrayList<String> patientIds = new ArrayList<>();
        for (TherapySessionDTO therapySession: therapySessions){
            patientIds.add(therapySession.getId());
        }
        return patientIds;
    }
}



//    public ArrayList<Custom> getAvailableTime(String programId, String therapistId) {
//        ArrayList<TherapistProgram> therapistPrograms = (ArrayList<TherapistProgram>) therapistProgramDAO.getAllByIDS(programId, therapistId);
//        ArrayList<TherapySession> therapySessions = (ArrayList<TherapySession>) therapySessionDAO.getAllByTherapistId(therapistId);
//        ArrayList<Custom> freeTimeSlots = new ArrayList<>();
//
//        // Create a calendar instance to iterate through the next 28 days
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(new Date());
//
//        for (int i = 0; i < 28; i++) {
//            Date currentDate = calendar.getTime();
//            calendar.add(Calendar.DAY_OF_MONTH, 1); // Move to the next day
//
//            // Check if there are therapy sessions on this date
//            boolean hasSession = therapySessions.stream()
//                    .anyMatch(session -> isSameDay(session.getStartTime(), currentDate));
//
//            if (hasSession) {
//                // If there are sessions, store the full working hours
//                freeTimeSlots.add(new Custom(String.valueOf(currentDate), "09:00 - 17:00")); // Assuming working hours
//            } else {
//                // Check for free time slots based on therapist programs
//                for (TherapistProgram program : therapistPrograms) {
//                    Time programStartTime = Time.valueOf(program.getStartTime());
//                    Time programEndTime = Time.valueOf(program.getEndTime());
//
//                    // Check if the program is on the same day
//                    if (isSameDay(programStartTime, currentDate)) {
//                        // Check for overlapping sessions
//                        boolean isOverlapping = therapySessions.stream()
//                                .anyMatch(session -> isOverlapping(session, programStartTime, programEndTime));
//
//                        if (!isOverlapping) {
//                            // If no overlapping sessions, add the program time slot
//                            String timeSlot = formatTime(programStartTime) + " - " + formatTime(programEndTime);
//                            freeTimeSlots.add(new Custom(String.valueOf(currentDate), timeSlot));
//                        }
//                    }
//                }
//            }
//        }
//
//        return freeTimeSlots;
//    }
//
//    private boolean isSameDay(Date date1, Date date2) {
//        Calendar cal1 = Calendar.getInstance();
//        cal1.setTime(date1);
//        Calendar cal2 = Calendar.getInstance();
//        cal2.setTime(date2);
//        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
//                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
//    }
//
//    private boolean isOverlapping(TherapySession session, Time startTime, Time endTime) {
//        return (session.getStartTime().before(endTime) && session.getEndTime().after(startTime));
//    }
//
//    private String formatTime(Date date) {
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(date);
//        return String.format("%02d:%02d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));
//    }
//}
//////////////////////////////////////////////////////////////
//    public ArrayList<Custom> getAvailableTime(String programId, String therapistId) {
//        // Retrieve therapist programs and therapy sessions based on the provided IDs
//        ArrayList<TherapistProgram> therapistPrograms = (ArrayList<TherapistProgram>) therapistProgramDAO.getAllByID(programId,therapistId);
//        ArrayList<TherapySession> therapySessions = (ArrayList<TherapySession>) therapySessionDAO.getAllByTherapistId(therapistId);
//        ArrayList<Custom> freeTimeSlots = new ArrayList<>();
//
//        // Define the full day time range
//        LocalTime startOfDay = LocalTime.of(8, 0); // 8:00 AM
//        LocalTime endOfDay = LocalTime.of(23, 59); // 11:59 PM
//
//        // Create a list to hold working hours and booked slots
//        List<Custom> workingHours = new ArrayList<>();
//        List<Custom> bookedSlots = new ArrayList<>();
//
//        // Populate working hours from therapist programs
//        for (TherapistProgram program : therapistPrograms) {
//            LocalTime startTime = program.getStartTime();
//            LocalTime endTime = program.getEndTime();
//            workingHours.add(new Custom(startTime, endTime));
//        }
//
//        // Populate booked slots from therapy sessions
//        for (TherapySession session : therapySessions) {
//            LocalTime startTime = session.getStartTime().toLocalTime();
//            LocalTime endTime = session.getEndTime().toLocalTime();
//            bookedSlots.add(new Custom(startTime, endTime));
//        }
//
//        // Generate dates for the next 28 days
//        for (int i = 0; i < 28; i++) {
//            LocalDate date = LocalDate.now().plusDays(i);
//            String dateString = date.toString(); // Format date as needed
//
//            // Check if there are any booked slots for the day
//            List<Custom> availableSlots;
//            if (bookedSlots.isEmpty()) {
//                // If no sessions are booked, use the full working hours
//                for (Custom workingHour : workingHours) {
//                    availableSlots = new ArrayList<>();
//                    availableSlots.add(new Custom(workingHour.start, workingHour.end));
//                    for (Custom slot : availableSlots) {
//                        freeTimeSlots.add(new Custom(dateString, slot.toString()));
//                    }
//                }
//            } else {
//                // Find available slots for the day
//                availableSlots = findAvailableSlots(startOfDay, endOfDay, bookedSlots);
//                // Format available slots as strings and add to results
//                for (Custom slot : availableSlots) {
//                    freeTimeSlots.add(new Custom(dateString, slot.toString()));
//                }
//            }
//        }
//
//        return freeTimeSlots;
//    }
//
//    private List<Custom> findAvailableSlots(LocalTime startOfDay, LocalTime endOfDay, List<Custom> bookedSlots) {
//        List<Custom> availableSlots = new ArrayList<>();
//        LocalTime currentStart = startOfDay;
//
//        // Sort booked slots by start time
//        bookedSlots.sort((a, b) -> a.start.compareTo(b.start));
//
//        for (Custom booked : bookedSlots) {
//            // If there's a gap between the current start and the booked start, add it as an available slot
//            if (currentStart.isBefore(booked.start)) {
//                availableSlots.add(new Custom(currentStart, booked.start));
//            }
//            // Move the current start to the end of the booked slot if it overlaps or is adjacent
//            currentStart = booked.end.isAfter(currentStart) ? booked.end : currentStart;
//        }
//
//        // Check if there's time left after the last booked slot
//        if (currentStart.isBefore(endOfDay)) {
//            availableSlots.add(new Custom(currentStart, endOfDay));
//        }
//
//        return availableSlots;
//    }
//
//
//    public ArrayList<Custom> subtractTimeSlots(ArrayList<Custom> available, ArrayList<Custom> toSubtract) {
//        ArrayList<Custom> result = new ArrayList<>();
//
//        for (Custom slot : available) {
//            LocalTime pointer = slot.start;
//            for (Custom busy : toSubtract) {
//                if (busy.end.isBefore(pointer) || busy.start.isAfter(slot.end)) continue;
//
//                if (pointer.isBefore(busy.start)) {
//                    result.add(new Custom(pointer, busy.start));
//                }
//
//                if (busy.end.isAfter(pointer)) {
//                    pointer = busy.end.isAfter(slot.end) ? slot.end : busy.end;
//                }
//            }
//
//            if (pointer.isBefore(slot.end)) {
//                result.add(new Custom(pointer, slot.end));
//            }
//        }
//
//        return result;
//    }
//
//    public ArrayList<Custom> intersectTimeSlots(ArrayList<Custom> listA, ArrayList<Custom> listB) {
//        ArrayList<Custom> result = new ArrayList<>();
//
//        for (Custom a : listA) {
//            for (Custom b : listB) {
//                LocalTime start = a.start.isAfter(b.start) ? a.start : b.start;
//                LocalTime end = a.end.isBefore(b.end) ? a.end : b.end;
//
//                if (start.isBefore(end)) {
//                    result.add(new Custom(start, end));
//                }
//            }
//        }
//
//        return result;
//    }
//
//}
