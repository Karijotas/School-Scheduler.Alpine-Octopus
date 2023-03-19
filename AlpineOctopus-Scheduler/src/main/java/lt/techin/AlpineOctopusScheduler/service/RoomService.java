package lt.techin.AlpineOctopusScheduler.service;

import lt.techin.AlpineOctopusScheduler.api.dto.RoomEntityDto;
import lt.techin.AlpineOctopusScheduler.api.dto.RoomTestDto;
import lt.techin.AlpineOctopusScheduler.api.dto.mapper.RoomMapper;
import lt.techin.AlpineOctopusScheduler.dao.RoomRepository;
import lt.techin.AlpineOctopusScheduler.exception.SchedulerValidationException;
import lt.techin.AlpineOctopusScheduler.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Validator;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static lt.techin.AlpineOctopusScheduler.api.dto.mapper.RoomMapper.toRoomEntityDto;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    private final Validator validator;

    @Autowired
    public RoomService(RoomRepository roomRepository, Validator validator) {
        this.roomRepository = roomRepository;
        this.validator = validator;
    }

//    public List<RoomEntityDto> getPagedAllRooms(int page, int pageSize) {
//
//        Pageable pageable = PageRequest.of(page, pageSize);
//
//        return roomRepository.findAll(pageable).stream().sorted(Comparator.comparing(Room::getModifiedDate).reversed()).map(RoomMapper::toRoomEntityDto).collect(Collectors.toList());
//    }

    public List<RoomTestDto> getAllRooms() {
        return roomRepository.findAll().stream()
                .map(RoomMapper::toRoomTestDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<RoomEntityDto> getAvailablePagedRoomsByNameContaining(String nameText, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return roomRepository.findAllByDeletedAndNameContainingIgnoreCaseOrderByModifiedDateDesc(Boolean.FALSE, nameText, pageable).stream().sorted(Comparator.comparing(Room::getModifiedDate).reversed())
                .map(RoomMapper::toRoomEntityDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<RoomEntityDto> getAvailablePagedBuildingsByNameContaining(String buildingText, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return roomRepository.findByDeletedAndBuildingContainingIgnoreCaseOrderByModifiedDateDesc(Boolean.FALSE, buildingText, pageable).stream().sorted(Comparator.comparing(Room::getModifiedDate).reversed())
                .map(RoomMapper::toRoomEntityDto).collect(Collectors.toList());
    }

//    @Transactional(readOnly = true)
//    public List<RoomEntityDto> getAvailableRoomsByNameContaining(String nameText) {
//
//        return roomRepository.findAllByDeletedAndNameContainingIgnoreCaseOrderByModifiedDateDesc(Boolean.FALSE, nameText).stream().sorted(Comparator.comparing(Room::getModifiedDate).reversed())
//                .map(RoomMapper::toRoomEntityDto).collect(Collectors.toList());
//    }

//    @Transactional(readOnly = true)
//    public List<RoomEntityDto> getAvailableBuildingsByNameContaining(String buildingText) {
//
//        return roomRepository.findByDeletedAndBuildingContainingIgnoreCaseOrderByModifiedDateDesc(Boolean.FALSE, buildingText).stream().sorted(Comparator.comparing(Room::getModifiedDate).reversed())
//                .map(RoomMapper::toRoomEntityDto).collect(Collectors.toList());
//    }

//    void validateInputWithInjectedValidator(Room room) {
//        Set<ConstraintViolation<Room>> violations = validator.validate(room);
//        if (!violations.isEmpty()) {
//            throw new SchedulerValidationException(violations.toString(), "Room", "Error in room entity", room.toString());
//        }
//    }

    public boolean classIsUnique(Room room) {
        return roomRepository.findAll()
                .stream()
                .noneMatch(room1 -> room1.getName().equals(room.getName()));
    }

    public List<Room> getAll() {

        return roomRepository.findAll();
        // uzkomentavau sita nes tada praeina testai.
        // return roomRepository.findAllByDeletedOrderByModifiedDateDesc(Boolean.FALSE);
    }

    public Optional<Room> getById(Long id) {
        return roomRepository.findById(id);
    }

    public Room create(Room room) {
//        validateInputWithInjectedValidator(room);
        var newRoom = new Room();
        newRoom.setId(room.getId());
        newRoom.setName(room.getName());
        newRoom.setDescription(room.getDescription());
        newRoom.setBuilding(room.getBuilding());
        newRoom.setDeleted(Boolean.FALSE);
        newRoom.setCreatedDate(room.getCreatedDate());
        newRoom.setModifiedDate(room.getModifiedDate());
        return roomRepository.save(newRoom);
    }

    public Room update(Long id, Room room) {
//        validateInputWithInjectedValidator(room);
        var existingRoom = roomRepository.findById(id)
                .orElseThrow(() -> new SchedulerValidationException("Room does not exist",
                        "id", "Room not found", id.toString()));

        existingRoom.setName(room.getName());
        existingRoom.setBuilding(room.getBuilding());
        existingRoom.setDescription(room.getDescription());
        existingRoom.setModifiedDate(room.getModifiedDate());

        return roomRepository.save(existingRoom);
    }
//
//    public boolean deleteById(Long id) {
//        if (roomRepository.existsById(id)) {
//            roomRepository.deleteById(id);
//            return true;
//        }
//        return false;
//    }

    public List<RoomEntityDto> getAllAvailablePagedRooms(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return roomRepository.findAllByDeletedOrderByModifiedDateDesc(Boolean.FALSE, pageable).stream()
                .map(RoomMapper::toRoomEntityDto).collect(Collectors.toList());
    }

    public List<RoomEntityDto> getAllDeletedPagedRooms(int page, int pageSize) {

        Pageable pageable = PageRequest.of(page, pageSize);

        return roomRepository.findAllByDeletedOrderByModifiedDateDesc(Boolean.TRUE, pageable).stream()
                .map(RoomMapper::toRoomEntityDto).collect(Collectors.toList());
    }

    public List<RoomEntityDto> getAllAvailableRooms() {
        return roomRepository.findAllByDeletedOrderByModifiedDateDesc(Boolean.FALSE).stream()
                .map(RoomMapper::toRoomEntityDto).collect(Collectors.toList());
    }

    public List<RoomEntityDto> getAllDeletedRooms() {
        return roomRepository.findAllByDeletedOrderByModifiedDateDesc(Boolean.TRUE).stream()
                .map(RoomMapper::toRoomEntityDto).collect(Collectors.toList());
    }

    public RoomEntityDto restoreRoom(Long roomId) {

        var existingRoom = roomRepository.findById(roomId).orElseThrow(() -> new SchedulerValidationException("Room does not exist",
                "id", "Room not found", roomId.toString()));
        existingRoom.setDeleted(Boolean.FALSE);
        roomRepository.save(existingRoom);
        return toRoomEntityDto(existingRoom);
    }

    public RoomEntityDto deleteRoom(Long roomId) {

        var existingRoom = roomRepository.findById(roomId).orElseThrow(() -> new SchedulerValidationException("Room does not exist",
                "id", "Room not found", roomId.toString()));
        existingRoom.setDeleted(Boolean.TRUE);
        roomRepository.save(existingRoom);
        return toRoomEntityDto(existingRoom);
    }
}