package lt.techin.AlpineOctopusScheduler.api.dto;

import javax.persistence.*;
import java.util.Objects;


public class ProgramSubjectHourListDto {

    private Long id;

private ProgramSubjectHoursForCreate subjectHourList;


    public ProgramSubjectHourListDto(ProgramSubjectHoursForCreate subjectHourList) {
        this.subjectHourList = subjectHourList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProgramSubjectHoursForCreate getSubjectHourList() {
        return subjectHourList;
    }

    public void setSubjectHourList(ProgramSubjectHoursForCreate subjectHourList) {
        this.subjectHourList = subjectHourList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProgramSubjectHourListDto that = (ProgramSubjectHourListDto) o;
        return Objects.equals(id, that.id) && Objects.equals(subjectHourList, that.subjectHourList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, subjectHourList);
    }

    @Override
    public String toString() {
        return "ProgramSubjectHourList{" +
                "id=" + id +
                ", subjectHourList=" + subjectHourList +
                '}';
    }
}


