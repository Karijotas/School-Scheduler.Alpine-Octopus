package lt.techin.AlpineOctopusScheduler.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lt.techin.AlpineOctopusScheduler.model.Subject;

import java.util.Objects;
import java.util.Set;

public class TeacherDto {

    private String name;

    private String surname;
    private String loginEmail;
    private String contactEmail;
    private double workHoursPerWeek;
    private String phone;
    private String shift;

    private Set<Subject> teachersSubjects;

    public TeacherDto() {

    }
    public TeacherDto(String name, String surname, String loginEmail, String contactEmail, double workHoursPerWeek, String phone, String shift, Set<Subject> teachersSubjects) {
        this.name = name;
        this.surname = surname;
        this.loginEmail = loginEmail;
        this.contactEmail = contactEmail;
        this.workHoursPerWeek = workHoursPerWeek;
        this.phone = phone;
        this.shift = shift;
        this.teachersSubjects = teachersSubjects;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLoginEmail() {
        return loginEmail;
    }

    public void setLoginEmail(String loginEmail) {
        this.loginEmail = loginEmail;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public double getWorkHoursPerWeek() {
        return workHoursPerWeek;
    }

    public void setWorkHoursPerWeek(double workHoursPerWeek) {
        this.workHoursPerWeek = workHoursPerWeek;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }
//    @JsonIgnore
    public Set<Subject> getTeachersSubjects() {
        return teachersSubjects;
    }

    public void setTeachersSubjects(Set<Subject> teachersSubjects) {
        this.teachersSubjects = teachersSubjects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeacherDto that = (TeacherDto) o;
        return Double.compare(that.getWorkHoursPerWeek(), getWorkHoursPerWeek()) == 0 && Objects.equals(getName(), that.getName()) && Objects.equals(getSurname(), that.getSurname()) && Objects.equals(getLoginEmail(), that.getLoginEmail()) && Objects.equals(getContactEmail(), that.getContactEmail()) && Objects.equals(getPhone(), that.getPhone()) && Objects.equals(getShift(), that.getShift()) && Objects.equals(getTeachersSubjects(), that.getTeachersSubjects());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getSurname(), getLoginEmail(), getContactEmail(), getWorkHoursPerWeek(), getPhone(), getShift(), getTeachersSubjects());
    }
    @Override
    public String toString() {
        return "TeacherDto{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", loginEmail='" + loginEmail + '\'' +
                ", contactEmail='" + contactEmail + '\'' +
                ", workHoursPerWeek=" + workHoursPerWeek +
                ", phone='" + phone + '\'' +
                ", shift='" + shift + '\'' +
                ", teachersSubjects=" + teachersSubjects +
                '}';
    }
}
