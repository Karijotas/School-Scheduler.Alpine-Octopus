package lt.techin.AlpineOctopusScheduler.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

//Mantvydas Juršys


@Entity
//@Table(name = "TEACHERS")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private String surname;
    private String loginEmail;
    private String contactEmail;
    private String phone;
    private double workHoursPerWeek;
    private String shift;

    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;


    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedDate;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String modifiedBy;

    @ManyToMany (mappedBy = "subjectTeachers")
    @JsonIgnore
    private Set<Subject> teachersSubjects = new HashSet<>();;

    public Teacher(){}

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
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
        return Double.compare(teacher.getWorkHoursPerWeek(), getWorkHoursPerWeek()) == 0 && Objects.equals(getId(), teacher.getId()) && Objects.equals(getName(), teacher.getName()) && Objects.equals(getSurname(), teacher.getSurname()) && Objects.equals(getLoginEmail(), teacher.getLoginEmail()) && Objects.equals(getContactEmail(), teacher.getContactEmail()) && Objects.equals(getPhone(), teacher.getPhone()) && Objects.equals(getShift(), teacher.getShift()) && Objects.equals(getCreatedDate(), teacher.getCreatedDate()) && Objects.equals(getModifiedDate(), teacher.getModifiedDate()) && Objects.equals(getCreatedBy(), teacher.getCreatedBy()) && Objects.equals(getModifiedBy(), teacher.getModifiedBy()) && Objects.equals(getTeachersSubjects(), teacher.getTeachersSubjects());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getSurname(), getLoginEmail(), getContactEmail(), getPhone(), getWorkHoursPerWeek(), getShift(), getCreatedDate(), getModifiedDate(), getCreatedBy(), getModifiedBy(), getTeachersSubjects());
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
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                ", createdBy='" + createdBy + '\'' +
                ", modifiedBy='" + modifiedBy + '\'' +
                ", teachersSubjects=" + teachersSubjects +
                '}';
    }
}
