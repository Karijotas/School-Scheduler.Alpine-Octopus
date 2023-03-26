package lt.techin.AlpineOctopusScheduler.dao;

import lt.techin.AlpineOctopusScheduler.model.Holiday;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Long> {
//    List<Holiday> findByDateRange(LocalDate startDate, LocalDate endDate);

    List<Holiday> findByNameContainingIgnoreCase(String nameText);

    List<Holiday> findAllByStartDateGreaterThanEqual(LocalDate startDate, Pageable pageable);

    List<Holiday> findByStartDateBetween(LocalDate start, LocalDate end);


}
