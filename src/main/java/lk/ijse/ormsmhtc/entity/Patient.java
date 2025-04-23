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
public class Patient {
    @Id
    String id;

    String name;
    String address;
    String phone;
    String email;
    @Column(nullable = true)
    String history;

    public Patient(String id, String name, String address, String phone, String email, String history) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.history = history;
    }

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> payments;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TherapySession> therapySessions;

//    @OneToMany
//    private List<PatientProgram> patientPrograms;
}
