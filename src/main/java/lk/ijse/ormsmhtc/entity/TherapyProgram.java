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
public class TherapyProgram {
    @Id
    String id;

    String name;
    String duration;
    double cost;
    String description;

//    @ManyToOne
//    @JoinColumn(name = "therapist_id")
//    private Therapist therapist;

    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> payments;

    @OneToMany(mappedBy = "therapyProgram", cascade = CascadeType.ALL)
    private List<TherapySession> therapySessions;

    public TherapyProgram(String id, String name, String duration, double cost, String description) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.cost = cost;
        this.description = description;
    }


    //    @OneToMany
//    private List<PatientProgram> patientPrograms;
//
//    @OneToMany
//    private List<TherapistProgram> therapistPrograms;
}
