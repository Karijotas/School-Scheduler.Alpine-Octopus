package lt.techin.AlpineOctopusScheduler.service;

import lt.techin.AlpineOctopusScheduler.dao.ModuleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ModuleService {


    private final ModuleRepository moduleRepository;

    public ModuleService(ModuleRepository moduleRepository) {

        this.moduleRepository = moduleRepository;
    }

    public List<Module> getAll() {

        return moduleRepository.findAll();
    }

    public Optional<Module> getById(Long id) {

        return moduleRepository.findById(id);
    }


    public Module create(Module module) {

        return moduleRepository.save(module);
    }

    public Module update(Long id, Module module) {
        module.setId(id);//FIXME will improve later

        return moduleRepository.save(module);
    }

    public boolean deleteById(Long id) {
        if (moduleRepository.existsById(id)) {
            moduleRepository.deleteById(id);
            return true;
        }

        return false;
    }
}


