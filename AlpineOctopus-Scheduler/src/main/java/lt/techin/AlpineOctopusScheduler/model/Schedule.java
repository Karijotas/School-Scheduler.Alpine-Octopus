package lt.techin.AlpineOctopusScheduler.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

//    private LocalDateTime dateFrom;

//    private lastLessonTime

    private Lesson lesson;
}

