package lt.techin.AlpineOctopusScheduler.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Objects;

public class GroupsDto {


    private String name;
    private Integer schoolYear;
    private Integer studentAmount;
    private String program;
    private String shift;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedDate;

    private String createdBy;

    private String modifiedBy;
    public GroupsDto(){}

    public GroupsDto(String name, Integer schoolYear, Integer studentAmount, String program, String shift, LocalDateTime createdDate, LocalDateTime modifiedDate, String createdBy, String modifiedBy) {
        this.name = name;
        this.schoolYear = schoolYear;
        this.studentAmount = studentAmount;
        this.program = program;
        this.shift = shift;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.createdBy = createdBy;
        this.modifiedBy = modifiedBy;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(Integer schoolYear) {
        this.schoolYear = schoolYear;
    }

    public Integer getStudentAmount() {
        return studentAmount;
    }

    public void setStudentAmount(Integer studentAmount) {
        this.studentAmount = studentAmount;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupsDto groupsDto = (GroupsDto) o;
        return Objects.equals(getName(), groupsDto.getName()) && Objects.equals(getSchoolYear(), groupsDto.getSchoolYear()) && Objects.equals(getStudentAmount(), groupsDto.getStudentAmount()) && Objects.equals(getProgram(), groupsDto.getProgram()) && Objects.equals(getShift(), groupsDto.getShift());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getSchoolYear(), getStudentAmount(), getProgram(), getShift());
    }

    @Override
    public String toString() {
        return "GroupDto{" +
                "name='" + name + '\'' +
                ", schoolYear=" + schoolYear +
                ", studentAmount=" + studentAmount +
                ", program='" + program + '\'' +
                ", shift='" + shift + '\'' +
                '}';
    }
}
