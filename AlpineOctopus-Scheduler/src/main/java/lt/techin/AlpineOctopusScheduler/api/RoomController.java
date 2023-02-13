package lt.techin.AlpineOctopusScheduler.api;

import lt.techin.AlpineOctopusScheduler.api.dto.RoomDto;
import lt.techin.AlpineOctopusScheduler.model.Room;
import lt.techin.AlpineOctopusScheduler.service.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static lt.techin.AlpineOctopusScheduler.api.dto.mapper.RoomMapper.toRoom;
import static lt.techin.AlpineOctopusScheduler.api.dto.mapper.RoomMapper.toRoomDto;
import static org.springframework.http.ResponseEntity.ok;

@RestController
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }


    @GetMapping("/rooms")
    List<Room> getAllRooms(){
        return roomService.getAll();
    }
    @GetMapping("/rooms/{id}")
    public Optional<Room> getRoom(@PathVariable Long id){
        return roomService.getById(id);
    }
    @PostMapping
    public ResponseEntity<RoomDto> createRoom(@Valid @RequestBody RoomDto roomDto) {
        var createdRoom = roomService.create(toRoom(roomDto));
        return ok(toRoomDto(createdRoom));
    }
    @PutMapping("/rooms/{id}")
    public Room updateRoom (@RequestBody Room newRoom, @PathVariable Long id){
        return roomService.update(id, newRoom);
    }
    @DeleteMapping("/rooms/{id}")
    public String deleteRoom(@PathVariable Long id) {
        if(roomService.deleteById(id) == true){
            return "Room successfully deleted";
        } else {
            return "Room not found";
        }
    }

}
