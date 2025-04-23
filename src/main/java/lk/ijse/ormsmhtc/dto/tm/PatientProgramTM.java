package lk.ijse.ormsmhtc.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PatientProgramTM {
    String patientId;
    String programId;
    Date registerDate;
    String paymentId;
}
