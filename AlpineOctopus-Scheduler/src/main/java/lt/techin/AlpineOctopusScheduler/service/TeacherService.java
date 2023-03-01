package lt.techin.AlpineOctopusScheduler.service;

import lt.techin.AlpineOctopusScheduler.api.dto.TeacherEntityDto;
import lt.techin.AlpineOctopusScheduler.api.dto.mapper.TeacherMapper;
import lt.techin.AlpineOctopusScheduler.dao.ShiftRepository;
import lt.techin.AlpineOctopusScheduler.dao.SubjectRepository;
import lt.techin.AlpineOctopusScheduler.dao.TeacherRepository;
import lt.techin.AlpineOctopusScheduler.exception.SchedulerValidationException;
import lt.techin.AlpineOctopusScheduler.model.Shift;
import lt.techin.AlpineOctopusScheduler.model.Subject;
import lt.techin.AlpineOctopusScheduler.model.Teacher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

//Mantvydas Juršys


@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;
    private final ShiftRepository shiftRepository;
    private final Validator validator;

    public TeacherService(TeacherRepository teacherRepository, SubjectRepository subjectRepository, ShiftRepository shiftRepository, Validator validator) {
        this.teacherRepository = teacherRepository;
        this.subjectRepository = subjectRepository;
        this.shiftRepository = shiftRepository;
        this.validator = validator;
    }

    void validateInputWithInjectedValidator(Teacher teacher) {
        Set<ConstraintViolation<Teacher>> violations = validator.validate(teacher);
        if (!violations.isEmpty()) {
            throw new SchedulerValidationException(violations.toString(), "Teacher", "Error in teacher entity", teacher.toString());
        }
    }

    public boolean loginEmailIsUnique(Teacher teacher) {
        return teacherRepository.findAll()
                .stream()
                .noneMatch(teacher1 -> teacher1.getLoginEmail().equals(teacher.getLoginEmail()));
    }

    public List<Teacher> getAll() {
        return teacherRepository.findAll();
    }

    public Optional<Teacher> getById(Long id) {
        return teacherRepository.findById(id);
    }

    public List<Teacher> getPagedAllTeachers(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return teacherRepository.findAll(pageable).stream().sorted(Comparator.comparing(Teacher::getModifiedDate).reversed()).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TeacherEntityDto> getTeachersByNameContaining(String nameText) {
        return teacherRepository.findByNameContainingIgnoreCase(nameText).stream().sorted(Comparator.comparing(Teacher::getModifiedDate).reversed())
                .map(TeacherMapper::toTeacherEntityDto).collect(Collectors.toList());
    }

    public Teacher create(Teacher teacher) {

        validateInputWithInjectedValidator(teacher);
        return teacherRepository.save(teacher);
    }

    public Teacher update(Long id, Teacher teacher) {

        validateInputWithInjectedValidator(teacher);
        var existingTeacher = teacherRepository.findById(id)
                .orElseThrow(() -> new SchedulerValidationException("Teacher does not exist",
                        "id", "Teacher not found", id.toString()));

        existingTeacher.setName(teacher.getName());
        existingTeacher.setLoginEmail(teacher.getLoginEmail());
        existingTeacher.setContactEmail(teacher.getContactEmail());
        existingTeacher.setPhone(teacher.getPhone());
        existingTeacher.setWorkHoursPerWeek(teacher.getWorkHoursPerWeek());
        existingTeacher.setTeacherShifts(teacher.getTeacherShifts());

        return teacherRepository.save(existingTeacher);
    }

    public boolean deleteById(Long id) {
        if (teacherRepository.existsById(id)) {
            teacherRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Set<Shift> getAllShiftsById(Long teacherId) {
        return teacherRepository.findById(teacherId).get().getTeacherShifts();
    }

    public List<Subject> getAllSubjectsById(Long teacherId) {
        return subjectRepository.findAllBySubjectTeachers_Id(teacherId);
    }

    public Teacher addShiftToTeacher(Long teacherId, Long shiftId) {
        var existingTeacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new SchedulerValidationException("Teacher does not exist",
                        "id", "Teacher not found", teacherId.toString()));

        var existingShift = shiftRepository.findById(shiftId)
                .orElseThrow(() -> new SchedulerValidationException("Shift does not exist",
                        "id", "Shift not found", shiftId.toString()));

        Set<Shift> existingShiftList = existingTeacher.getTeacherShifts();
        existingShiftList.add(existingShift);
        existingTeacher.setTeacherShifts(existingShiftList);

        return teacherRepository.save(existingTeacher);
    }

    public boolean deleteShiftInTeacherById(Long teacherId, Long shiftId) {
        try {
            var existingTeacher = teacherRepository.findById(teacherId).get();
            existingTeacher.getTeacherShifts().remove(shiftRepository.findById(shiftId).get());
            teacherRepository.save(existingTeacher);
            return true;
        } catch (EmptyResultDataAccessException exception) {
            return false;
        }
    }

//    public Teacher addSubjectToTeacher(Long teacherId, Long subjectId) {
//        var existingTeacher = teacherRepository.findById(teacherId)
//                .orElseThrow(() -> new SchedulerValidationException("Teacher does not exist",
//                        "id", "Teacher not found", teacherId.toString()));
//
//        var existingSubject = subjectRepository.findById(subjectId)
//                .orElseThrow(() -> new SchedulerValidationException("Subject does not exist",
//                        "id", "Subject not found", subjectId.toString()));
//
//        Set<Subject> existingSubjectList = existingTeacher.getTeachersSubjects();
//        existingSubjectList.add(existingSubject);
//        existingTeacher.setTeachersSubjects(existingSubjectList);
//
//        return teacherRepository.save(existingTeacher);

    public void addSubjectToTeacher(Long teacherId, Long subjectId) {
        var existingTeacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new SchedulerValidationException("Teacher does not exist",
                        "id", "Teacher not found", teacherId.toString()));

        var existingSubject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new SchedulerValidationException("Subject does not exist",
                        "id", "Subject not found", subjectId.toString()));

        teacherRepository.insertTeacherAndSubject(teacherId, subjectId);
    }


    public boolean deleteSubjectFromTeacherById(Long teacherId, Long subjectId) {
        var existingTeacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new SchedulerValidationException("Teacher does not exist",
                        "id", "Teacher not found", teacherId.toString()));

        var existingSubject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new SchedulerValidationException("Subject does not exist",
                        "id", "Subject not found", subjectId.toString()));

        teacherRepository.deleteTeacherFromSubject(teacherId, subjectId);

        if (subjectRepository.existsById(subjectId)) {
            subjectRepository.deleteById(subjectId);
            return true;
        }

        return false;
    }

    @Transactional(readOnly = true)
    public List<TeacherEntityDto> getPagedTeachersByShiftNameContaining(String shiftText) {
        return teacherRepository.findDistinctByDeletedAndTeacherShifts_NameContainingIgnoreCaseOrderByModifiedDateDesc(Boolean.FALSE, shiftText).stream().map(TeacherMapper::toTeacherEntityDto).collect(Collectors.toList());
    }
}