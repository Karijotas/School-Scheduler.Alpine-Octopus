package lt.techin.AlpineOctopusScheduler.dao;

import lt.techin.AlpineOctopusScheduler.model.Module;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ModuleRepository extends JpaRepository<Module, Long> {

    List<Module> findByNameContainingIgnoreCase(String nameText, Pageable pageable);

    List<Module> findByNameContainingIgnoreCase(String nameText);

    List<Module> findAllByDeletedOrderByModifiedDateDesc(Boolean deleted, Pageable pageable);

    List<Module> findAllByDeletedAndNameContainingIgnoreCaseOrderByModifiedDateDesc(Boolean deleted, String nameText, Pageable pageable);

    List<Module> findAllByDeletedOrderByModifiedDateDesc(Boolean deleted);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO MODULES_SUBJECTS (MODULE_ID, SUBJECT_ID) VALUES (:MODULE_ID, :SUBJECT_ID)",
            nativeQuery = true)
    void insertModuleAndSubject(@Param("MODULE_ID") Long moduleId, @Param("SUBJECT_ID") Long subjectId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM MODULES_SUBJECTS WHERE MODULE_ID= :MODULE_ID AND SUBJECT_ID = :SUBJECT_ID",
            nativeQuery = true)
    void deleteModuleFromSubject(@Param("MODULE_ID") Long moduleId, @Param("SUBJECT_ID") Long subjectId);

}
