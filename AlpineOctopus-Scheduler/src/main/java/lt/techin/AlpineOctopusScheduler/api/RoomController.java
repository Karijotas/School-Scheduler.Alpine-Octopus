package lt.techin.AlpineOctopusScheduler.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lt.techin.AlpineOctopusScheduler.api.dto.RoomDto;
import lt.techin.AlpineOctopusScheduler.api.dto.RoomEntityDto;
import lt.techin.AlpineOctopusScheduler.api.dto.TeacherDto;
import lt.techin.AlpineOctopusScheduler.api.dto.mapper.RoomMapper;
import lt.techin.AlpineOctopusScheduler.model.Room;
import lt.techin.AlpineOctopusScheduler.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static lt.techin.AlpineOctopusScheduler.api.dto.mapper.RoomMapper.toRoom;
import static lt.techin.AlpineOctopusScheduler.api.dto.mapper.RoomMapper.toRoomDto;
import static lt.techin.AlpineOctopusScheduler.api.dto.mapper.TeacherMapper.toTeacherDto;
import static org.springframework.http.ResponseEntity.ok;

@Controller
@RequestMapping("/api/v1/rooms")
@Validated
public class RoomController {

    private final Logger logger = LoggerFactory.getLogger(RoomController.class);
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }


    @GetMapping
    @ResponseBody
    public List<RoomEntityDto> getAllRooms() {
        return roomService.getAll().stream()
                .map(RoomMapper::toRoomEntityDto)
                .collect(toList());
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Room> getRoom(@PathVariable Long id) {
        var roomOptional = roomService.getById(id);

        var responseEntity = roomOptional
                .map(room -> ok(room))
                .orElseGet(() -> ResponseEntity.notFound().build());

        return responseEntity;
    }

    @PostMapping
    public ResponseEntity<RoomDto> createRoom(@Valid @RequestBody RoomDto roomDto) {
        var createdRoom = roomService.create(toRoom(roomDto));
        return ok(toRoomDto(createdRoom));
    }
    @PutMapping("/{id}")
    public ResponseEntity<RoomDto> updateRoom(@PathVariable Long id, @RequestBody RoomDto roomDto) {
        var updatedRoom = roomService.update(id, toRoom(roomDto));

        return ok(toRoomDto(updatedRoom));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        var roomDeleted = roomService.deleteById(id);

        if (roomDeleted) {
            return ResponseEntity.noContent().build();
//            new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/subjects/{moduleId}")
    public ResponseEntity<RoomDto> addSubjectToRoom(@PathVariable Long roomId, @RequestBody Long subjectId) {
        var updatedRoom = roomService.addSubjectToRoom(roomId, subjectId);

        return ok(toRoomDto(updatedRoom));
    }

}
