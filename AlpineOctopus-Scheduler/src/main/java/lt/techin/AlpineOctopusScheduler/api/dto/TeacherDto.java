package lt.techin.AlpineOctopusScheduler.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lt.techin.AlpineOctopusScheduler.model.Subject;

import java.util.Objects;
import java.util.Set;

public class TeacherDto {

    private String name;

    private String surname;

    private String contactEmail;

    private String phone;

    private String shift;

    private Set<Subject> teachersSubjects;

    public TeacherDto() {

    }

    public TeacherDto(String name, String surname, String contactEmail, String phone, String shift, Set<Subject> teachersSubjects) {
        this.name = name;
        this.surname = surname;
        this.contactEmail = contactEmail;
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

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
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
    @JsonIgnore
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
        return Objects.equals(name, that.name) && Objects.equals(surname, that.surname) && Objects.equals(contactEmail, that.contactEmail) && Objects.equals(phone, that.phone) && Objects.equals(shift, that.shift) && Objects.equals(teachersSubjects, that.teachersSubjects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, contactEmail, phone, shift, teachersSubjects);
    }

    @Override
    public String toString() {
        return "TeacherDto{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", contactEmail='" + contactEmail + '\'' +
                ", phone='" + phone + '\'' +
                ", shift='" + shift + '\'' +
                ", teachersSubjects=" + teachersSubjects +
                '}';
    }
}
