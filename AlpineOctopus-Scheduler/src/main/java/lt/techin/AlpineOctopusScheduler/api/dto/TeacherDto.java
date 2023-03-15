package lt.techin.AlpineOctopusScheduler.api.dto;

import lt.techin.AlpineOctopusScheduler.model.Shift;
import lt.techin.AlpineOctopusScheduler.model.Subject;

import java.util.Objects;
import java.util.Set;

public class TeacherDto {

    private String name;
    private String loginEmail;
    private String contactEmail;
    private double workHoursPerWeek;
    private String phone;
    private Set<Shift> teacherShifts;

    private Set<Subject> teacherSubjects;

    private Boolean deleted;


    public TeacherDto() {

    }

    public TeacherDto(String name, String loginEmail, String contactEmail, double workHoursPerWeek, String phone, Set<Shift> teacherShifts, Set<Subject> teacherSubjects, Boolean deleted) {
        this.name = name;
        this.loginEmail = loginEmail;
        this.contactEmail = contactEmail;
        this.workHoursPerWeek = workHoursPerWeek;
        this.phone = phone;
        this.teacherShifts = teacherShifts;
        this.teacherSubjects = teacherSubjects;
        this.deleted = deleted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Set<Shift> getTeacherShifts() {
        return teacherShifts;
    }

    public void setTeacherShifts(Set<Shift> teacherShifts) {
        this.teacherShifts = teacherShifts;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Set<Subject> getTeacherSubjects() {
        return teacherSubjects;
    }

    public void setTeacherSubjects(Set<Subject> teacherSubjects) {
        this.teacherSubjects = teacherSubjects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeacherDto that = (TeacherDto) o;
        return Double.compare(that.workHoursPerWeek, workHoursPerWeek) == 0 && Objects.equals(name, that.name) && Objects.equals(loginEmail, that.loginEmail) && Objects.equals(contactEmail, that.contactEmail) && Objects.equals(phone, that.phone) && Objects.equals(teacherShifts, that.teacherShifts) && Objects.equals(teacherSubjects, that.teacherSubjects) && Objects.equals(deleted, that.deleted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, loginEmail, contactEmail, workHoursPerWeek, phone, teacherShifts, teacherSubjects, deleted);
    }

    @Override
    public String toString() {
        return "TeacherDto{" +
                "name='" + name + '\'' +
                ", loginEmail='" + loginEmail + '\'' +
                ", contactEmail='" + contactEmail + '\'' +
                ", workHoursPerWeek=" + workHoursPerWeek +
                ", phone='" + phone + '\'' +
                ", teacherShifts=" + teacherShifts +
                ", teacherSubjects=" + teacherSubjects +
                ", deleted=" + deleted +
                '}';
    }
}
