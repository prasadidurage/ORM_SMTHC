package lk.ijse.ormsmhtc.dao.custom;


import lk.ijse.ormsmhtc.dao.CrudDAO;
import lk.ijse.ormsmhtc.entity.Payment;

import java.util.ArrayList;

public interface PaymentDAO extends CrudDAO<Payment> {
    public boolean update(Payment payment, String therapySessionId, String patientId, String programId);
    public Payment getBalance(String programID, String patientID);
    public ArrayList<Payment> findByPaymentId(String paymentId);
}
