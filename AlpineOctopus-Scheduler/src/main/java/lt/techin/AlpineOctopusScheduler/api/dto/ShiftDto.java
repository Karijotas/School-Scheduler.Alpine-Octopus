package lt.techin.AlpineOctopusScheduler.api.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class ShiftDto {


    private String name;

    private int starts;

    private int ends;

    //    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime createdDate;
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime modifiedDate;
    private Boolean deleted;

    public ShiftDto() {
    }

    public ShiftDto(String name, int starts, int ends, LocalDateTime createdDate, LocalDateTime modifiedDate, Boolean deleted) {
        this.name = name;
        this.starts = starts;
        this.ends = ends;
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

    public int getStarts() {
        return starts;
    }

    public void setStarts(int starts) {
        this.starts = starts;
    }

    public int getEnds() {
        return ends;
    }

    public void setEnds(int ends) {
        this.ends = ends;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShiftDto shiftDto = (ShiftDto) o;
        return starts == shiftDto.starts && ends == shiftDto.ends && Objects.equals(name, shiftDto.name) && Objects.equals(deleted, shiftDto.deleted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, starts, ends, deleted);
    }

    @Override
    public String toString() {
        return "ShiftDto{" +
                "name='" + name + '\'' +
                ", starts=" + starts +
                ", ends=" + ends +
//                ", createdDate=" + createdDate +
//                ", modifiedDate=" + modifiedDate +
                ", deleted=" + deleted +
                '}';
    }
}
