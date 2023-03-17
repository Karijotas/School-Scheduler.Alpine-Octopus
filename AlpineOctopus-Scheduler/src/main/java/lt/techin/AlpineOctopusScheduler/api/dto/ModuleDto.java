package lt.techin.AlpineOctopusScheduler.api.dto;

import java.util.Objects;

public class ModuleDto {

    private String name;
    private String description;

    private Boolean deleted;
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime createdDate;
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime modifiedDate;

//    private Set<Subject> modulesSubjects;


    public ModuleDto() {
    }

    public ModuleDto(String name, String description, Boolean deleted) {
        this.name = name;
        this.description = description;
//        this.createdDate=createdDate;
//        this.modifiedDate=modifiedDate;
        this.deleted = deleted;
//        this.modulesSubjects = modulesSubjects;

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
//
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

    //    public Set<Subject> getModulesSubjects() {
//        return modulesSubjects;
//    }
//
//    public void setModulesSubjects(Set<Subject> modulesSubjects) {
//        this.modulesSubjects = modulesSubjects;
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModuleDto moduleDto = (ModuleDto) o;
        return Objects.equals(name, moduleDto.name) && Objects.equals(description, moduleDto.description) && Objects.equals(deleted, moduleDto.deleted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, deleted);
    }

    @Override
    public String toString() {
        return "ModuleDto{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", deleted=" + deleted +
//                ", createdDate=" + createdDate +
//                ", modifiedDate=" + modifiedDate +
                '}';
    }
}

