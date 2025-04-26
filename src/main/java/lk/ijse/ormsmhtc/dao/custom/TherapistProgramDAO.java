package lk.ijse.ormsmhtc.dao.custom;



import lk.ijse.ormsmhtc.dao.CrudDAO;
import lk.ijse.ormsmhtc.entity.TherapistProgram;
import lk.ijse.ormsmhtc.entity.TherapistProgramId;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public interface TherapistProgramDAO extends CrudDAO<TherapistProgram> {
    public boolean save(TherapistProgramId therapistProgramId, String therapistId, String programId, String day, LocalTime startTime, LocalTime endTime);
    public boolean update(TherapistProgramId therapistProgramId, String therapistId, String programId, String day, LocalTime startTime, LocalTime endTime);
    public boolean delete(TherapistProgramId therapistProgramId);
    public List<TherapistProgram> getDataWiseTherapist(String therapistID);
    public ArrayList<TherapistProgram> getAllByID(String programId);
    public ArrayList<TherapistProgram> getAllByIDS(String programId, String therapistId);
}
