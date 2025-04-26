package lk.ijse.ormsmhtc.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Entity
@Table
public class Therapist {
    @Id
    String id;

    String name;
    String phone;
    String email;
    String specialization;

    public Therapist(String id, String name, String phone, String email, String specialization) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.specialization = specialization;
    }

//    @OneToMany(mappedBy = "therapist", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<TherapyProgram> therapyPrograms;

    @OneToMany(mappedBy = "therapist", cascade = CascadeType.ALL)
    private List<TherapySession> therapySessions;

//    @OneToMany
//    private List<TherapistProgram> therapistPrograms;
}
