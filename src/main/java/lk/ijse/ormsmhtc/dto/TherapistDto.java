package lk.ijse.ormsmhtc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TherapistDto {
    String id;
    String name;
    String phone;
    String email;
    String specialization;
}
