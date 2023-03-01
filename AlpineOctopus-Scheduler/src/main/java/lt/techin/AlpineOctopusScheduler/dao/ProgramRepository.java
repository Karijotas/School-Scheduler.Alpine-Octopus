package lt.techin.AlpineOctopusScheduler.dao;

import lt.techin.AlpineOctopusScheduler.model.Program;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {

    @Query(value = "SELECT s.id, psh.subject_hours FROM subject s INNER JOIN program_subject_hours psh ON psh.subject_id = s.id WHERE psh.program_id = :pid", nativeQuery = true)
    List<String> GetSubjectsAndHoursInProgram(@Param("pid") Long id);

//    @Modifying
//    @Query("DELETE FROM ProgramSubjectHours WHERE ProgramSubjectHours.program.id = :pid AND ProgramSubjectHours.subject.id = :sid")
//    List<Subject> DeleteSubjectByIdInProgram(@Param("pid") Long programId, @Param("sid") Long subjectId);

    List<Program> findByNameContainingIgnoreCase(String nameText);

    List<Program> findByNameContainingIgnoreCase(String nameText, Pageable pageable);


    List<Program> findAllByDeletedOrderByModifiedDateDesc(Boolean deleted, Pageable pageable);

    List<Program> findAllByDeletedAndNameContainingIgnoreCase(Boolean deleted, String nameText, Pageable pageable);

    List<Program> findAllByDeletedAndNameContainingIgnoreCase(Boolean deleted, String nameText);

    List<Program> findAllByDeletedOrderByModifiedDateDesc(Boolean deleted);


}

//(@Param("names") List<String> names, @Param("status") String status);
//@Query(value="SELECT * FROM notes n INNER JOIN notes_labels nl ON nl.notes_id = n.id WHERE nl.labels_id = :lid", nativeQuery=true)

//"SELECT d FROM Document d WHERE d.docType IN(:names) and d.status = :status ORDER BY d.id DESC"