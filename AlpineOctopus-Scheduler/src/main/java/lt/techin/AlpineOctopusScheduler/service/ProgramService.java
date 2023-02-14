package lt.techin.AlpineOctopusScheduler.service;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public List<ProgramEntityDto> getAllPrograms(){
       return programRepository.findAll().stream().map(ProgramMapper::toProgramEntityDto).collect(Collectors.toList());
    }

    public List<ProgramDto> getPagedAllPrograms(int page, int pageSize) {

        Pageable pageable = PageRequest.of(page, pageSize);

        return programRepository.findAll(pageable).stream().map(ProgramMapper::toProgramDto).collect(Collectors.toList());
    }

    public Optional<Program> getById(Long id) {
        return programRepository.findById(id);
    }
    @Transactional(readOnly = true)
    public List<ProgramsDtoForSearch> getProgramsByNameContaining(String nameText){
        return programRepository.findByNameContainingIgnoreCase(nameText);
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

    public boolean deleteById(Long id) {
        try {
            programRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException exception) {
            return false;
        }
    }

    public List<ProgramSubjectHoursDtoForList> getAllSubjectsInProgram(Long id) {
        List<String> subjectList = programRepository.GetSubjectsInProgram(id);
        List<ProgramSubjectHoursDtoForList> programSubjectHoursDtoForListsList = new ArrayList<>();
        for (String s: subjectList) {
            String[] subjectHours = s.split(",");
            programSubjectHoursDtoForListsList
                    .add(new ProgramSubjectHoursDtoForList(subjectRepository.findById(Long.parseLong(subjectHours[0])).get(),
                            Integer.parseInt(subjectHours[1])));
        }
return programSubjectHoursDtoForListsList;
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
