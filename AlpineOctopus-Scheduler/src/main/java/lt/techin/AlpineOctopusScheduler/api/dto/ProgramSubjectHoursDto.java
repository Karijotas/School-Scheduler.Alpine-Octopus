package lt.techin.AlpineOctopusScheduler.api.dto;

import lt.techin.AlpineOctopusScheduler.model.Program;
import lt.techin.AlpineOctopusScheduler.model.Subject;

import java.util.Objects;

public class ProgramSubjectHoursDto {

    private Long id;
    private Program program;

    private Subject subject;

    Integer subjectHours;

    public ProgramSubjectHoursDto() {
    }

    public ProgramSubjectHoursDto(Program program, Subject subject, Integer subjectHours) {
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

    public Integer getSubjectHours() {
        return subjectHours;
    }

    public void setSubjectHours(Integer subjectHours) {
        this.subjectHours = subjectHours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProgramSubjectHoursDto that = (ProgramSubjectHoursDto) o;
        return Objects.equals(id, that.id) && Objects.equals(program, that.program) && Objects.equals(subject, that.subject) && Objects.equals(subjectHours, that.subjectHours);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, program, subject, subjectHours);
    }

    @Override
    public String toString() {
        return "ProgramSubjectHoursDto{" +
                "id=" + id +
                ", program=" + program +
                ", subject=" + subject +
                ", subjectHours=" + subjectHours +
                '}';
    }
}
