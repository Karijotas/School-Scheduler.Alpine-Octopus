package lt.techin.AlpineOctopusScheduler.api.dto;

import lt.techin.AlpineOctopusScheduler.model.Program;

import java.time.LocalDateTime;
import java.util.Objects;

public class GroupsEntityDto extends GroupsDto {

    private Long id;

    private String programName;

    public GroupsEntityDto(String name, Integer schoolYear, Integer studentAmount, String shift, LocalDateTime createdDate, LocalDateTime modifiedDate, String createdBy, String modifiedBy, Long id, String programName) {
        super(name, schoolYear, studentAmount, shift, createdDate, modifiedDate, createdBy, modifiedBy);
        this.id = id;
        this.programName = programName;
    }

    public GroupsEntityDto() {
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
        return Objects.equals(getId(), that.getId()) && Objects.equals(getProgramName(), that.getProgramName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId(), getProgramName());
    }

    @Override
    public String toString() {
        return "GroupsEntityDto{" +
                "id=" + id +
                ", programName='" + programName + '\'' +
                '}';
    }
}