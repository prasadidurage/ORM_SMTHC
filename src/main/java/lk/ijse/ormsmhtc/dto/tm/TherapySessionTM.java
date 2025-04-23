package lk.ijse.ormsmhtc.dto.tm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TherapySessionTM {
    String id;
    Date date;
    Time startTime;
    Time endTime;
    String therapistId;
    String patientId;
    String therapyProgramID;
}
