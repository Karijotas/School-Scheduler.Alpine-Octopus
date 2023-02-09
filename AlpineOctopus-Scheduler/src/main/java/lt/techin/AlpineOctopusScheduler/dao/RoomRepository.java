package lt.techin.AlpineOctopusScheduler.dao;

import lt.techin.AlpineOctopusScheduler.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room,String> {

}
