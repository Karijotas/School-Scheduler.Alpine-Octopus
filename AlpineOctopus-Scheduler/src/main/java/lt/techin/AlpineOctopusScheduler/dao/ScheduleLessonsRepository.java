package lt.techin.AlpineOctopusScheduler.dao;

import lt.techin.AlpineOctopusScheduler.model.ScheduleLessons;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleLessonsRepository extends JpaRepository<ScheduleLessons, Long> {
}
