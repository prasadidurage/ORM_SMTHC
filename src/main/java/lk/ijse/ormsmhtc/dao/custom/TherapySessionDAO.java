package lk.ijse.ormsmhtc.dao.custom;


import lk.ijse.ormsmhtc.dao.CrudDAO;
import lk.ijse.ormsmhtc.entity.Custom;
import lk.ijse.ormsmhtc.entity.TherapySession;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public interface TherapySessionDAO extends CrudDAO<TherapySession> {
    public Object getAllByTherapistId(String therapistId);
    public boolean save(String id, Date date, Time startTime, Time endTime, String therapistId, String patientId, String therapyProgramID);
    public boolean update(String id, String patientId, String therapistId, String therapyProgramID, Date date, Time startTime, Time endTime);
    public ArrayList<Custom> getTherapyPerformance(String therapistId);
    public ArrayList<Custom> getSessionStatistic(String programId);
    public ArrayList<TherapySession> getAllByIdPatientId(String patientId);
}
