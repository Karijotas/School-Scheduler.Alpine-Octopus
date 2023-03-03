package lt.techin.AlpineOctopusScheduler.dao;

import lt.techin.AlpineOctopusScheduler.model.Teacher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {


    List<Teacher> findByNameContainingIgnoreCase(String nameText, Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO TEACHERS_SUBJECTS (TEACHER_ID, SUBJECT_ID) VALUES (:TEACHER_ID, :SUBJECT_ID)",
            nativeQuery = true)
    void insertTeacherAndSubject(@Param("TEACHER_ID") Long teacherId, @Param("SUBJECT_ID") Long subjectId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM TEACHERS_SUBJECTS WHERE TEACHER_ID= :TEACHER_ID AND SUBJECT_ID = :SUBJECT_ID",
            nativeQuery = true)
    void deleteTeacherFromSubject(@Param("TEACHER_ID") Long teacherId, @Param("SUBJECT_ID") Long subjectId);

    List<Teacher> findDistinctByDeletedAndTeacherShifts_NameContainingIgnoreCaseOrderByModifiedDateDesc(Boolean deleted, String shiftText);

    List<Teacher> findAllByDeletedOrderByModifiedDateDesc(Boolean deleted, Pageable pageable);

    List<Teacher> findAllByDeletedOrderByModifiedDateDesc(Boolean deleted);

    List<Teacher> findAllByDeletedAndNameContainingIgnoreCaseOrderByModifiedDateDesc(Boolean deleted, String nameText, Pageable pageable);

    List<Teacher> findAllByDeletedAndNameContainingIgnoreCaseOrderByModifiedDateDesc(Boolean deleted, String nameText);


}
