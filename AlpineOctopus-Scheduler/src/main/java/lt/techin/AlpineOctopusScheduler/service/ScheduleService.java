package lt.techin.AlpineOctopusScheduler.service;

import lt.techin.AlpineOctopusScheduler.api.dto.ScheduleEntityDto;
import lt.techin.AlpineOctopusScheduler.api.dto.ScheduleTestDto;
import lt.techin.AlpineOctopusScheduler.api.dto.mapper.LessonMapper;
import lt.techin.AlpineOctopusScheduler.api.dto.mapper.ScheduleMapper;
import lt.techin.AlpineOctopusScheduler.dao.*;
import lt.techin.AlpineOctopusScheduler.exception.SchedulerValidationException;
import lt.techin.AlpineOctopusScheduler.model.Lesson;
import lt.techin.AlpineOctopusScheduler.model.ProgramSubjectHours;
import lt.techin.AlpineOctopusScheduler.model.Schedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    public static Logger logger = LoggerFactory.getLogger(ScheduleService.class);

    private final ScheduleSubjectsRepository scheduleSubjectsRepository;

    private final GroupsRepository groupsRepository;
    private final ShiftRepository shiftRepository;
    private final ProgramRepository programRepository;
    private final ProgramSubjectHoursRepository programSubjectHoursRepository;
    private final RoomRepository roomRepository;
    private final TeacherRepository teacherRepository;
    private final ScheduleLessonsRepository scheduleLessonsRepository;
    private final LessonRepository lessonRepository;
    private final HolidayRepository holidayRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, ScheduleSubjectsRepository scheduleSubjectsRepository,
                           GroupsRepository groupsRepository,
                           ShiftRepository shiftRepository,
                           ProgramRepository programRepository,
                           ProgramSubjectHoursRepository programSubjectHoursRepository,
                           RoomRepository roomRepository,
                           TeacherRepository teacherRepository,
                           ScheduleLessonsRepository scheduleLessonsRepository,
                           LessonRepository lessonRepository,
                           HolidayRepository holidayRepository) {
        this.scheduleRepository = scheduleRepository;
        this.scheduleSubjectsRepository = scheduleSubjectsRepository;
        this.groupsRepository = groupsRepository;
        this.shiftRepository = shiftRepository;
        this.programRepository = programRepository;
        this.programSubjectHoursRepository = programSubjectHoursRepository;
        this.roomRepository = roomRepository;
        this.teacherRepository = teacherRepository;
        this.scheduleLessonsRepository = scheduleLessonsRepository;
        this.lessonRepository = lessonRepository;
        this.holidayRepository = holidayRepository;
    }


    public boolean lessonIsNotPlanned(Long scheduleId, LocalDateTime startTime, LocalDateTime endTime) {
        var existingSchedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new SchedulerValidationException("Schedule does not exist", "id", "Schedule not found", scheduleId.toString()));


        return existingSchedule.getLessons()
                .stream()
                .noneMatch(lesson -> lesson.getStartTime().equals(startTime) || lesson.getEndTime().equals(endTime)
                        || (lesson.getStartTime().isAfter(startTime) && lesson.getEndTime().isBefore(endTime)));
    }

    public boolean validateTeacherBetweenSchedules(Long teacher, LocalDateTime startTime, LocalDateTime endTime) {
        logger.info("Trying to validate teachers");
        var teacherSchedules = lessonRepository.findByTeacher_IdAndStartTimeGreaterThan(teacher, startTime.minusYears(10));
        if (teacherSchedules.stream().anyMatch(lesson -> (lesson.getStartTime().equals(startTime)))) {
            return false;
        } else if (teacherSchedules.stream().anyMatch(lesson -> (lesson.getEndTime().equals(endTime)))) {
            return false;
        } else if (teacherSchedules.stream().anyMatch(lesson -> (lesson.getStartTime().isAfter(startTime) && lesson.getEndTime().isBefore(endTime)))) {
            return false;
        }
        return true;
    }


    public boolean validateRoomBetweenSchedules(Long roomId, LocalDateTime startTime, LocalDateTime endTime) {

        logger.info("Trying to validate rooms");


        var roomSchedules = lessonRepository.findByRoom_IdAndStartTimeGreaterThan(roomId, startTime.minusYears(10));
        if (roomSchedules.stream().anyMatch(lesson -> (lesson.getStartTime().equals(startTime)))) {
            return false;
        } else if (roomSchedules.stream().anyMatch(lesson -> (lesson.getEndTime().equals(endTime)))) {
            return false;
        } else if (roomSchedules.stream().anyMatch(lesson -> lesson.getStartTime().isAfter(startTime) && lesson.getEndTime().isBefore(endTime))) {
            return false;
        }


        return true;
    }

    public boolean lessonDateValidation(Long scheduleId, LocalDateTime startTime) {
        var existingSchedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new SchedulerValidationException("Schedule does not exist", "id", "Schedule not found", scheduleId.toString()));

        var startDate = existingSchedule.getStartingDate().minusDays(1l);

        if (startDate.equals(startTime) || (startDate.isBefore(startTime.toLocalDate()))) {
            return true;
        }
        return false;
    }

    public Schedule allTeachersAreSet(Long scheduleId) {

        var existingSchedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new SchedulerValidationException("Schedule does not exist", "id", "Schedule not found", scheduleId.toString()));

        // finding all teachers that are set for subjects
        var scheduleSubjectsTeachers = existingSchedule.getSubjects()
                .stream()
                .map(subject -> subject.getTeacher())
                .collect(Collectors.toList());

        // finding all rooms that are set for subjects
        var scheduleSubjectsRooms = existingSchedule.getSubjects()
                .stream()
                .map(subject -> subject.getRoom())
                .collect(Collectors.toList());
