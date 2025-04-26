package lk.ijse.ormsmhtc.bo.custom;

import lk.ijse.ormsmhtc.bo.SuperBO;
import lk.ijse.ormsmhtc.dto.CustomDto;
import lk.ijse.ormsmhtc.dto.TherapySessionDTO;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public interface TherapySessionBO extends SuperBO {
    public ArrayList<TherapySessionDTO> getAll();
    public String getNextSessionId();
    public ArrayList<CustomDto> getAvailableTime(String programId, String therapistId);
     ArrayList<CustomDto> getAllDatesForDayOfMonth(String day, String timeSlot, ArrayList<CustomDto> timeAndDate);
     List<Time[]> adjustFreeSlots(List<Time[]> freeSlots, Time sessionStart, Time sessionEnd);
    public String formatTime(Time time);
    public boolean saveTherapySession(TherapySessionDTO therapySessionDTO);    public boolean deleteSession(String sessionId);
    public ArrayList<String> getAllId();
    public ArrayList<CustomDto> getTherapyPerformance(String therapistId);
    public ArrayList<CustomDto> getSessionStatistic(String programId);
}
