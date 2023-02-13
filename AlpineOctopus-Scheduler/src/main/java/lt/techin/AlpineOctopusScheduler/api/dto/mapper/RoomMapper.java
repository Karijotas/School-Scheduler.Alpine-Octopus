package lt.techin.AlpineOctopusScheduler.api.dto.mapper;

import lt.techin.AlpineOctopusScheduler.api.dto.RoomDto;
import lt.techin.AlpineOctopusScheduler.api.dto.RoomEntityDto;
import lt.techin.AlpineOctopusScheduler.model.Room;

public class RoomMapper {
    public static RoomDto toRoomDto(Room room) {
        var roomDto = new RoomDto();

        roomDto.setName(room.getName());
        roomDto.setBuilding(room.getBuilding());
        roomDto.setDescription(room.getDescription());

        return roomDto;
    }

    public static Room toRoom(RoomDto roomDto) {
        var room = new Room();

        room.setName(roomDto.getName());
        room.setBuilding(roomDto.getBuilding());
        room.setDescription(roomDto.getDescription());

        return room;
    }


    public static RoomEntityDto toRoomEntityDto(Room room) {
        var roomDto = new RoomEntityDto();

        roomDto.setId(room.getId());
        roomDto.setName(room.getName());
        roomDto.setBuilding(room.getBuilding());
        roomDto.setDescription(room.getDescription());

        return roomDto;
    }

    public static Room toRoom(RoomEntityDto roomEntityDto) {
        var room = new Room();

        room.setId(roomEntityDto.getId());
        room.setName(roomEntityDto.getName());
        room.setBuilding(roomEntityDto.getBuilding());
        room.setDescription(roomEntityDto.getDescription());

        return room;
    }
}
