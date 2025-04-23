package lk.ijse.ormsmhtc.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserTM {
    private String id;
    private String name;
    private String username;
    private String password;
    private String role;
    private String email;
}
