package lt.techin.AlpineOctopusScheduler.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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
    @NotBlank
    @Size(min = 1, max = 100, message = "A name shouldn't be longer than 100 characters or shorter than 1")
    private String name;
    @NotBlank
    @Email
    private String loginEmail;
    private String contactEmail;
    private String phone;
    private double workHoursPerWeek;
    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinTable(
            name = "teacher_shifts",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "shift_id"))
    private Set<Shift> teacherShifts = new HashSet<>();

    private Boolean deleted = Boolean.FALSE;

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


    public Teacher() {
    }

    public Teacher(Long id, String name, String loginEmail, String contactEmail, String phone, double workHoursPerWeek, Set<Shift> teacherShifts, Boolean deleted, LocalDateTime createdDate, LocalDateTime modifiedDate, String createdBy, String modifiedBy) {
        this.id = id;
        this.name = name;
        this.loginEmail = loginEmail;
        this.contactEmail = contactEmail;
        this.phone = phone;
        this.workHoursPerWeek = workHoursPerWeek;
        this.teacherShifts = teacherShifts;
        this.deleted = deleted;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.createdBy = createdBy;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return Double.compare(teacher.workHoursPerWeek, workHoursPerWeek) == 0 && Objects.equals(id, teacher.id) && Objects.equals(name, teacher.name) && Objects.equals(loginEmail, teacher.loginEmail) && Objects.equals(contactEmail, teacher.contactEmail) && Objects.equals(phone, teacher.phone) && Objects.equals(teacherShifts, teacher.teacherShifts) && Objects.equals(deleted, teacher.deleted) && Objects.equals(createdDate, teacher.createdDate) && Objects.equals(modifiedDate, teacher.modifiedDate) && Objects.equals(createdBy, teacher.createdBy) && Objects.equals(modifiedBy, teacher.modifiedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, loginEmail, contactEmail, phone, workHoursPerWeek, teacherShifts, deleted, createdDate, modifiedDate, createdBy, modifiedBy);
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", loginEmail='" + loginEmail + '\'' +
                ", contactEmail='" + contactEmail + '\'' +
                ", phone='" + phone + '\'' +
                ", workHoursPerWeek=" + workHoursPerWeek +
                ", teacherShifts=" + teacherShifts +
                ", deleted=" + deleted +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                ", createdBy='" + createdBy + '\'' +
                ", modifiedBy='" + modifiedBy + '\'' +
                '}';
    }
}
