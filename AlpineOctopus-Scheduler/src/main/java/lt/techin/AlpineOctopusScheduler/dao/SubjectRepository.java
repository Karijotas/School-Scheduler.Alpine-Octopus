package lt.techin.AlpineOctopusScheduler.dao;

import lt.techin.AlpineOctopusScheduler.model.Subject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;


@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

    List<Subject> findByNameContainingIgnoreCase(String nameText, Pageable pageable);

//    List<Subject> findByOrderByModifiedDateDesc();





//    @Transactional
//    @Modifying
//    @Query(value = "INSERT INTO modules_subjects (module_id, subject_id) VALUES (:module_id, :subject_id)",
//            nativeQuery = true)
//    void addModuleToSubject(@Param("module_id") Long moduleId, @Param("subject_id") Long subjectId);
}
