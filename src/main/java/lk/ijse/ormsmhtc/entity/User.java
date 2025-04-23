package lk.ijse.ormsmhtc.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Entity
@Table
public class User {
    @Id
    private String id;

    private String name;

    private String username;
    private String password;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false,unique = true)
    private String email;
}
