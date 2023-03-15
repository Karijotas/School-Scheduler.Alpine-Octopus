package lt.techin.AlpineOctopusScheduler.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.Objects;

@Entity
public class ScheduleLessons {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @Valid
    Schedule schedule;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @Valid
    Lesson lesson;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduleLessons that = (ScheduleLessons) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getSchedule(), that.getSchedule()) && Objects.equals(getLesson(), that.getLesson());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getSchedule(), getLesson());
    }

    @Override
    public String toString() {
        return "ScheduleLessons{" +
                "id=" + id +
                ", schedule=" + schedule +
                ", lesson=" + lesson +
                '}';
    }
}
