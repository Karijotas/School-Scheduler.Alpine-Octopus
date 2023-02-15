package lt.techin.AlpineOctopusScheduler.service;

import lt.techin.AlpineOctopusScheduler.api.dto.*;
import lt.techin.AlpineOctopusScheduler.api.dto.mapper.ProgramMapper;
import lt.techin.AlpineOctopusScheduler.dao.ProgramRepository;
import lt.techin.AlpineOctopusScheduler.dao.ProgramSubjectHoursRepository;
import lt.techin.AlpineOctopusScheduler.dao.SubjectRepository;
import lt.techin.AlpineOctopusScheduler.exception.SchedulerValidationException;
import lt.techin.AlpineOctopusScheduler.model.Program;
import lt.techin.AlpineOctopusScheduler.model.ProgramSubjectHours;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static lt.techin.AlpineOctopusScheduler.api.dto.mapper.ProgramSubjectHoursMapper.toProgramSubjectHours;

@Service
public class ProgramService {

    private final ProgramRepository programRepository;
    private final SubjectRepository subjectRepository;
    private final ProgramSubjectHoursRepository programSubjectHoursRepository;

    public ProgramService(ProgramRepository programRepository, SubjectRepository subjectRepository, ProgramSubjectHoursRepository programSubjectHoursRepository) {
        this.programRepository = programRepository;
        this.subjectRepository = subjectRepository;
        this.programSubjectHoursRepository = programSubjectHoursRepository;
    }

    public List<ProgramEntityDto> getAllPrograms() {
        return programRepository.findAll().stream().map(ProgramMapper::toProgramEntityDto).collect(Collectors.toList());
    }

    public List<ProgramEntityDto> getPagedAllPrograms(int page, int pageSize) {

        Pageable pageable = PageRequest.of(page, pageSize);

        return programRepository.findAll(pageable).stream().map(ProgramMapper::toProgramEntityDto).collect(Collectors.toList());
    }

    public Optional<Program> getById(Long id) {
        return programRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<ProgramEntityDto> getProgramsByNameContaining(String nameText) {
        return programRepository.findByNameContainingIgnoreCase(nameText).stream()
                .map(ProgramMapper::toProgramEntityDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProgramEntityDto> getPagedProgramsByNameContaining(String nameText, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return programRepository.findByNameContainingIgnoreCase(nameText, pageable).stream()
                .map(ProgramMapper::toProgramEntityDto).collect(Collectors.toList());
    }

    public Program create(Program program) {
        return programRepository.save(program);
    }

    public Program update(Long id, Program program) {
        var existingProgram = programRepository.findById(id)
                .orElseThrow(() -> new SchedulerValidationException("Program does not exist",
                        "id", "Program not found", id.toString()));

        existingProgram.setName(program.getName());
        existingProgram.setDescription(program.getDescription());
        existingProgram.setSubjectHours(program.getSubjectHours());

        return programRepository.save(existingProgram);
    }

    public ProgramSubjectHours updateProgramSubjectHours(Long id, ProgramSubjectHours programSubjectHours) {
        var existingProgramSubjectHours = programSubjectHoursRepository.findById(id)
                .orElseThrow(() -> new SchedulerValidationException("Subject and Hours in Program does not exist",
                        "id", "Program or Subject or Hours not found", id.toString()));
        existingProgramSubjectHours.setSubject(programSubjectHours.getSubject());
        existingProgramSubjectHours.setProgram(programSubjectHours.getProgram());
        existingProgramSubjectHours.setSubjectHours(programSubjectHours.getSubjectHours());

        return existingProgramSubjectHours;
    }

    public boolean deleteById(Long id) {
        try {
            programRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException exception) {
            return false;
        }
    }

    public boolean deleteSubjectInProgramById(Long id) {
        try {
            programSubjectHoursRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException exception) {
            return false;
        }
    }


    public List<ProgramSubjectHoursDto> getAllSubjectsInProgramByProgramId(Long id) {
        List<String> subjectList = programRepository.GetSubjectsAndHoursInProgram(id);
        List<ProgramSubjectHoursDto> programSubjectHoursDto = new ArrayList<>();
        for (String s : subjectList) {
            String[] subjectHours = s.split(",");
            programSubjectHoursDto
                    .add(new ProgramSubjectHoursDto(programRepository.findById(id).get(),
                            subjectRepository.findById(Long.parseLong(subjectHours[0])).get(),
                            Integer.parseInt(subjectHours[1])));
        }
        return programSubjectHoursDto;
    }
//    @Transactional
//    public boolean deleteSubjectInProgramById(Long programId, Long subjectId){
//        try{
//            programRepository.DeleteSubjectByIdInProgram(programId, subjectId);
//            return true;
//        } catch (EmptyResultDataAccessException exception) {
//            return false;
//        }
//    }

    public boolean deleteSubjectInProgramById(Long programId, Long subjectId, Integer hours) {
        try {
            programSubjectHoursRepository.deleteById(programSubjectHoursRepository.findAll().stream()
                    .filter(psh -> psh.getProgram()
                            .getId().equals(programId)).filter(psh -> psh.getSubject().getId().equals(subjectId))
                    .filter(psh -> psh.getSubjectHours() == hours)
                    .findFirst().get().getId().longValue());
            return true;
        } catch (EmptyResultDataAccessException exception) {
            return false;
        }
    }

    public boolean deleteSubjectInProgramBySubjectId(Long programId, Long subjectId) {
        try {
            programSubjectHoursRepository.deleteById(programSubjectHoursRepository.findAll().stream()
                    .filter(psh -> psh.getProgram()
                            .getId().equals(programId)).filter(psh -> psh.getSubject().getId().equals(subjectId))
                    .findFirst().get().getId().longValue());
            return true;
        } catch (EmptyResultDataAccessException exception) {
            return false;
        }
    }

    public ProgramSubjectHours addSubjectAndHoursToProgram(Long programId, Long subjectId, Integer subjectHours) {
        var existingProgram = programRepository.findById(programId)
                .orElseThrow(() -> new SchedulerValidationException("Program does not exist",
                        "id", "Program not found", programId.toString()));
        var existingSubject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new SchedulerValidationException("Subject does not exist",
                        "id", "Subject not found", subjectId.toString()));
        var newProgramSubjectHoursDto = new ProgramSubjectHoursDto(existingProgram, existingSubject, subjectHours);
        return programSubjectHoursRepository.save(toProgramSubjectHours(newProgramSubjectHoursDto));
    }


}
