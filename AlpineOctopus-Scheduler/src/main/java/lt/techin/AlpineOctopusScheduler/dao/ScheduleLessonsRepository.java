package lt.techin.AlpineOctopusScheduler.dao;

import lt.techin.AlpineOctopusScheduler.model.ScheduleSubjects;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleLessonsRepository extends JpaRepository<ScheduleSubjects, Long> {
}
