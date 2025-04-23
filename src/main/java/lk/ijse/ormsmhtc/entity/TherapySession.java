package lk.ijse.ormsmhtc.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Entity
@Table
public class TherapySession {
    @Id
    String id;

    Date date;
    Time startTime;
    Time endTime;

    @ManyToOne
    @JoinColumn(name = "therapist_id")
    private Therapist therapist;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "therapy_program_id")
    private TherapyProgram therapyProgram;

    @OneToMany(mappedBy = "therapySession", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> payments;

    public TherapySession(String id, Date date, Time startTime, Time endTime, Therapist therapist, Patient patient, TherapyProgram therapyProgram) {
        this.id = id;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.therapist = therapist;
        this.patient = patient;
        this.therapyProgram = therapyProgram;
    }
}
