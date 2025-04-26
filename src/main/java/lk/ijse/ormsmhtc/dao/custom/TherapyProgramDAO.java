package lk.ijse.ormsmhtc.dao.custom;


import lk.ijse.ormsmhtc.dao.CrudDAO;
import lk.ijse.ormsmhtc.entity.TherapyProgram;

import java.util.List;

public interface TherapyProgramDAO extends CrudDAO<TherapyProgram> {
    public List<TherapyProgram> getAllId();
    public double getFee(String programID);
}
