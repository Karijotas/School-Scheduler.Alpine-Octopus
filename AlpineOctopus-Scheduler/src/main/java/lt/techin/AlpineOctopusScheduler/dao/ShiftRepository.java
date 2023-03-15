package lt.techin.AlpineOctopusScheduler.dao;

import lt.techin.AlpineOctopusScheduler.model.Shift;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Long> {

    List<Shift> findByNameContainingIgnoreCase(String nameText, Pageable pageable);


    List<Shift> findAllByDeletedOrderByModifiedDateDesc(Boolean deleted, Pageable pageable);

    List<Shift> findAllByDeletedOrderByModifiedDateDesc(Boolean deleted);

    List<Shift> findAllByDeletedAndNameContainingIgnoreCaseOrderByModifiedDateDesc(Boolean deleted, String nameText, Pageable pageable);

    List<Shift> findAllByDeletedAndNameContainingIgnoreCaseOrderByModifiedDateDesc(Boolean deleted, String nameText);


}
