package lt.techin.AlpineOctopusScheduler.api.dto;

import java.util.Objects;

public class GroupsDto {


    private String name;
    private Integer schoolYear;
    private Integer studentAmount;
    private String program;
    private String shift;

    public GroupsDto(){}

    public GroupsDto(String name, Integer schoolYear, Integer studentAmount, String program, String shift) {
        this.name = name;
        this.schoolYear = schoolYear;
        this.studentAmount = studentAmount;
        this.program = program;
        this.shift = shift;
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
