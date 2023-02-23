package lt.techin.AlpineOctopusScheduler.service;


import lt.techin.AlpineOctopusScheduler.api.dto.SubjectDto;
import lt.techin.AlpineOctopusScheduler.api.dto.mapper.ModuleMapper;
import lt.techin.AlpineOctopusScheduler.dao.ProgramRepository;

import lt.techin.AlpineOctopusScheduler.api.dto.SubjectEntityDto;
import lt.techin.AlpineOctopusScheduler.api.dto.mapper.SubjectMapper;

import lt.techin.AlpineOctopusScheduler.dao.ModuleRepository;
import lt.techin.AlpineOctopusScheduler.dao.RoomRepository;

import lt.techin.AlpineOctopusScheduler.dao.SubjectRepository;
import lt.techin.AlpineOctopusScheduler.dao.TeacherRepository;
import lt.techin.AlpineOctopusScheduler.exception.SchedulerValidationException;

import lt.techin.AlpineOctopusScheduler.model.Module;
import lt.techin.AlpineOctopusScheduler.model.Room;

import lt.techin.AlpineOctopusScheduler.model.Subject;
import lt.techin.AlpineOctopusScheduler.model.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
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

        return subjectRepository.findAll(pageable).stream().sorted(Comparator.comparing(Subject::getModifiedDate).reversed()).
                map(SubjectMapper::toSubjectEntityDto).collect(Collectors.toList());
    }


    public Optional<Subject> getById(Long id) {
        return subjectRepository.findById(id);
    }


    @Transactional(readOnly = true)
    public List<SubjectEntityDto> getPagedSubjectsByNameContaining(String nameText, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return subjectRepository.findByNameContainingIgnoreCase(nameText, pageable).stream().sorted(Comparator.comparing(Subject::getModifiedDate).reversed())
                .map(SubjectMapper::toSubjectEntityDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<SubjectEntityDto> getPagedSubjectsByNameAndByModuleContaining(String nameText,String moduleText, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        var createdModule = moduleRepository.findByNameContainingIgnoreCase(moduleText, pageable).stream()
                .findFirst();
        return subjectRepository.findAll().stream().
                filter(subjects -> subjects.getSubjectModules().contains(createdModule)).sorted(Comparator.comparing(Subject::getModifiedDate).reversed())
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

    public Boolean deleteById(Long id) {
        try {
            subjectRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
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

    public Set<Module> getAllModulesById(Long subjectId) {
        return subjectRepository.findById(subjectId).get().getSubjectModules();
    }

    public Set<Room> getAllRoomsById(Long subjectId) {
        return subjectRepository.findById(subjectId).get().getSubjectRooms();
    }

    public Set<Teacher> getAllTeachersById(Long subjectId) {
        return subjectRepository.findById(subjectId).get().getSubjectTeachers();
    }

    public boolean deleteModuleInSubjectById(Long subjectId, Long moduleId) {
        try {
            var existingSubject = subjectRepository.findById(subjectId).get();
            existingSubject.getSubjectModules().remove(moduleRepository.findById(moduleId).get());
            subjectRepository.save(existingSubject);
            return true;
        } catch (EmptyResultDataAccessException exception) {
            return false;
        }

    }

    public boolean deleteTeacherInSubjectById(Long subjectId, Long teacherId) {
        try {
            var existingSubject = subjectRepository.findById(subjectId).get();
            existingSubject.getSubjectTeachers().remove(teacherRepository.findById(teacherId).get());
            subjectRepository.save(existingSubject);
            return true;
        } catch (EmptyResultDataAccessException exception) {
            return false;
        }

    }

    public boolean deleteRoomInSubjectById(Long subjectId, Long roomId) {
        try {
            var existingSubject = subjectRepository.findById(subjectId).get();
            existingSubject.getSubjectRooms().remove(roomRepository.findById(roomId).get());
            subjectRepository.save(existingSubject);
            return true;
        } catch (EmptyResultDataAccessException exception) {
            return false;
        }

    }
}
