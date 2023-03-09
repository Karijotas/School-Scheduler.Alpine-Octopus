package lt.techin.AlpineOctopusScheduler.stubs;

import lt.techin.AlpineOctopusScheduler.model.Room;

public class RoomCreator {
    public static Room createRoom(Long id){
    	
        var room = new Room();

        room.setId(id);
        room.setName("jp-01");
        room.setBuilding("lakunu g.");
        room.setDescription("java programavimas");

        return room;
    }
}
