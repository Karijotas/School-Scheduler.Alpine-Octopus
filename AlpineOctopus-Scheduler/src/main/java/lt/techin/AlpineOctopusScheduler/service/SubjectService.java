package lt.techin.AlpineOctopusScheduler.service;


import lt.techin.AlpineOctopusScheduler.api.dto.ProgramEntityDto;
import lt.techin.AlpineOctopusScheduler.api.dto.mapper.ProgramMapper;
import lt.techin.AlpineOctopusScheduler.dao.ProgramRepository;

import lt.techin.AlpineOctopusScheduler.api.dto.SubjectEntityDto;
import lt.techin.AlpineOctopusScheduler.api.dto.mapper.SubjectMapper;

import lt.techin.AlpineOctopusScheduler.dao.ModuleRepository;
import lt.techin.AlpineOctopusScheduler.dao.RoomRepository;

import lt.techin.AlpineOctopusScheduler.dao.SubjectRepository;
import lt.techin.AlpineOctopusScheduler.dao.TeacherRepository;
import lt.techin.AlpineOctopusScheduler.exception.SchedulerValidationException;

import lt.techin.AlpineOctopusScheduler.model.*;

import lt.techin.AlpineOctopusScheduler.model.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;

    private final ModuleRepository moduleRepository;

    private final TeacherRepository teacherRepository;

    private final RoomRepository roomRepository;

    private final ProgramRepository programRepository;

    @Autowired
    public SubjectService(SubjectRepository subjectRepository, ModuleRepository moduleRepository, TeacherRepository teacherRepository, RoomRepository roomRepository, ProgramRepository programRepository) {
        this.subjectRepository = subjectRepository;
        this.moduleRepository = moduleRepository;
        this.teacherRepository = teacherRepository;
        this.roomRepository = roomRepository;
        this.programRepository = programRepository;
    }

    public List<Subject> getAll() {
        return subjectRepository.findAll();
    }

    public List<SubjectEntityDto> getPagedAllSubjects(int page, int pageSize) {

        Pageable pageable = PageRequest.of(page, pageSize);

        return subjectRepository.findAll(pageable).stream().map(SubjectMapper::toSubjectEntityDto).collect(Collectors.toList());
    }



    public Optional<Subject> getById(Long id) {
        return subjectRepository.findById(id);
    }


    @Transactional(readOnly = true)
    public List<SubjectEntityDto> getPagedSubjectsByNameContaining(String nameText, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return subjectRepository.findByNameContainingIgnoreCase(nameText, pageable).stream()
                .map(SubjectMapper::toSubjectEntityDto).collect(Collectors.toList());
    }

    public Subject create(Subject subject) {
        return subjectRepository.save(subject);
    }

    public Subject update(Long id, Subject subject) {
        var existingSubject = subjectRepository.findById(id)
                .orElseThrow(() -> new SchedulerValidationException("Subject does not exist",
                        "id", "Subject not found", id.toString()));

        existingSubject.setName(subject.getName());
        existingSubject.setDescription(subject.getDescription());
        existingSubject.setCreatedDate(subject.getCreatedDate());
        existingSubject.setModifiedDate(subject.getModifiedDate());

        return subjectRepository.save(existingSubject);
    }

    public Boolean deleteById(Long id){
        try{
            subjectRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e){
            return false;
        }
    }

    public Subject addModuleToSubject(Long subjectId, Long moduleId) {
        var existingSubject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new SchedulerValidationException("Subject does not exist",
                        "id", "Subject not found", subjectId.toString()));

        var existingModule = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new SchedulerValidationException("Module does not exist",
                        "id", "Module not found", moduleId.toString()));

        Set<Module> existingModuleList = existingSubject.getSubjectModules();
        existingModuleList.add(existingModule);
        existingSubject.setSubjectModules(existingModuleList);

        return subjectRepository.save(existingSubject);
    }

    public Subject addTeacherToSubject(Long subjectId, Long teacherId) {
        var existingSubject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new SchedulerValidationException("Subject does not exist",
                        "id", "Subject not found", subjectId.toString()));

        var existingTeacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new SchedulerValidationException("Teacher does not exist",
                        "id", "Teacher not found", teacherId.toString()));

        Set<Teacher> existingTeacherList = existingSubject.getSubjectTeachers();
        existingTeacherList.add(existingTeacher);
        existingSubject.setSubjectTeachers(existingTeacherList);

        return subjectRepository.save(existingSubject);
    }

    public Subject addRoomToSubject(Long subjectId, Long roomId) {
        var existingSubject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new SchedulerValidationException("Subject does not exist",
                        "id", "Subject not found", subjectId.toString()));

        var existingRoom = roomRepository.findById(roomId)
                .orElseThrow(() -> new SchedulerValidationException("Room does not exist",
                        "id", "Room not found", roomId.toString()));

        Set<Room> existingRoomList = existingSubject.getSubjectRooms();
        existingRoomList.add(existingRoom);
        existingSubject.setSubjectRooms(existingRoomList);

        return subjectRepository.save(existingSubject);
    }

  public Set<Module> getAllModulesById (Long subjectId){
        return subjectRepository.findById(subjectId).get().getSubjectModules();
  }

    public List<SubjectEntityDto> getAllAvailablePagedSubjects(int page, int pageSize){
        Pageable pageable = PageRequest.of(page, pageSize);
        return subjectRepository.findAllByDeletedOrderByModifiedDateDesc(Boolean.FALSE, pageable).stream()
                .map(SubjectMapper::toSubjectEntityDto).collect(Collectors.toList());
    }
    public List<SubjectEntityDto> getAllDeletedPagedSubjects(int page, int pageSize) {

        Pageable pageable = PageRequest.of(page, pageSize);

        return subjectRepository.findAllByDeletedOrderByModifiedDateDesc(Boolean.TRUE, pageable).stream()
                .map(SubjectMapper::toSubjectEntityDto).collect(Collectors.toList());
    }

    public List<SubjectEntityDto> getAllAvailableSubjects(){
        return subjectRepository.findAllByDeletedOrderByModifiedDateDesc(Boolean.FALSE).stream()
                .map(SubjectMapper::toSubjectEntityDto).collect(Collectors.toList());
    }

    public List<SubjectEntityDto> getAllDeletedSubjects(){
        return subjectRepository.findAllByDeletedOrderByModifiedDateDesc(Boolean.TRUE).stream()
                .map(SubjectMapper::toSubjectEntityDto).collect(Collectors.toList());
    }

    public Subject restoreSubject(Long subjectId){
        var existingSubject = subjectRepository.findById(subjectId).orElseThrow(() -> new SchedulerValidationException("Subject does not exist",
                "id", "Subject not found", subjectId.toString()));
        existingSubject.setDeleted(Boolean.FALSE);
        subjectRepository.save(existingSubject);
        return existingSubject;
    }

    public Subject deleteSubject(Long subjectId){
        var existingSubject = subjectRepository.findById(subjectId).orElseThrow(() -> new SchedulerValidationException("Subject does not exist",
                "id", "Subject not found", subjectId.toString()));
        existingSubject.setDeleted(Boolean.TRUE);
        subjectRepository.save(existingSubject);
        return existingSubject;
    }

}
