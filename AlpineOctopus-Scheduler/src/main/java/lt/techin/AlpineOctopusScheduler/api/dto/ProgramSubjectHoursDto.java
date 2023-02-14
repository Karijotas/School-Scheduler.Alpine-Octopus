package lt.techin.AlpineOctopusScheduler.api.dto;

import lt.techin.AlpineOctopusScheduler.model.Program;
import lt.techin.AlpineOctopusScheduler.model.Subject;

import java.util.Objects;

public class ProgramSubjectHoursDto {

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
        ProgramSubjectHoursDto that = (ProgramSubjectHoursDto) o;
        return subjectHours == that.subjectHours && Objects.equals(program, that.program) && Objects.equals(subject, that.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(program, subject, subjectHours);
    }

    @Override
    public String toString() {
        return "ProgramSubjectHoursDto{" +
                "program=" + program +
                ", subject=" + subject +
                ", subjectHours=" + subjectHours +
                '}';
    }
}
