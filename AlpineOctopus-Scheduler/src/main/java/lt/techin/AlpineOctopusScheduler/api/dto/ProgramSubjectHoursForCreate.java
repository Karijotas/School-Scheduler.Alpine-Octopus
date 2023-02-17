package lt.techin.AlpineOctopusScheduler.api.dto;

import java.util.Objects;

public class ProgramSubjectHoursForCreate {

      private Long subjectId;

      private Integer subjectHour;

    public ProgramSubjectHoursForCreate() {
    }

    public ProgramSubjectHoursForCreate(Long subjectId, Integer subjectHour) {
        this.subjectId = subjectId;
        this.subjectHour = subjectHour;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getSubjectHour() {
        return subjectHour;
    }

    public void setSubjectHour(Integer subjectHour) {
        this.subjectHour = subjectHour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProgramSubjectHoursForCreate that = (ProgramSubjectHoursForCreate) o;
        return Objects.equals(subjectId, that.subjectId) && Objects.equals(subjectHour, that.subjectHour);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subjectId, subjectHour);
    }

    @Override
    public String toString() {
        return "ProgramSubjectHoursForCreate{" +
                "subjectId=" + subjectId +
                ", subjectHour=" + subjectHour +
                '}';
    }
}
