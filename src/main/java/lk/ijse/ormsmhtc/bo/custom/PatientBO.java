package lk.ijse.ormsmhtc.bo.custom;



import lk.ijse.ormsmhtc.bo.SuperBO;
import lk.ijse.ormsmhtc.dto.PatientDto;
import lk.ijse.ormsmhtc.dto.TherapySessionDTO;

import java.util.ArrayList;

public interface PatientBO extends SuperBO {
    public boolean savePatient(PatientDto patientDto);
    public ArrayList<PatientDto> getAllPatient();
    public String getNextId();
    public boolean updatePatient(PatientDto patientDto);
    public boolean deletePatient(String patientId);
    public ArrayList<String> getAllPatientId();
    public ArrayList<TherapySessionDTO> getAllById(String patientId);
}
