package lt.techin.AlpineOctopusScheduler.dao;

import lt.techin.AlpineOctopusScheduler.model.Groups;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupsRepository extends JpaRepository<Groups, Long> {

    List<Groups> findAllByDeletedAndNameContainingIgnoreCaseOrderByModifiedDateDesc(Boolean deleted, String nameText);

    List<Groups> findAllByDeletedAndSchoolYearOrderByModifiedDateDesc(Boolean deleted, Integer schoolYearText);

    List<Groups> findAllByDeletedOrderByModifiedDateDesc(Boolean deleted, Pageable pageable);

    List<Groups> findAllByDeletedAndNameContainingIgnoreCase(Boolean deleted, String nameText, Pageable pageable);

    List<Groups> findAllByDeletedOrderByModifiedDateDesc(Boolean deleted);

    List<Groups> findAllByDeletedAndProgram_nameContainingIgnoreCaseOrderByModifiedDateDesc(Boolean deleted, String programText);
}



