package lt.techin.AlpineOctopusScheduler.api.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class GroupsTestDto extends GroupsDto {

    private Long id;

    public GroupsTestDto() {
    }

    public GroupsTestDto(Long id) {
        this.id = id;
    }

    public GroupsTestDto(String name, Integer schoolYear, Integer studentAmount, String shift, Long programId, LocalDateTime createdDate, LocalDateTime modifiedDate, String createdBy, String modifiedBy, Boolean deleted) {
        super(name, schoolYear, studentAmount, shift, programId, createdDate, modifiedDate, createdBy, modifiedBy, deleted);
        this.id = id;
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
        GroupsTestDto that = (GroupsTestDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }

    @Override
    public String toString() {
        return "GroupsTestDto{" +
                "id=" + id +
                '}';
    }
}
