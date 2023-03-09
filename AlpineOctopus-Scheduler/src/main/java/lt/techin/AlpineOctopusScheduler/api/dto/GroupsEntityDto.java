package lt.techin.AlpineOctopusScheduler.api.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class GroupsEntityDto extends GroupsDto {

    private Long id;

    private String programName;

    private String shiftName;


    public GroupsEntityDto(String name, Integer schoolYear, Integer studentAmount, Long shiftId, Long programId, LocalDateTime createdDate, LocalDateTime modifiedDate, String createdBy, String modifiedBy, Boolean deleted, Long id, String programName, String shiftName) {
        super(name, schoolYear, studentAmount, shiftId, programId, createdDate, modifiedDate, createdBy, modifiedBy, deleted);
        this.id = id;
        this.programName = programName;
        this.shiftName = shiftName;
    }

    public GroupsEntityDto() {
    }

    public String getShiftName() {
        return shiftName;
    }

    public void setShiftName(String shiftName) {
        this.shiftName = shiftName;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        GroupsEntityDto that = (GroupsEntityDto) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getProgramName(), that.getProgramName()) && Objects.equals(getShiftName(), that.getShiftName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId(), getProgramName(), getShiftName());
    }

    @Override
    public String toString() {
        return "GroupsEntityDto{" +
                "id=" + id +
                ", programName='" + programName + '\'' +
                ", shiftName='" + shiftName + '\'' +
                '}';
    }
}