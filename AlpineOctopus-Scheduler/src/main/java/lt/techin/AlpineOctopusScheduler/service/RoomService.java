package lt.techin.AlpineOctopusScheduler.service;

import lt.techin.AlpineOctopusScheduler.api.dto.RoomEntityDto;
import lt.techin.AlpineOctopusScheduler.api.dto.mapper.RoomMapper;
import lt.techin.AlpineOctopusScheduler.dao.RoomRepository;
import lt.techin.AlpineOctopusScheduler.dao.SubjectRepository;
import lt.techin.AlpineOctopusScheduler.exception.SchedulerValidationException;
import lt.techin.AlpineOctopusScheduler.model.Room;
import lt.techin.AlpineOctopusScheduler.model.Subject;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class RoomService {
    private final RoomRepository roomRepository;

    private final SubjectRepository subjectRepository;
    @Autowired
    public RoomService(RoomRepository roomRepository, SubjectRepository subjectRepository) {
        this.roomRepository = roomRepository;
        this.subjectRepository = subjectRepository;
    }
    public List<RoomEntityDto> getPagedAllPrograms(int page, int pageSize) {

        Pageable pageable = PageRequest.of(page, pageSize);

        return roomRepository.findAll(pageable).stream().map(RoomMapper::toRoomEntityDto).collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public List<RoomEntityDto> getPagedRoomsByNameContaining(String nameText, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return roomRepository.findByNameContainingIgnoreCase(nameText, pageable).stream()
                .map(RoomMapper::toRoomEntityDto).collect(Collectors.toList());
    }

    public List<Room> getAll() {
        return roomRepository.findAll();
    }

    public Optional<Room> getById(Long id) {
        return roomRepository.findById(id);
    }

    public Room create(Room room) {
        return roomRepository.save(room);
    }

    public Room update(Long id, Room room) {
        var existingRoom = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room doesn't exist"));

        existingRoom.setName(room.getName());
        existingRoom.setBuilding(room.getBuilding());
        existingRoom.setDescription(room.getDescription());
        existingRoom.setModifiedDate(room.getModifiedDate());

        return roomRepository.save(existingRoom);
    }

    public boolean deleteById(Long id) {
        if (roomRepository.existsById(id)) {
            roomRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Room addSubjectToRoom(Long roomId, Long subjectId) {
        var existingRoom = roomRepository.findById(roomId)
                .orElseThrow(() -> new SchedulerValidationException("Room does not exist",
                        "id", "Room not found", roomId.toString()));

        var existingSubject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new SchedulerValidationException("Subject does not exist",
                        "id", "Subject not found", subjectId.toString()));

        Set<Subject> existingSubjectList = existingRoom.getRoomSubjects();
        existingSubjectList.add(existingSubject);
        existingRoom.setRoomSubjects(existingSubjectList);

        return roomRepository.save(existingRoom);
    }
}
