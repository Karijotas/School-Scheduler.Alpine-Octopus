package lt.techin.AlpineOctopusScheduler.dao;

import lt.techin.AlpineOctopusScheduler.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {


    List<Lesson> findByRoom_IdAndStartTimeGreaterThan(Long id, LocalDateTime startTime);

    List<Lesson> findByTeacher_IdAndStartTime(Long id, @NonNull LocalDateTime startTime);

    List<Lesson> findByTeacher_IdAndStartTimeGreaterThan(Long id, LocalDateTime startTime);


}
