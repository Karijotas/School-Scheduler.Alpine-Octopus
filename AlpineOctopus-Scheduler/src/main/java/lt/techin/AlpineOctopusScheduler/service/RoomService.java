package lt.techin.AlpineOctopusScheduler.service;

import lt.techin.AlpineOctopusScheduler.api.dto.RoomEntityDto;
import lt.techin.AlpineOctopusScheduler.api.dto.mapper.RoomMapper;
import lt.techin.AlpineOctopusScheduler.dao.RoomRepository;
import lt.techin.AlpineOctopusScheduler.exception.SchedulerValidationException;
import lt.techin.AlpineOctopusScheduler.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    private final Validator validator;

    @Autowired
    public RoomService(RoomRepository roomRepository, Validator validator) {
        this.roomRepository = roomRepository;
        this.validator = validator;
    }

    public List<RoomEntityDto> getPagedAllRooms(int page, int pageSize) {

        Pageable pageable = PageRequest.of(page, pageSize);

        return roomRepository.findAll(pageable).stream().map(RoomMapper::toRoomEntityDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<RoomEntityDto> getPagedRoomsByNameContaining(String nameText, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return roomRepository.findByNameContainingIgnoreCase(nameText, pageable).stream()
                .map(RoomMapper::toRoomEntityDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<RoomEntityDto> getPagedBuildingsByNameContaining(String buildingText, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return roomRepository.findByBuildingContainingIgnoreCase(buildingText, pageable).stream()
                .map(RoomMapper::toRoomEntityDto).collect(Collectors.toList());
    }

    void validateInputWithInjectedValidator(Room room) {
        Set<ConstraintViolation<Room>> violations = validator.validate(room);
        if (!violations.isEmpty()) {
            throw new SchedulerValidationException(violations.toString(), "Room", "Error in room entity", room.toString());
        }
    }

    public List<Room> getAll() {
        return roomRepository.findAllByOrderByDeletedAscIdAsc();
    }

    public Optional<Room> getById(Long id) {
        return roomRepository.findById(id);
    }

    public Room create(Room room) {
        validateInputWithInjectedValidator(room);
        return roomRepository.save(room);
    }

    public Room update(Long id, Room room) {
        validateInputWithInjectedValidator(room);
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
}