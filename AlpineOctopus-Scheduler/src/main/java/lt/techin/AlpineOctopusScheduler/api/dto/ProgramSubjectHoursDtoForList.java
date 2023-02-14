package lt.techin.AlpineOctopusScheduler.api.dto;

import lt.techin.AlpineOctopusScheduler.model.Subject;
import java.util.Objects;

public class ProgramSubjectHoursDtoForList {

    private Subject subject;

    private Integer subjectHours;

    public ProgramSubjectHoursDtoForList() {
    }

    public ProgramSubjectHoursDtoForList(Subject subject, Integer subjectHours) {
        this.subject = subject;
        this.subjectHours = subjectHours;
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
        ProgramSubjectHoursDtoForList that = (ProgramSubjectHoursDtoForList) o;
        return Objects.equals(subject, that.subject) && Objects.equals(subjectHours, that.subjectHours);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, subjectHours);
    }

    @Override
    public String toString() {
        return "ProgramSubjectHoursDtoForList{" +
                "subject=" + subject +
                ", subjectHours=" + subjectHours +
                '}';
    }
}
