package lt.techin.AlpineOctopusScheduler.api.dto;

import java.util.Objects;

public class ModuleEntityDto extends ModuleDto{

    private Long id;

    public ModuleEntityDto(){

    }

    public ModuleEntityDto(Long id, String name, String description){
        super(name, description);
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
        ModuleEntityDto that = (ModuleEntityDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ModuleEntityDto{" +
                "id=" + id +
                '}';
    }
}

