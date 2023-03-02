package lt.techin.AlpineOctopusScheduler.service;

import lt.techin.AlpineOctopusScheduler.api.dto.ModuleEntityDto;
import lt.techin.AlpineOctopusScheduler.api.dto.ModuleTestDto;
import lt.techin.AlpineOctopusScheduler.api.dto.mapper.ModuleMapper;
import lt.techin.AlpineOctopusScheduler.dao.ModuleRepository;
import lt.techin.AlpineOctopusScheduler.dao.SubjectRepository;
import lt.techin.AlpineOctopusScheduler.exception.SchedulerValidationException;
import lt.techin.AlpineOctopusScheduler.model.Module;
import lt.techin.AlpineOctopusScheduler.model.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static lt.techin.AlpineOctopusScheduler.api.dto.mapper.ModuleMapper.toModuleEntityDto;

@Service

public class ModuleService {


    private final ModuleRepository moduleRepository;

    private final SubjectRepository subjectRepository;

    private final Validator validator;

    @Autowired
    public ModuleService(ModuleRepository moduleRepository, SubjectRepository subjectRepository, Validator validator) {
        this.moduleRepository = moduleRepository;
        this.subjectRepository = subjectRepository;
        this.validator = validator;
    }

    void validateInputWithInjectedValidator(Module module) {
        Set<ConstraintViolation<Module>> violations = validator.validate(module);
        if (!violations.isEmpty()) {
            throw new SchedulerValidationException(violations.toString(), "Module", "Error in module entity", module.toString());
        }
    }

    public boolean moduleNameIsUnique(Module module) {
        return moduleRepository.findAll()
                .stream()
                .noneMatch(module1 -> module1.getName().equals(module.getName()));
    }

//    public List<ModuleEntityDto> getAllModules() {
//        return moduleRepository.findAll().stream().map(ModuleMapper::toModuleEntityDto).collect(Collectors.toList());
//    }

    public List<ModuleTestDto> getAllModules() {
        return moduleRepository.findAll().stream()
                .map(ModuleMapper::toModuleTestDto).collect(Collectors.toList());
    }

    public List<Module> getAll() {

        return moduleRepository.findAll();
    }

    public Optional<Module> getById(Long id) {

        return moduleRepository.findById(id);
    }


    public Module create(Module module) {
        validateInputWithInjectedValidator(module);
        return moduleRepository.save(module);
    }

    public Module update(Long id, Module module) {
        validateInputWithInjectedValidator(module);
        var existingModule = moduleRepository.findById(id)
                .orElseThrow(() -> new SchedulerValidationException("Module does not exist",
                        "id", "Module not found", id.toString()));

        existingModule.setName(module.getName());
        existingModule.setDescription(module.getDescription());
        existingModule.setCreatedDate(module.getCreatedDate());
        existingModule.setModifiedDate(module.getModifiedDate());

        return moduleRepository.save(existingModule);
    }

    public Boolean deleteById(Long id) {
        try {
            moduleRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    @Transactional(readOnly = true)
    public List<ModuleEntityDto> getPagedModulesByNameContaining(String nameText, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("modifiedDate"));
        return moduleRepository.findAllByDeletedAndNameContainingIgnoreCaseOrderByModifiedDateDesc(Boolean.FALSE, nameText, pageable).stream()
//                .sorted(Comparator.comparing(Module::getModifiedDate).reversed())
                .map(ModuleMapper::toModuleEntityDto).collect(Collectors.toList());
    }

    public List<ModuleEntityDto> getPagedAllModules(int page, int pageSize) {

        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("modifiedDate"));

        return moduleRepository.findAll(pageable).stream()
//                .sorted(Comparator.comparing(Module::getModifiedDate).reversed())
                .map(ModuleMapper::toModuleEntityDto).collect(Collectors.toList());
    }


    public List<Subject> getAllSubjectsById(Long moduleId) {
        return subjectRepository.findAllBySubjectModules_Id(moduleId);
    }

    public boolean deleteSubjectInModuleById(Long moduleId, Long subjectId) {
        try {
            var existingModule = moduleRepository.findById(moduleId).get();
            var existingSubject = subjectRepository.findAllBySubjectModules_Id(existingModule.getId())
                    .remove(subjectId);
//            existingModule.getSubjectModules().remove(moduleRepository.findById(moduleId).get());
            moduleRepository.save(existingModule);
            return true;
        } catch (EmptyResultDataAccessException exception) {
            return false;
        }

    }

    public List<ModuleEntityDto> getAllAvailablePagedModules(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return moduleRepository.findAllByDeletedOrderByModifiedDateDesc(Boolean.FALSE, pageable).stream()
                .map(ModuleMapper::toModuleEntityDto).collect(Collectors.toList());
    }

    public List<ModuleEntityDto> getAllDeletedPagedModules(int page, int pageSize) {

        Pageable pageable = PageRequest.of(page, pageSize);

        return moduleRepository.findAllByDeletedOrderByModifiedDateDesc(Boolean.TRUE, pageable).stream()
                .map(ModuleMapper::toModuleEntityDto).collect(Collectors.toList());
    }

    public List<ModuleEntityDto> getAllAvailableModules() {
        return moduleRepository.findAllByDeletedOrderByModifiedDateDesc(Boolean.FALSE).stream()
                .map(ModuleMapper::toModuleEntityDto).collect(Collectors.toList());
    }

    public List<ModuleEntityDto> getAllDeletedModules() {
        return moduleRepository.findAllByDeletedOrderByModifiedDateDesc(Boolean.TRUE).stream()
                .map(ModuleMapper::toModuleEntityDto).collect(Collectors.toList());
    }

    public ModuleEntityDto restoreModule(Long moduleId) {

        var existingModule = moduleRepository.findById(moduleId).orElseThrow(() -> new SchedulerValidationException("Module does not exist",
                "id", "Module not found", moduleId.toString()));
        existingModule.setDeleted(Boolean.FALSE);
        moduleRepository.save(existingModule);
        return toModuleEntityDto(existingModule);
    }

    public ModuleEntityDto deleteModule(Long moduleId) {

        var existingModule = moduleRepository.findById(moduleId).orElseThrow(() -> new SchedulerValidationException("Module does not exist",
                "id", "Module not found", moduleId.toString()));
        existingModule.setDeleted(Boolean.TRUE);
        moduleRepository.save(existingModule);
        return toModuleEntityDto(existingModule);
    }

    public void addSubjectToModule(Long moduleId, Long subjectId) {
        var existingModule = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new SchedulerValidationException("Module does not exist",
                        "id", "Module not found", moduleId.toString()));

        var existingSubject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new SchedulerValidationException("Subject does not exist",
                        "id", "Subject not found", subjectId.toString()));

        moduleRepository.insertModuleAndSubject(moduleId, subjectId);
    }
    

    public boolean deleteSubjectFromModuleById(Long moduleId, Long subjectId) {
        var existingModule = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new SchedulerValidationException("Module does not exist",
                        "id", "Module not found", moduleId.toString()));

        var existingSubject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new SchedulerValidationException("Subject does not exist",
                        "id", "Subject not found", subjectId.toString()));

        moduleRepository.deleteModuleFromSubject(moduleId, subjectId);

        if (subjectRepository.existsById(subjectId)) {
            subjectRepository.deleteById(subjectId);
            return true;
        }

        return false;
    }
}


