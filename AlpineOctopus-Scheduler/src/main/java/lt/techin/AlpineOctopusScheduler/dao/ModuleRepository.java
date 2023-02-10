package lt.techin.AlpineOctopusScheduler.dao;
import lt.techin.AlpineOctopusScheduler.model.Module;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuleRepository extends JpaRepository<Module, Long> {
}
