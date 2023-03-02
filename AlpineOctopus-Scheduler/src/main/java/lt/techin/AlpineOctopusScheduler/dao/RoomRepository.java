package lt.techin.AlpineOctopusScheduler.dao;

import lt.techin.AlpineOctopusScheduler.model.Room;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByNameContainingIgnoreCase(String nameText, Pageable pageable);

    List<Room> findByBuildingContainingIgnoreCase(String buildingText, Pageable pageable);

    List<Room> findAllByDeletedAndNameContainingIgnoreCaseOrderByModifiedDateDesc(Boolean deleted, String buildingText);

    List<Room> findAllByDeletedAndNameContainingIgnoreCaseOrderByModifiedDateDesc(Boolean deleted, String nameText, Pageable pageable);

    List<Room> findByDeletedAndBuildingContainingIgnoreCaseOrderByModifiedDateDesc(Boolean deleted, String buildingText);

    List<Room> findByDeletedAndBuildingContainingIgnoreCaseOrderByModifiedDateDesc(Boolean deleted, String buildingText, Pageable pageable);

    List<Room> findAllByDeletedOrderByModifiedDateDesc(Boolean deleted, Pageable pageable);

    List<Room> findAllByDeletedOrderByModifiedDateDesc(Boolean deleted);


}