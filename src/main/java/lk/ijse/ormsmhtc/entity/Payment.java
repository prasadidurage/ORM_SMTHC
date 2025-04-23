package lk.ijse.ormsmhtc.entity;

import jakarta.persistence.*;
import lombok.*;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Data
@Entity
@Table
public class Payment {
    @Id
    String id;

    @Column(nullable = false)
    double amount;

    String installment;
    String status;
    double balance;

    @Column(nullable = false)
    Date date;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "therapy_program_id")
    private TherapyProgram program;

    @ManyToOne
    @JoinColumn(name = "therapy_session_id")
    @Nullable
    private TherapySession therapySession;

    public Payment(String id, double amount, String installment, String status, double balance, Date date) {
        this.id = id;
        this.amount = amount;
        this.installment = installment;
        this.status = status;
        this.balance = balance;
        this.date = date;
    }
}
