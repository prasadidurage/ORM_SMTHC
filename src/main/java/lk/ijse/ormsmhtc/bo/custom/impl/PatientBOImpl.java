package lk.ijse.ormsmhtc.bo.custom.impl;


import lk.ijse.ormsmhtc.bo.custom.PatientBO;
import lk.ijse.ormsmhtc.dao.DAOFactory;
import lk.ijse.ormsmhtc.dao.custom.impl.PatientDAOImpl;
import lk.ijse.ormsmhtc.dao.custom.impl.TherapySessionDAOImpl;
import lk.ijse.ormsmhtc.dto.PatientDto;
import lk.ijse.ormsmhtc.dto.TherapySessionDTO;
import lk.ijse.ormsmhtc.entity.*;

import java.util.ArrayList;
import java.util.List;

public class PatientBOImpl implements PatientBO {
    private PatientDAOImpl patientDAO = (PatientDAOImpl) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PATIENT);
    private TherapySessionDAOImpl therapySessionDAO = (TherapySessionDAOImpl) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.THERAPY_SESSION);

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

    public ArrayList<TherapySessionDTO> getAllById(String patientId) {
        ArrayList<TherapySession> therapySession = therapySessionDAO.getAllByIdPatientId(patientId);
        ArrayList<TherapySessionDTO> therapySessionDTOS = new ArrayList<>();
        for (TherapySession therapySession1 : therapySession){
            TherapySessionDTO therapySessionDTO = new TherapySessionDTO(
                    therapySession1.getId(),
                    therapySession1.getDate(),
                    therapySession1.getStartTime(),
                    therapySession1.getEndTime(),
                    therapySession1.getTherapist().getId(),
                    therapySession1.getPatient().getId(),
                    therapySession1.getTherapyProgram().getId()
            );
            therapySessionDTOS.add(therapySessionDTO);
        }
        return therapySessionDTOS;
    }
}
