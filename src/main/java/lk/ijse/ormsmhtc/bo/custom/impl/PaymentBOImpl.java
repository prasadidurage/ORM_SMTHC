package lk.ijse.ormsmhtc.bo.custom.impl;



import lk.ijse.ormsmhtc.dao.custom.impl.PatientDAOImpl;
import lk.ijse.ormsmhtc.dao.custom.impl.PaymentDAOImpl;
import lk.ijse.ormsmhtc.dao.custom.impl.TherapyProgramDAOImpl;
import lk.ijse.ormsmhtc.dao.custom.impl.TherapySessionDAOImpl;
import lk.ijse.ormsmhtc.dto.PaymentDto;
import lk.ijse.ormsmhtc.entity.*;

import java.util.ArrayList;

public class PaymentBOImpl {
    PaymentDAOImpl paymentDAO = new PaymentDAOImpl();
    PatientDAOImpl patientDAO = new PatientDAOImpl();
    TherapyProgramDAOImpl therapyProgramDAO = new TherapyProgramDAOImpl();
    TherapySessionDAOImpl therapySessionDAO = new TherapySessionDAOImpl();
    public String getNextId() {
        String lastId = paymentDAO.getLastId();
        if (lastId != null) {
            String subString = lastId.substring(3);
            int lastIndex = Integer.parseInt(subString);
            int nextIndex = lastIndex + 1;
            return String.format("PAY%03d", nextIndex);
        }
        return "PAY001";
    }

    public boolean savePayment(PaymentDto paymentDto) {
        Patient patient = patientDAO.getAllById(paymentDto.getPatientId());
        TherapyProgram therapyProgram = therapyProgramDAO.getAllById(paymentDto.getProgramId());
        TherapySession therapySession = therapySessionDAO.getAllById(paymentDto.getTherapySessionId());
        Payment payment = new Payment(
                paymentDto.getId(),
                paymentDto.getAmount(),
                paymentDto.getInstallment(),
                paymentDto.getStatus(),
                paymentDto.getBalance(),
                paymentDto.getDate(),
                patient,
                therapyProgram,
                therapySession
        );
        return paymentDAO.save(payment);
    }

    public boolean updatePayment(PaymentDto paymentDto) {
//        Patient patient = patientDAO.getAllById(paymentDto.getPatientId());
//        TherapyProgram therapyProgram = therapyProgramDAO.getAllById(paymentDto.getProgramId());
//        TherapySession therapySession = therapySessionDAO.getAllById(paymentDto.getTherapySessionId());
        Payment payment = new Payment(
                paymentDto.getId(),
                paymentDto.getAmount(),
                paymentDto.getInstallment(),
                paymentDto.getStatus(),
                paymentDto.getBalance(),
                paymentDto.getDate()
        );
        System.out.println("PatientId = "+paymentDto.getPatientId()+" Program- "+paymentDto.getProgramId());
        return paymentDAO.update(payment,paymentDto.getTherapySessionId(),paymentDto.getPatientId(),paymentDto.getProgramId());
    }

    public PaymentDto getAllById(String paymentId) {
        Payment payment = paymentDAO.getAllById(paymentId);
        return new PaymentDto(
                payment.getProgram().getId(),
                payment.getPatient().getId(),
                payment.getDate(),
                payment.getBalance(),
                payment.getStatus(),
                payment.getInstallment(),
                payment.getAmount(),
                payment.getId()
        );
    }

    public boolean delete(String paymentId) {
        return paymentDAO.delete(paymentId);
    }

    public ArrayList<PaymentDto> getAll(){
        ArrayList<Payment> payments = paymentDAO.getAll();
        ArrayList<PaymentDto> paymentDtos = new ArrayList<>();
        for (Payment payment : payments){
            PaymentDto paymentDto = new PaymentDto(
                    payment.getId(),
                    payment.getAmount(),
                    payment.getInstallment(),
                    payment.getStatus(),
                    payment.getBalance(),
                    payment.getDate(),
                    payment.getPatient().getId(),
                    payment.getProgram().getId(),
                    (payment.getTherapySession() != null && payment.getTherapySession().getId() != null)
                            ? payment.getTherapySession().getId()
                            : "No"
            );
            paymentDtos.add(paymentDto);
        }
        return paymentDtos;
    }

    public boolean deleteSession(String paymentId) {
        return paymentDAO.delete(paymentId);
    }
}
