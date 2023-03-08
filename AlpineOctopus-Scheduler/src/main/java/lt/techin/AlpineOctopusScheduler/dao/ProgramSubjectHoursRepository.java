package lt.techin.AlpineOctopusScheduler.dao;

import lt.techin.AlpineOctopusScheduler.model.ProgramSubjectHours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProgramSubjectHoursRepository extends JpaRepository<ProgramSubjectHours, Long> {

    public List<ProgramSubjectHours> findAllByProgramId(Long programId);
}

//(@Param("names") List<String> names, @Param("status") String status);
//@Query(value="SELECT * FROM notes n INNER JOIN notes_labels nl ON nl.notes_id = n.id WHERE nl.labels_id = :lid", nativeQuery=true)

//"SELECT d FROM Document d WHERE d.docType IN(:names) and d.status = :status ORDER BY d.id DESC"