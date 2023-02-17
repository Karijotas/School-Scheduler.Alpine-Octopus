package lt.techin.AlpineOctopusScheduler.dao;


import lt.techin.AlpineOctopusScheduler.model.ProgramSubjectHoursList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProgramSubjectHourListRepository extends JpaRepository<ProgramSubjectHoursList, Long> {
}
