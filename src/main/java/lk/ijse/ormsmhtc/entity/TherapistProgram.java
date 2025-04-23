package lk.ijse.ormsmhtc.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "therapist_program")
public class TherapistProgram {
    @EmbeddedId
    private TherapistProgramId id;

    @ManyToOne
    @MapsId("therapistId")
    @JoinColumn(name="therapist_id")
    private Therapist therapistId;

    @ManyToOne
    @MapsId("programId")
    @JoinColumn(name="program_id")
    private TherapyProgram programId;

    String day;
    LocalTime startTime;
    LocalTime endTime;

}
