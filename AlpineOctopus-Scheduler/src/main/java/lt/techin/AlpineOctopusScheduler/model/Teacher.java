package lt.techin.AlpineOctopusScheduler.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

//Mantvydas Juršys


@Entity
//@Table(name = "TEACHERS")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;
    private String loginEmail;
    private String contactEmail;
    private String phone;
    private double workHoursPerWeek;

    //@ManyToMany(mappedBy = subjectEntity)
    //private List<String> subjectsList;

    private String shift;
// variables end

    @ManyToMany (mappedBy = "subjectTeachers")
    @JsonIgnore
    private Set<Subject> teachersSubjects;

    public Teacher(){}

    public Teacher(Long id, String name, String surname, String loginEmail, String contactEmail, String phone, double workHoursPerWeek, String shift, Set<Subject> teachersSubjects) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.loginEmail = loginEmail;
        this.contactEmail = contactEmail;
        this.phone = phone;
        this.workHoursPerWeek = workHoursPerWeek;
        this.shift = shift;
        this.teachersSubjects = teachersSubjects;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getWorkHoursPerWeek() {
        return workHoursPerWeek;
    }

    public void setWorkHoursPerWeek(double workHoursPerWeek) {
        this.workHoursPerWeek = workHoursPerWeek;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

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
        Teacher teacher = (Teacher) o;
        return Double.compare(teacher.workHoursPerWeek, workHoursPerWeek) == 0 && Objects.equals(id, teacher.id) && Objects.equals(name, teacher.name) && Objects.equals(surname, teacher.surname) && Objects.equals(loginEmail, teacher.loginEmail) && Objects.equals(contactEmail, teacher.contactEmail) && Objects.equals(phone, teacher.phone) && Objects.equals(shift, teacher.shift) && Objects.equals(teachersSubjects, teacher.teachersSubjects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, loginEmail, contactEmail, phone, workHoursPerWeek, shift, teachersSubjects);
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", loginEmail='" + loginEmail + '\'' +
                ", contactEmail='" + contactEmail + '\'' +
                ", phone='" + phone + '\'' +
                ", workHoursPerWeek=" + workHoursPerWeek +
                ", shift='" + shift + '\'' +
                ", teachersSubjects=" + teachersSubjects +
                '}';
    }
}
