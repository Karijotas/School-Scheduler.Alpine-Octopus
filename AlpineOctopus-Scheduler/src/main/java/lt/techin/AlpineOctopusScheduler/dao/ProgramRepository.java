package lt.techin.AlpineOctopusScheduler.dao;

import lt.techin.AlpineOctopusScheduler.api.dto.SubjectDto;
import lt.techin.AlpineOctopusScheduler.model.Program;
import lt.techin.AlpineOctopusScheduler.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;


@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {

@Query(value = "SELECT s.name, psh.subject_hours FROM subject s INNER JOIN program_subject_hours psh ON psh.subject_id = s.id WHERE psh.program_id = :pid", nativeQuery = true)
    Set<String> GetSubjectsInProgram (@Param("pid") Long id);
}

//(@Param("names") List<String> names, @Param("status") String status);
//@Query(value="SELECT * FROM notes n INNER JOIN notes_labels nl ON nl.notes_id = n.id WHERE nl.labels_id = :lid", nativeQuery=true)

//"SELECT d FROM Document d WHERE d.docType IN(:names) and d.status = :status ORDER BY d.id DESC"