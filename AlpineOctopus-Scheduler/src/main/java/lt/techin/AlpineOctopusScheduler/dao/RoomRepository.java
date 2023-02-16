package lt.techin.AlpineOctopusScheduler.dao;

import lt.techin.AlpineOctopusScheduler.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room,Long> {

    List<Room> findByNameContainingIgnoreCase(String nameText, Pageable pageable);

}
