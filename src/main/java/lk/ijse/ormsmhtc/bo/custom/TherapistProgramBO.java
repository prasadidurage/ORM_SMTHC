package lk.ijse.ormsmhtc.bo.custom;


import lk.ijse.ormsmhtc.bo.SuperBO;
import lk.ijse.ormsmhtc.dto.CustomDto;
import lk.ijse.ormsmhtc.dto.TherapistProgramDto;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public interface TherapistProgramBO extends SuperBO {
    public boolean saveTherapistProgram(String programId, String therapistId, String day, LocalTime startTime, LocalTime endTime);
    public ArrayList<TherapistProgramDto> getAllTherapistProgram();
    public boolean updateTherapistProgram(String programId, String therapistId, String day, LocalTime startTime, LocalTime endTime);
    public boolean deleteTherapistProgram(String programId, String therapistId);
    public ArrayList<String> getTherapistId(String programId);
    public ArrayList<CustomDto> getAvailableTime(String therapistID);
    List<CustomDto> findAvailableSlots(LocalTime startOfDay, LocalTime endOfDay, List<CustomDto> bookedSlots);
    List<String> getAllDaysOfWeek();
}
