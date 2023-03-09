package lt.techin.AlpineOctopusScheduler.dao;

import lt.techin.AlpineOctopusScheduler.model.Schedule;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    /* TODO: SUTVARKYTI FILTRUS !
     *  dabar filtruoja ne nuo arba ne iki, o pagal datas.
     *  Padaryti kad filtruotų normaliai nuo iki
     * */

    List<Schedule> findByNameContainingIgnoreCaseOrderByModifiedDateDesc(String nameText, Pageable pageable);

    List<Schedule> findByStartingDateOrderByModifiedDateDesc(LocalDate startingDate, Pageable pageable);

    List<Schedule> findByPlannedTillDateOrderByModifiedDateDesc(LocalDate plannedTillDate, Pageable pageable);


}
