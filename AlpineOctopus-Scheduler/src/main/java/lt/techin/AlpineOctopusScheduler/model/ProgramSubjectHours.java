package lt.techin.AlpineOctopusScheduler.model;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.Objects;

@Entity
public class ProgramSubjectHours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id")
    @JsonIgnore
    @Valid
    Program program;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    @Valid
    Subject subject;

    Integer subjectHours;

    public ProgramSubjectHours() {
    }

    public ProgramSubjectHours(Long id, Program program, Subject subject, Integer subjectHours) {
        this.id = id;
        this.program = program;
        this.subject = subject;
        this.subjectHours = subjectHours;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public int getSubjectHours() {
        return subjectHours;
    }

    public void setSubjectHours(Integer subjectHours) {
        this.subjectHours = subjectHours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProgramSubjectHours that = (ProgramSubjectHours) o;
        return subjectHours == that.subjectHours && Objects.equals(id, that.id) && Objects.equals(program, that.program) && Objects.equals(subject, that.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, program, subject, subjectHours);
    }
}
