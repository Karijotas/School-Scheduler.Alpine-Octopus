package lt.techin.AlpineOctopusScheduler.api.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class SubjectEntityDto extends SubjectDto{

    private Long id;

    public SubjectEntityDto(){

    }

    public SubjectEntityDto(Long id, String name, String description, LocalDateTime createdDate, LocalDateTime modifiedDate){
        super(name, description , createdDate, modifiedDate);
        this.id= id;
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
        SubjectEntityDto that = (SubjectEntityDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }

    @Override
    public String toString() {
        return "SubjectEntityDto{" +
                "id=" + id +
                '}';
    }
}
