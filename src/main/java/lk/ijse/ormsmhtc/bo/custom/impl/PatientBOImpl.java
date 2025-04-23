package lk.ijse.ormsmhtc.bo.custom.impl;

import lk.ijse.ormsmhtc.bo.custom.PatientBO;

import lk.ijse.ormsmhtc.bo.custom.PatientBO;
import lk.ijse.ormsmhtc.dto.PatientDto;
import lk.ijse.ormsmhtc.entity.Patient;
import lk.ijse.ormsmhtc.dao.custom.impl.PatientDAOImpl;

import java.util.ArrayList;
import java.util.List;

public class PatientBOImpl {
    private PatientDAOImpl patientDAO = new PatientDAOImpl();

    public boolean savePatient(PatientDto patientDto) {
        Patient patient = new Patient(
                patientDto.getId(),
                patientDto.getName(),
                patientDto.getAddress(),
                patientDto.getPhone(),
                patientDto.getEmail(),
                patientDto.getHistory()
        );
        return patientDAO.save(patient);
    }

    public ArrayList<PatientDto> getAllPatient() {
        List<Patient> patients = patientDAO.getAll();
        ArrayList<PatientDto> patientDtoList = new ArrayList<PatientDto>();
        for (Patient patient : patients) {
            PatientDto patientDto = new PatientDto(
                    patient.getId(),
                    patient.getName(),
                    patient.getAddress(),
                    patient.getPhone(),
                    patient.getEmail(),
                    patient.getHistory()
            );
            patientDtoList.add(patientDto);
        }
        return patientDtoList;
    }

    public String getNextId() {
        String lastId = patientDAO.getLastId();
        if (lastId != null) {
            String subString = lastId.substring(1);
            int lastIndex = Integer.parseInt(subString);
            int nextIndex = lastIndex + 1;
            return String.format("P%03d", nextIndex);
        }
        return "P001";

    }

    public boolean updatePatient(PatientDto patientDto) {
        return patientDAO.update(new Patient(
                patientDto.getId(),
                patientDto.getName(),
                patientDto.getAddress(),
                patientDto.getPhone(),
                patientDto.getEmail(),
                patientDto.getHistory()
            ));
    }

    public boolean deletePatient(String patientId) {
        return patientDAO.delete(patientId);
    }

    public ArrayList<String> getAllPatientId() {
        ArrayList<PatientDto> patientDtos = getAllPatient();
        ArrayList<String> patientIds = new ArrayList<>();
        for (PatientDto patientDto: patientDtos){
            patientIds.add(patientDto.getId());
        }
        return patientIds;
    }
}
