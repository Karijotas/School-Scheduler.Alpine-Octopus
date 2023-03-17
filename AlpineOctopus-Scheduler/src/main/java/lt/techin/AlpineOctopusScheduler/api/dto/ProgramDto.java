package lt.techin.AlpineOctopusScheduler.api.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class ProgramDto {
    private String name;

    private String description;

    private Boolean deleted;

//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime createdDate;
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime modifiedDate;

    public ProgramDto() {
    }

    public ProgramDto(String name, String description, LocalDateTime createdDate, LocalDateTime modifiedDate, Boolean deleted) {
        this.name = name;
        this.description = description;
//        this.createdDate = createdDate;
//        this.modifiedDate = modifiedDate;
        this.deleted = deleted;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public LocalDateTime getCreatedDate() {
//        return createdDate;
//    }

//    public void setCreatedDate(LocalDateTime createdDate) {
//        this.createdDate = createdDate;
//    }
//
//    public LocalDateTime getModifiedDate() {
//        return modifiedDate;
//    }
//
//    public void setModifiedDate(LocalDateTime modifiedDate) {
//        this.modifiedDate = modifiedDate;
//    }

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
        ProgramDto that = (ProgramDto) o;
        return Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(deleted, that.deleted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, deleted);
    }

    @Override
    public String toString() {
        return "ProgramDto{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", deleted=" + deleted +
//                ", createdDate=" + createdDate +
//                ", modifiedDate=" + modifiedDate +
                '}';
    }
}
