package lt.techin.AlpineOctopusScheduler.dao;

import lt.techin.AlpineOctopusScheduler.model.Module;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModuleRepository extends JpaRepository<Module, Long> {

    List<Module> findByNameContainingIgnoreCaseOrderByModifiedDateDesc(String nameText, Pageable pageable);

    List<Module> findByNameContainingIgnoreCase(String nameText);

    List<Module> findAllByDeletedOrderByModifiedDateDesc(Boolean deleted, Pageable pageable);

    List<Module> findAllByDeletedAndNameContainingIgnoreCaseOrderByModifiedDateDesc(Boolean deleted, String nameText, Pageable pageable);

    List<Module> findAllByDeletedOrderByModifiedDateDesc(Boolean deleted);

}
