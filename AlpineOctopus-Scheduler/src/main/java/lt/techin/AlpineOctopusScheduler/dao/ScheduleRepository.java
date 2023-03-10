package lt.techin.AlpineOctopusScheduler.dao;

import lt.techin.AlpineOctopusScheduler.model.Schedule;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByNameContainingIgnoreCaseOrderByModifiedDateDesc(String nameText, Pageable pageable);

    List<Schedule> findAllByStartingDateAfterOrderByModifiedDateDesc(LocalDate startingDate, Pageable pageable);


    List<Schedule> findByPlannedTillDateBeforeOrderByModifiedDateDesc(LocalDate plannedTillDate, Pageable pageable);


}
