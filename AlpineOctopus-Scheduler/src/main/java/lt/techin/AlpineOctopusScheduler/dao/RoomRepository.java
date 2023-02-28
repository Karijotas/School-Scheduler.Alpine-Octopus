package lt.techin.AlpineOctopusScheduler.dao;

import lt.techin.AlpineOctopusScheduler.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByNameContainingIgnoreCase(String nameText);

    List<Room> findByBuildingContainingIgnoreCase(String buildingText);

    List<Room> findAllByOrderByDeletedAscIdAsc();

}