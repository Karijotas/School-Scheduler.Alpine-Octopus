package lt.techin.AlpineOctopusScheduler.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Objects;

public class GroupsDto {


    private String name;
    private Integer schoolYear;
    private Integer studentAmount;
    private Long shiftId;
    private Long programId;

    private Boolean deleted;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedDate;

    private String createdBy;

    private String modifiedBy;

    public GroupsDto() {
    }

    public GroupsDto(String name, Integer schoolYear, Integer studentAmount, Long shiftId, Long programId, LocalDateTime createdDate, LocalDateTime modifiedDate, String createdBy, String modifiedBy, Boolean deleted) {
        this.name = name;
        this.schoolYear = schoolYear;
        this.studentAmount = studentAmount;
        this.shiftId = shiftId;
        this.programId = programId;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.createdBy = createdBy;
        this.modifiedBy = modifiedBy;
        this.deleted = deleted;
    }

    public Long getProgramId() {
        return programId;
    }

    public void setProgramId(Long programId) {
        this.programId = programId;
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

    public Long getShiftId() {
        return shiftId;
    }

    public void setShiftId(Long shiftId) {
        this.shiftId = shiftId;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupsDto groupsDto = (GroupsDto) o;
        return Objects.equals(getName(), groupsDto.getName()) && Objects.equals(getSchoolYear(), groupsDto.getSchoolYear()) && Objects.equals(getStudentAmount(), groupsDto.getStudentAmount()) && Objects.equals(shiftId, groupsDto.shiftId) && Objects.equals(getProgramId(), groupsDto.getProgramId()) && Objects.equals(getDeleted(), groupsDto.getDeleted()) && Objects.equals(getCreatedBy(), groupsDto.getCreatedBy()) && Objects.equals(getModifiedBy(), groupsDto.getModifiedBy());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getSchoolYear(), getStudentAmount(), shiftId, getProgramId(), getDeleted(), getCreatedBy(), getModifiedBy());
    }

    @Override
    public String toString() {
        return "GroupsDto{" +
                "name='" + name + '\'' +
                ", schoolYear=" + schoolYear +
                ", studentAmount=" + studentAmount +
                ", shiftId=" + shiftId +
                ", programId=" + programId +
                ", deleted=" + deleted +
//                ", createdDate=" + createdDate +
//                ", modifiedDate=" + modifiedDate +
                ", createdBy='" + createdBy + '\'' +
                ", modifiedBy='" + modifiedBy + '\'' +
                '}';
    }
}
