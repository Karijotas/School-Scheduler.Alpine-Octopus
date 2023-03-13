package lt.techin.AlpineOctopusScheduler.dao;

import lt.techin.AlpineOctopusScheduler.model.Lesson;
import lt.techin.AlpineOctopusScheduler.model.Schedule;
import lt.techin.AlpineOctopusScheduler.model.ScheduleLessons;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleLessonsRepository extends JpaRepository<ScheduleLessons, Long> {

    void deleteByScheduleAndLesson(Schedule schedule, Lesson lesson);


}
