package lk.ijse.ormsmhtc.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "patient_program")
public class PatientProgram {
    @EmbeddedId
    private PatientProgramId patientProgramId;

    @ManyToOne
    @MapsId("patientId")
    @JoinColumn(name="patient_id")
    private Patient patientId;

    @ManyToOne
    @MapsId("programId")
    @JoinColumn(name="program_id")
    private TherapyProgram programId;

    Date registration_date;

    @OneToOne
    @JoinColumn(name="payment_id")
    private Payment payment;
}
