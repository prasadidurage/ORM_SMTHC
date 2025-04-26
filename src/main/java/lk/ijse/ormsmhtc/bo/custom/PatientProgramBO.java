package lk.ijse.ormsmhtc.bo.custom;


import lk.ijse.ormsmhtc.bo.SuperBO;
import lk.ijse.ormsmhtc.dto.PatientProgramDto;
import lk.ijse.ormsmhtc.dto.PaymentDto;

import java.util.ArrayList;

public interface PatientProgramBO extends SuperBO {
    public ArrayList<PatientProgramDto> getAllPatientProgram();
    public boolean savePatientProgram(PatientProgramDto patientProgramDto, PaymentDto paymentDto);
    public boolean savePayment(PaymentDto paymentDto);
    public boolean updatePatientProgram(PatientProgramDto patientProgramDto, PaymentDto paymentDto);
    public boolean updatePayment(PaymentDto paymentDto);
    public boolean deletePatientProgram(String programId, String patientId, String paymentId);
    public ArrayList<String> getProgramsIdByPatient(String patientId);
}
