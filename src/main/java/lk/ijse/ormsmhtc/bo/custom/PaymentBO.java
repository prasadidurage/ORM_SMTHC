package lk.ijse.ormsmhtc.bo.custom;


import lk.ijse.ormsmhtc.bo.SuperBO;
import lk.ijse.ormsmhtc.dto.PaymentDto;

import java.util.ArrayList;

public interface PaymentBO extends SuperBO {
    public String getNextId();
    public boolean savePayment(PaymentDto paymentDto);
    public boolean updatePayment(PaymentDto paymentDto) ;
    public PaymentDto getAllById(String paymentId);
    public boolean delete(String paymentId);
    public ArrayList<PaymentDto> getAll();
    public boolean deleteSession(String paymentId);
    public double getBalance(String programID, String patientID);
    public ArrayList<PaymentDto> fondByPaymentId(String paymentId);
}
