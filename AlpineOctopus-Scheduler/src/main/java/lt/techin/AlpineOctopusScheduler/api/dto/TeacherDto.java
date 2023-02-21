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

    private Boolean deleted;

    public TeacherDto() {

    }
    public TeacherDto(String name, String surname, String loginEmail, String contactEmail, double workHoursPerWeek, String phone, String shift, Boolean deleted) {
        this.name = name;
        this.surname = surname;
        this.loginEmail = loginEmail;
        this.contactEmail = contactEmail;
        this.workHoursPerWeek = workHoursPerWeek;
        this.phone = phone;
        this.shift = shift;
        this.deleted = deleted;
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
        TeacherDto that = (TeacherDto) o;
        return Double.compare(that.workHoursPerWeek, workHoursPerWeek) == 0 && Objects.equals(name, that.name) && Objects.equals(surname, that.surname) && Objects.equals(loginEmail, that.loginEmail) && Objects.equals(contactEmail, that.contactEmail) && Objects.equals(phone, that.phone) && Objects.equals(shift, that.shift) && Objects.equals(deleted, that.deleted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, loginEmail, contactEmail, workHoursPerWeek, phone, shift, deleted);
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
                ", deleted=" + deleted +
                '}';
    }
}
