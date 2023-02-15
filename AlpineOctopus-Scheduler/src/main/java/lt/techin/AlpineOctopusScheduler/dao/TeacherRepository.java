package lt.techin.AlpineOctopusScheduler.dao;

import lt.techin.AlpineOctopusScheduler.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


//Mantvydas Juršys

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    // JPA is good stuff
}
