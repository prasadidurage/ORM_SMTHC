package lk.ijse.ormsmhtc.dao.custom;

import lk.ijse.ormsmhtc.dao.CrudDAO;
import lk.ijse.ormsmhtc.entity.Therapist;
import lk.ijse.ormsmhtc.entity.TherapyProgram;

import java.util.List;

public interface TherapistDAO extends CrudDAO<Therapist> {
    public List<TherapyProgram> getAllId();
}
