package lt.techin.AlpineOctopusScheduler.api;

import lt.techin.AlpineOctopusScheduler.dao.RoomRepository;
import lt.techin.AlpineOctopusScheduler.model.Room;
import lt.techin.AlpineOctopusScheduler.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RoomController {
    private RoomService roomService;
    @Autowired
    private RoomRepository roomRepository;

    @GetMapping("/rooms")
    List<Room> getAllRooms(){
        return roomService.getAllRooms();
    }
    @GetMapping("/rooms/{name}")
    public Room getRoom(@PathVariable String name){
        return roomService.getRoom(name);
    }
    @PostMapping("/rooms")
    public Room addNewRoom (@RequestBody Room newRoom){
        return roomService.addRoom(newRoom);
    }
    @PutMapping("/rooms/{name}")
    public Room updateRoom (@RequestBody Room newRoom, @PathVariable String name){
        return roomService.updateRoom(name, newRoom);
    }
    @DeleteMapping("/rooms/{name}")
    public String deleteRoom(@PathVariable String name) {
        if(roomService.deleteRoom(name) == true){
            return "Room successfully deleted";
        } else {
            return "Room not found";
        }
    }

}
