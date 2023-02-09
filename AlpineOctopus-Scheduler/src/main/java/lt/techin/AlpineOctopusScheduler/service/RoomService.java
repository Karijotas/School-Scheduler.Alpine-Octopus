package lt.techin.AlpineOctopusScheduler.service;

import lt.techin.AlpineOctopusScheduler.dao.RoomRepository;
import lt.techin.AlpineOctopusScheduler.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;



import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    private RoomRepository roomRepository;
    @Autowired
    public RoomService(RoomRepository roomRepository){
        this.roomRepository = roomRepository;
    }


    public List<Room> getAllRooms(){
        return roomRepository.findAll();
    }
    public Room getRoom(String name){
        return roomRepository.findById(name)
                .orElseThrow(()-> new RuntimeException());

    }
    public Room addRoom(Room room){
        return roomRepository.save(room);
    }

    public Room updateRoom(String name,Room newRoom){
        return roomRepository.findById(name)
                .map(room ->{
                    room.setClassName(newRoom.getClassName());
                    room.setBuilding(newRoom.getBuilding());
                    room.setDescription(newRoom.getDescription());
                    return roomRepository.save(room);
                }).orElseThrow(()-> new RuntimeException("room does not exist"));
    }
    public Boolean deleteRoom(String name){
        try{
            roomRepository.deleteById(name);
            return true;
        } catch (EmptyResultDataAccessException e){
            return false;
        }

    }

}
