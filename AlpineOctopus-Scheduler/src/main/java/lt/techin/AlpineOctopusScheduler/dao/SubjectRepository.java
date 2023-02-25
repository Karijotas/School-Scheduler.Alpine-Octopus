package lt.techin.AlpineOctopusScheduler.dao;

import lt.techin.AlpineOctopusScheduler.model.Subject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

    List<Subject> findByNameContainingIgnoreCase(String nameText, Pageable pageable);

    List<Subject> findAllByDeletedAndNameContainingIgnoreCaseOrderByModifiedDateDesc(Boolean deleted, String nameText, Pageable pageable);

    List<Subject> findAllByDeletedAndSubjectModules_NameContainingIgnoreCase(Boolean deleted, String moduleText);

    List<Subject> findAllByDeletedOrderByModifiedDateDesc(Boolean deleted, Pageable pageable);

    List<Subject> findAllByDeletedOrderByModifiedDateDesc(Boolean deleted);

    @Query(value = "SELECT DISTINCT * FROM subject inner join modules_subjects on modules_subjects.subject_id = subject.id " +
            "join module on modules_subjects.module_id = module.id WHERE module.name ILIKE %:pid% AND subject.deleted=:sdel order by modified_date desc", nativeQuery = true)
    List<Subject> findModulesContaining(@Param("pid") String nameText, @Param("sdel") Boolean deleted);

    List<Subject> findDistinctByDeletedAndSubjectModules_NameContainingIgnoreCaseOrderByModifiedDateDesc(Boolean deleted, String moduleText);

    @Query(value = "SELECT s FROM subject s INNER JOIN module m ON m.module_id = s.id WHERE psh.program_id = :pid", nativeQuery = true)
    List<String> GetSubjectsAndHoursInProgram(@Param("pid") Long id);
//    SELECT * FROM MyTable WHERE Column1 CONTAINS 'word1 And word2 And word3'
//    @Transactional

//    @Modifying
//    @Query(value = "INSERT INTO modules_subjects (module_id, subject_id) VALUES (:module_id, :subject_id)",
//            nativeQuery = true)
//    void addModuleToSubject(@Param("module_id") Long moduleId, @Param("subject_id") Long subjectId);
}