//        System.out.println(Arrays.toString(scheduleSubjectsRooms.toArray()) + "uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu" + scheduleSubjectsTeachers +
//                scheduleSubjectsTeachers.size() + " " + existingSchedule.getSubjects().size() + " " + scheduleSubjectsRooms.size() + " " + existingSchedule.getSubjects().size());

        //checking if any was not set
        if (scheduleSubjectsTeachers.contains(null) || scheduleSubjectsRooms.contains(null)) {
            existingSchedule.setStatus(1);
        } else {
            existingSchedule.setStatus(0);
        }
        scheduleRepository.save(existingSchedule);

        return existingSchedule;
    }

    public Integer teacherLessonsPerDay(Long teacherId, LocalDateTime day, Integer lessonHours) {
        var teacherSetLessons = lessonRepository.findByTeacher_IdAndStartTime(teacherId, day);
        Integer lessonAmount = teacherSetLessons.stream()
                .map(lesson -> lesson.getLessonHours())
                .reduce(0, (integer, integer2) -> integer + integer2) + lessonHours;
        logger.info("counting lessons per day:");
        logger.info(lessonAmount.toString());
        return lessonAmount;

    }

//    public boolean teacherWorkingHoursValidation (Long teacherId, Double workHoursPerWeek ){
//        var existingTeacher = teacherRepository.findById(teacherId)
//                .orElseThrow(() -> new SchedulerValidationException("Teacher does not exist", "id", "Teacher not found", teacherId.toString()));
//
//        if (existingTeacher.getWorkHoursPerWeek()<= )
//    }


    @Transactional
    public List<ScheduleEntityDto> getSchedulesByNameContaining(String nameText, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return scheduleRepository.findByNameContainingIgnoreCaseOrderByModifiedDateDesc(nameText, pageable)
                .stream()
                .map(ScheduleMapper::toScheduleEntityDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ScheduleEntityDto> getSchedulesByStartingDate(String startingDate, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);

        //Provided String must be in a YYYY-MM-DD format. Subtracting one in order for the searched day itself to appear in results
        var starting = LocalDate.parse(startingDate).minusDays(1);

        return scheduleRepository.findAllByStartingDateAfterOrderByModifiedDateDesc(starting, pageable)
                .stream()
                .map(ScheduleMapper::toScheduleEntityDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ScheduleEntityDto> getSchedulesByPlannedTill(String plannedTill, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);

        //Provided String must be in a YYYY-MM-DD format. Adding one more in order for the searched day itself to appear in results
        var planned = LocalDate.parse(plannedTill).plusDays(1);

        return scheduleRepository.findByPlannedTillDateBeforeOrderByModifiedDateDesc(planned, pageable)
                .stream()
                .map(ScheduleMapper::toScheduleEntityDto)
                .collect(Collectors.toList());
    }


    public List<ScheduleEntityDto> getAllPagedSchedules(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);

        return scheduleRepository.findAll(pageable)
                .stream()
                .map(ScheduleMapper::toScheduleEntityDto)
                .collect(Collectors.toList());
    }

    public Optional<Schedule> getById(Long id) {
        return scheduleRepository.findById(id);
    }

    public Schedule create(Schedule schedule, Long groupId, LocalDate startingDate) {

        //Getting group, from it - shift and program
        var createdGroup = groupsRepository.findById(groupId)
                .orElseThrow(() -> new SchedulerValidationException("Group doesn't exist", "id", "Group doesn't exist", groupId.toString()));
        var createdProgram = createdGroup.getProgram().getId();
        //Getting subjects in the program and adding them to the lesson Set
        List<ProgramSubjectHours> subjectHoursList = programSubjectHoursRepository.findAllByProgramId(createdProgram);

        Set<Lesson> lessonList = subjectHoursList
                .stream()
                .map(LessonMapper::toLessonFromSubject)
                .collect(Collectors.toSet());

        lessonList.stream()
                .forEach(lesson -> lesson.setSubjectTeachers(teacherRepository.findAllByTeacherSubjects_Id(lesson.getSubject().getId())));


        //Creating the Schedule
        schedule.setName(createdGroup.getName() + " " + createdGroup.getShift().getName() + " " + createdGroup.getSchoolYear().toString());
        schedule.setStartingDate(startingDate);
        schedule.setGroup(createdGroup);
        schedule.setGroupIdValue(createdGroup.getId());
        schedule.setShift(createdGroup.getShift());
        schedule.setShiftName(createdGroup.getShift().getName());
        schedule.setSubjects(lessonList);
        schedule.setStatus(1);

        return scheduleRepository.save(schedule);
    }

    public Schedule setTeacherAndRoomInASchedule(Long id, Long lessonId, Long teacherId, Long roomId) {

        //finding the schedule in repository
        var existingSchedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new SchedulerValidationException("Schedule does not exist", "id", "Schedule not found", id.toString()));

        if (teacherId != null) {
            //finding the teacher
            var existingTeacher = teacherRepository.findById(teacherId)
                    .orElseThrow(() -> new SchedulerValidationException("Teacher does not exist", "id", "Teacher not found", teacherId.toString()));
            //setting the teacher
            existingSchedule.getSubjects()
                    .stream()
                    .filter(lesson -> lesson.getId().equals(lessonId))
                    .forEach(lesson -> lesson.setTeacher(existingTeacher));


            if (roomId != null) {
                //finding the room
                var existingRoom = roomRepository.findById(roomId)
                        .orElseThrow(() -> new SchedulerValidationException("Room does not exist", "id", "Room not found", roomId.toString()));
                //setting the room
                existingSchedule.getSubjects()
                        .stream()
                        .filter(lesson -> lesson.getId().equals(lessonId))
                        .forEach(lesson -> lesson.setRoom(existingRoom));

                //replacing the subjectList
                existingSchedule.setSubjects(existingSchedule.getSubjects());
                //save to repository
            }
        }
        //save to repository
        return scheduleRepository.save(existingSchedule);
    }

    public Schedule setTeacherAndRoomInALesson(Long id, Long lessonId, Long teacherId, Long roomId) {
        //finding the schedule in repository
        var existingSchedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new SchedulerValidationException("Schedule does not exist", "id", "Schedule not found", id.toString()));


        //finding the lesson start and end times
        var startTime = existingSchedule.getLessons()
                .stream()
                .filter(lesson -> lesson.getId().equals(lessonId))
                .findFirst()
                .get()
                .getStartTime();
        var endTime = existingSchedule.getLessons()
                .stream()
                .filter(lesson -> lesson.getId().equals(lessonId))
                .findFirst()
                .get()
                .getEndTime();

        if (teacherId != null) {

            //finding the teacher
            var existingTeacher = teacherRepository.findById(teacherId)
                    .orElseThrow(() -> new SchedulerValidationException("Teacher does not exist", "id", "Teacher not found", teacherId.toString()));
            //validation
            if (validateTeacherBetweenSchedules(existingTeacher.getId(), startTime, endTime)) {
                existingSchedule.getLessons()
                        .stream()
                        .filter(lesson -> lesson.getId().equals(lessonId))
                        .forEach(lesson -> lesson.setStatus(1));
                existingSchedule.setStatus(1);
            }

            //setting the teacher
            existingSchedule.getLessons()
                    .stream()
                    .filter(lesson -> lesson.getId().equals(lessonId))
                    .forEach(lesson -> lesson.setTeacher(existingTeacher));

            existingSchedule.setStatus(0);
        } else {
            existingSchedule.setStatus(1);
        }

        if (roomId != null) {
            //finding the room
            var existingRoom = roomRepository.findById(roomId)
                    .orElseThrow(() -> new SchedulerValidationException("Room does not exist", "id", "Room not found", roomId.toString()));

            if (validateRoomBetweenSchedules(existingRoom.getId(), startTime, endTime)) {
                existingSchedule.getLessons()
                        .stream()
                        .filter(lesson -> lesson.getId().equals(lessonId))
                        .forEach(lesson -> lesson.setStatus(1));
                existingSchedule.setStatus(1);
            }

            //setting the room
            existingSchedule.getLessons()
                    .stream()
                    .filter(lesson -> lesson.getId().equals(lessonId))
                    .forEach(lesson -> lesson.setRoom(existingRoom));
            existingSchedule.setStatus(0);
        } else {
            existingSchedule.setStatus(2);
        }


        //save to repository

        return scheduleRepository.save(existingSchedule);
    }


    public Schedule ScheduleLesson(Long scheduleId, Long subjectId, LocalDateTime startTime, LocalDateTime endTime) {
        //finding the schedule in repository
        var existingSchedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new SchedulerValidationException("Schedule does not exist", "id", "Schedule not found", scheduleId.toString()));

        /**
         *     Validations before scheduling a lesson:
         * 1 - Is there enough remaining lesson hours left in subject
         * 2 - Is end time not earlier than start time
         * 3 - If lesson is not already planned in this schedule, in this time period
         * 4 - If lesson is not earlier than schedule start
         */


        if (existingSchedule.getSubjects()
                .stream()
                .filter(lesson -> lesson.getId().equals(subjectId))
                .noneMatch(lesson -> (lesson.getLessonHours() - (endTime.getHour() - startTime.getHour())) < 0)) {

            if (endTime.isAfter(startTime)) {

                if (lessonIsNotPlanned(scheduleId, startTime, endTime)) {

                    if (lessonDateValidation(scheduleId, startTime)) {

                        //finding and getting the subject to schedule, then turning it to a new lesson object
                        Lesson createdLesson = existingSchedule.getSubjects()
                                .stream()
                                .filter(lesson -> lesson.getId().equals(subjectId))
                                .map(LessonMapper::toIndividualLesson)
                                .findAny()
                                .orElseThrow(() -> new SchedulerValidationException("Schedule does not exist", "id", "Schedule not found", scheduleId.toString()));

                        //setting the time in the schedule
                        createdLesson.setStartTime(startTime);
                        createdLesson.setEndTime(endTime);

                        //setting lesson duration
                        createdLesson.setLessonHours(endTime.getHour() - startTime.getHour());

                        //subtracting the subjectHours from subjectTotalHours & setting teacher working hours
                        existingSchedule.getSubjects()
                                .stream()
                                .filter(lesson -> lesson.getId()
                                        .equals(subjectId))
                                .forEach(
                                        lesson -> {
//                                            if (teacherLessonsPerDay(lesson.getTeacher().getId(), startTime, createdLesson.getLessonHours()) <= 12) {

                                            if (lesson.getLessonHours() - createdLesson.getLessonHours() <= 0) {
                                                lesson.setLessonHours(0);
                                                logger.info("lessonHours" + lesson.getLessonHours().toString());
                                            } else {
                                                lesson.setLessonHours(lesson.getLessonHours() - createdLesson.getLessonHours());
                                            }
//                                            lesson.getTeacher().setWorkHoursPerWeek(lesson.getTeacher().getWorkHoursPerWeek() + createdLesson.getLessonHours());
//                                            } else {
//                                                throw new SchedulerValidationException("Too many lessons taken", "lesson", "Lesson amount", scheduleId.toString());
//                                            }
                                        });

                        existingSchedule.scheduleLesson(createdLesson);
                        logger.info("saved");
                        logger.info("planned till:");
                        // finding and setting last day of planned schedule (last planned lesson)
                        var last = existingSchedule.getLessons()
                                .stream()
                                .sorted(Comparator.comparing(Lesson::getEndTime))
                                .collect(Collectors.toList())
                                .get(existingSchedule.getLessons().size() - 1).getEndTime();

                        existingSchedule.setPlannedTillDate(last.toLocalDate());

                        //validating if the teacher already teaches during the timeframe in another lesson. If so, setting the status to warning
                        if (createdLesson.getTeacher() != null) {
                            logger.info("step1");
                            if (!validateTeacherBetweenSchedules(createdLesson.getTeacher().getId(), startTime, endTime)) {
                                logger.info("Setting lesson status to critical. Reason: teacher works at the same time in another lesson");
                                createdLesson.setStatus(1);
                                createdLesson.setStatusMessage("Mokytojas jau užimtas tuo pačiu laiku. Pamoka: " + createdLesson.getSubject().getName().toString() + ", Laikas: " + (createdLesson.getStartTime().getMonthValue() + 1) + "mėn.  " + createdLesson.getStartTime().getDayOfMonth() + "d. , nuo " + createdLesson.getStartTime().getHour() + " pamokos.");
                                existingSchedule.setStatus(1);
                            }
                        }

                        //validating if the classroom is already in use in another Schedule lesson. If so, setting the status to warning
                        if (createdLesson.getRoom() != null) {
                            if (!validateRoomBetweenSchedules(createdLesson.getRoom().getId(), startTime, endTime)) {
                                logger.info("Setting lesson status to critical. Reason: class is occupied at the same time in another lesson");
                                createdLesson.setStatus(1);
                                createdLesson.setStatusMessage("Klasė jau užimta tuo pačiu laiku. Klasė: " + createdLesson.getRoom().getName().toString() + ", Laikas: " + (createdLesson.getStartTime().getMonthValue() + 1) + "mėn.  " + createdLesson.getStartTime().getDayOfMonth() + "d. , nuo " + createdLesson.getStartTime().getHour() + " pamokos.");
                                existingSchedule.setStatus(1);

                            }
                        }

                        if (createdLesson.getTeacher() != null && createdLesson.getRoom() != null) {
                            if (!validateTeacherBetweenSchedules(createdLesson.getTeacher().getId(), startTime, endTime) && !validateRoomBetweenSchedules(createdLesson.getRoom().getId(), startTime, endTime)) {
                                logger.info("Setting lesson status to critical. Reason: teacher works at the same time in another lesson");
                                createdLesson.setStatus(1);
                                createdLesson.setStatusMessage("Mokytojas ir klasė jau užimti tuo pačiu laiku. Pamoka: " + createdLesson.getSubject().getName().toString() + ", Laikas: " + (createdLesson.getStartTime().getMonthValue() + 1) + "mėn.  " + createdLesson.getStartTime().getDayOfMonth() + "d. , nuo " + createdLesson.getStartTime().getHour() + " pamokos.");
                                existingSchedule.setStatus(1);
                            }
                        }
                        createdLesson.setStatus(0);
                        lessonRepository.save(createdLesson);

                        return scheduleRepository.save(existingSchedule);
                    } else {
                        throw new SchedulerValidationException("Planuojama pamoka yra anksčiau nei tvarkaraščio pradžia. Pradžia:" + existingSchedule.getStartingDate(), "time", "Invalid time", scheduleId.toString());
                    }
                } else {
                    throw new SchedulerValidationException("Pasirinktu metu jau yra suplanuotos kitos pamokos", "time", "Invalid time", scheduleId.toString());
                }
            } else {
                throw new SchedulerValidationException("Pamokos pabaigos laiką pasirinkote ankstesnį, nei pamokos pradžia.", "time", "Invalid time", scheduleId.toString());
            }

        } else {
            throw new SchedulerValidationException("Pasirinkote daugiau pamokų, nei liko / suplanuotos visos pamokos.", "Subject", "Subject planned", subjectId.toString());
        }
    }


    public Set<Lesson> getAllLessonsByScheduleId(Long scheduleId) {
        var existingSchedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new SchedulerValidationException("Schedule does not exist", "id", "Schedule not found", scheduleId.toString()));
        Set<Lesson> lessons = new HashSet<>();
        lessons.addAll(existingSchedule.getLessons());
        return lessons;
    }

    public Set<Lesson> getAllSubjectsByScheduleId(Long scheduleId) {
        var existingSchedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new SchedulerValidationException("Schedule does not exist", "id", "Schedule not found", scheduleId.toString()));
        Set<Lesson> subjects = new HashSet<>();
        subjects.addAll(existingSchedule.getSubjects());
        return subjects;


    }

    public Schedule setLessonOnline(Long scheduleId, Long lessonId) {
        //finding the schedule in repository
        var existingSchedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new SchedulerValidationException("Schedule does not exist", "id", "Schedule not found", scheduleId.toString()));

        var existingLessons = existingSchedule.getLessons();

        existingLessons.stream()
                .filter(lesson -> lesson.getId().equals(lessonId))
                .forEach(lesson -> lesson.setOnline(true));

        existingSchedule.setLessons(existingLessons);

        return scheduleRepository.save(existingSchedule);
    }

    public Lesson getSingleLesson(Long scheduleId, Long lessonId) {
        //finding the schedule in repository
        var existingSchedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new SchedulerValidationException("Schedule does not exist", "id", "Schedule not found", scheduleId.toString()));
        //finding and getting the lesson
        var createdLesson = existingSchedule.getLessons()
                .stream()
                .filter(lesson -> lesson.getId().equals(lessonId))
                .findAny().get();

        return createdLesson;
    }

    public boolean deleteById(Long id) {
        //finding the schedule in repository
        var existingSchedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new SchedulerValidationException("Schedule does not exist", "id", "Schedule not found", id.toString()));
        Set<Lesson> newList = new HashSet<>();
        existingSchedule.setLessons(newList);
        existingSchedule.setSubjects(newList);
        scheduleRepository.save(existingSchedule);
        try {
            scheduleRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException exception) {
            return false;
        }
    }

    @Transactional
    public boolean removeLesson(Long scheduleId, Long lessonId) {
        try {

            //finding the schedule in repository
            var existingSchedule = scheduleRepository.findById(scheduleId)
                    .orElseThrow(() -> new SchedulerValidationException("Schedule does not exist", "id", "Schedule not found", scheduleId.toString()));

            //finding the lesson
            var existingLesson = lessonRepository.findById(lessonId)
                    .orElseThrow(() -> new SchedulerValidationException("Lesson does not exist", "id", "Lesson not found", lessonId.toString()));


            //adding the deleted lesson hours to subject hours
            existingSchedule.getSubjects()
                    .stream()
                    .filter(lesson -> lesson.getSubject().getId().equals(existingLesson.getSubject().getId()))
                    .forEach(
                            lesson -> lesson.setLessonHours(lesson.getLessonHours() + existingLesson.getLessonHours()));

            scheduleRepository.save(existingSchedule);

            //deleting a lesson from schedule
            scheduleLessonsRepository.deleteByScheduleAndLesson(scheduleRepository.findById(scheduleId).get(), lessonRepository.findById(lessonId).get());

            //delete a lesson
            lessonRepository.deleteById(lessonId);

            return true;
        } catch (EmptyResultDataAccessException exception) {
            return false;
        }
    }

    public List<ScheduleTestDto> getAllSchedules() {
        return scheduleRepository.findAll().stream()
                .map(ScheduleMapper::toScheduleTestDto).collect(Collectors.toList());
    }


}
