package lt.techin.AlpineOctopusScheduler.dao;

import lt.techin.AlpineOctopusScheduler.model.Holiday;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Long> {
    //    List<Holiday> findByNameContainingIgnoreCase(String nameText, Pageable pageable);
    List<Holiday> findByNameContainingIgnoreCase(String nameText);

    List<Holiday> findAllByStartDateGreaterThanEqual(LocalDate startDate, Pageable pageable);
//
//    List<Holiday> findAllByStartingDate(LocalDate startDate, Pageable pageable);


//    List<Holiday> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
