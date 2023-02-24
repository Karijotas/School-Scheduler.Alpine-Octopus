package lt.techin.AlpineOctopusScheduler.service;
import lt.techin.AlpineOctopusScheduler.api.dto.ModuleEntityDto;
import lt.techin.AlpineOctopusScheduler.api.dto.SubjectEntityDto;
import lt.techin.AlpineOctopusScheduler.api.dto.mapper.ModuleMapper;
import lt.techin.AlpineOctopusScheduler.api.dto.mapper.SubjectMapper;
import lt.techin.AlpineOctopusScheduler.dao.SubjectRepository;
import lt.techin.AlpineOctopusScheduler.exception.SchedulerValidationException;
import lt.techin.AlpineOctopusScheduler.model.Module;
import lt.techin.AlpineOctopusScheduler.dao.ModuleRepository;
import lt.techin.AlpineOctopusScheduler.model.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Table;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service

public class ModuleService {


    private final ModuleRepository moduleRepository;

    private final SubjectRepository subjectRepository;
    @Autowired
    public ModuleService(ModuleRepository moduleRepository,
                         SubjectRepository subjectRepository) {

        this.moduleRepository = moduleRepository;
        this.subjectRepository = subjectRepository;
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
        var existingModule = moduleRepository.findById(id)
                .orElseThrow(() -> new SchedulerValidationException("Module does not exist",
                        "id", "Module not found", id.toString()));

        existingModule.setName(module.getName());
        existingModule.setDescription(module.getDescription());
        existingModule.setCreatedDate(module.getCreatedDate());
        existingModule.setModifiedDate(module.getModifiedDate());

        return moduleRepository.save(existingModule);
    }

    public Boolean deleteById(Long id){
        try{
            moduleRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e){
            return false;
        }
    }

    @Transactional(readOnly = true)
    public List<ModuleEntityDto> getPagedModulesByNameContaining(String nameText, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("modifiedDate"));
        return moduleRepository.findByNameContainingIgnoreCase(nameText, pageable).stream()
//                .sorted(Comparator.comparing(Module::getModifiedDate).reversed())
                .map(ModuleMapper::toModuleEntityDto).collect(Collectors.toList());
    }

    public List<ModuleEntityDto> getPagedAllModules(int page, int pageSize) {

        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("modifiedDate"));

        return moduleRepository.findAll(pageable).stream()
//                .sorted(Comparator.comparing(Module::getModifiedDate).reversed())
                .map(ModuleMapper::toModuleEntityDto).collect(Collectors.toList());
    }

//    public Module addSubjectToModule(Long moduleId, Long subjectId) {
//        var existingModule = moduleRepository.findById(moduleId)
//                .orElseThrow(() -> new SchedulerValidationException("Module does not exist",
//                        "id", "Module not found", moduleId.toString()));
//
//        var existingSubject = subjectRepository.findById(subjectId)
//                .orElseThrow(() -> new SchedulerValidationException("Subject does not exist",
//                        "id", "Subject not found", subjectId.toString()));
//
//        Set<Subject> existingSubjectList = existingModule.getModulesSubjects();
//        existingSubjectList.add(existingSubject);
//        existingModule.setModulesSubjects(existingSubjectList);
//
//        return moduleRepository.save(existingModule);
//    }

//    public Set<Subject> getAllSubjectsById (Long moduleId){
//        return moduleRepository.findById(moduleId).get().getModulesSubjects();
//    }

//    public boolean deleteSubjectFromModuleById(Long moduleId, Long subjectId) {
//        var existingModule = moduleRepository.findById(moduleId)
//                .orElseThrow(() -> new SchedulerValidationException("Module does not exist",
//                        "id", "Module not found", moduleId.toString()));
//
//        var existingSubject = subjectRepository.findById(subjectId)
//                .orElseThrow(() -> new SchedulerValidationException("Subject does not exist",
//                        "id", "Subject not found", subjectId.toString()));
//
//        Set<Subject> existingSubjectList = existingModule.getModulesSubjects();
//        subjectRepository.deleteAll(existingSubjectList);
//        existingModule.setModulesSubjects(existingSubjectList);
//
//        if (subjectRepository.existsById(subjectId)) {
//            subjectRepository.deleteById(subjectId);
//            return true;
//        }
//
//        return false;
//    }
}


