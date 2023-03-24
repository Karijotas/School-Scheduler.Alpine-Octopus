package lt.techin.AlpineOctopusScheduler.dao;

import lt.techin.AlpineOctopusScheduler.model.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Long> {
//    List<Holiday> findByNameContainingIgnoreCase(String nameText, Pageable pageable);
//
//    List<Holiday> findAllByStartingDate(LocalDate startDate, Pageable pageable);


//    List<Holiday> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
