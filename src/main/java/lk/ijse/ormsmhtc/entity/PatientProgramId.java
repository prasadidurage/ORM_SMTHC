package lk.ijse.ormsmhtc.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class PatientProgramId {
    private String patientId;
    private String programId;
}
