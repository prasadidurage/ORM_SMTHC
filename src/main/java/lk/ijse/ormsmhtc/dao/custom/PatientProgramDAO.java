package lk.ijse.ormsmhtc.dao.custom;


import lk.ijse.ormsmhtc.dao.CrudDAO;
import lk.ijse.ormsmhtc.entity.PatientProgram;
import lk.ijse.ormsmhtc.entity.PatientProgramId;

import java.util.ArrayList;
import java.util.Date;

public interface PatientProgramDAO extends CrudDAO<PatientProgram> {
    public boolean save(PatientProgramId patientProgramId, String patientId, String programId, Date registerDate, String paymentId);
    public boolean update(PatientProgramId patientProgramId, String patientId, String programId, Date registerDate, String paymentId);
    public ArrayList<PatientProgram> getAllByID(String patientId);
}
