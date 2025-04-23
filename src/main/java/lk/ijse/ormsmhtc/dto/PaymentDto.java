package lk.ijse.ormsmhtc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentDto {
    String id;
    double amount;
    String installment;
    String status;
    double balance;
    Date date;
    String patientId;
    String programId;
    String  therapySessionId;

    public PaymentDto(String programId, String patientId, Date date, double balance, String status, String installment, double amount, String id) {
        this.programId = programId;
        this.patientId = patientId;
        this.date = date;
        this.balance = balance;
        this.status = status;
        this.installment = installment;
        this.amount = amount;
        this.id = id;
    }
}
