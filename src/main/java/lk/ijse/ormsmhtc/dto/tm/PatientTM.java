package lk.ijse.ormsmhtc.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PatientTM {
    String id;
    String name;
    String address;
    String phone;
    String email;
    String history;
}
