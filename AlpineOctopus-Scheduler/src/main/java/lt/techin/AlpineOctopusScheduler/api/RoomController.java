package lt.techin.AlpineOctopusScheduler.api;

import io.swagger.annotations.ApiOperation;
import lt.techin.AlpineOctopusScheduler.api.dto.RoomDto;
import lt.techin.AlpineOctopusScheduler.api.dto.RoomEntityDto;
import lt.techin.AlpineOctopusScheduler.api.dto.RoomTestDto;
import lt.techin.AlpineOctopusScheduler.exception.SchedulerValidationException;
import lt.techin.AlpineOctopusScheduler.model.Room;
import lt.techin.AlpineOctopusScheduler.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static lt.techin.AlpineOctopusScheduler.api.dto.mapper.RoomMapper.*;
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

//    @GetMapping
//    @ResponseBody
//    public List<RoomEntityDto> getAllRooms() {
//        return roomService.getAll().stream()
//                .map(RoomMapper::toRoomEntityDto)
//                .collect(toList());
//    }

    @GetMapping
    @ResponseBody
    public List<RoomEntityDto> getAvailableRooms() {
        return roomService.getAllAvailableRooms();
    }

    @GetMapping(path = "/archive/", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<RoomEntityDto> getDeletedRooms() {
        return roomService.getAllDeletedRooms();
    }


    @GetMapping(path = "/page", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<RoomEntityDto> getPagedAvailableRooms(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                      @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {

        Pageable pageable = PageRequest.of(page, pageSize);
        return roomService.getAllAvailablePagedRooms(page, pageSize);
    }

    @GetMapping(path = "/archive/page", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<RoomEntityDto> getPagedDeletedRooms(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                    @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {

        return roomService.getAllDeletedPagedRooms(page, pageSize);
    }
//
//    @GetMapping(path = "/page", produces = {MediaType.APPLICATION_JSON_VALUE})
//    @ResponseBody
//
//    public List<RoomEntityDto> getPagedAllPRooms(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
//
//                                                 @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
//        return roomService.getAllAvailableRooms();
//    }

    @GetMapping(path = "/all")
    @ResponseBody
    public List<RoomTestDto> getAllRoomss() {
        return roomService.getAllRooms();
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
    public ResponseEntity<RoomEntityDto> createRoom(@Valid @RequestBody RoomEntityDto roomDto) {
        if (roomService.classIsUnique(toRoom(roomDto))) {
            var createdRoom = roomService.create(toRoom(roomDto));
            return ok(toRoomEntityDto(createdRoom));
        } else {
            throw new SchedulerValidationException("Class already exists", "Class name & building adress", "Already exists", roomDto.getName());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomDto> updateRoom(@PathVariable Long id, @Valid @RequestBody RoomDto roomDto) {
        var updatedRoom = roomService.update(id, toRoom(roomDto));
        return ok(toRoomDto(updatedRoom));
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
//        var roomDeleted = roomService.deleteById(id);
//
//        if (roomDeleted) {
//            return ResponseEntity.noContent().build();
////            new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
//        }
//        return ResponseEntity.notFound().build();
//    }

//    @GetMapping(path = "/page", produces = {MediaType.APPLICATION_JSON_VALUE})
//    @ResponseBody
//    public List<RoomEntityDto> getPagedAllRooms(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
//                                                @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
//
//
//        return roomService.getPagedAllRooms(page, pageSize);
//
//    }

//    @GetMapping(path = "page/name-filter/{nameText}")
//    @ApiOperation(value = "Get Paged Programs starting with", notes = "Returns list of Programs starting with passed String")
//    @ResponseBody
//    public List<RoomEntityDto> getPagedRoomsByNameContaining(@PathVariable String nameText,
//                                                             @RequestParam(value = "page", defaultValue = "0", required = false) int page,
//                                                             @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
//        return roomService.getAvailablePagedRoomsByNameContaining(nameText, page, pageSize);
//    }
//
//    @GetMapping(path = "page/building-filter/{buildingText}")
//    @ApiOperation(value = "Get Paged Programs starting with", notes = "Returns list of Programs starting with passed String")
//    @ResponseBody
//    public List<RoomEntityDto> getPagedBuildingsByNameContaining(@PathVariable String buildingText,
//                                                                 @RequestParam(value = "page", defaultValue = "0", required = false) int page,
//                                                                 @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
//        return roomService.getAvailablePagedBuildingsByNameContaining(buildingText, page, pageSize);
//    }

    @GetMapping(path = "/name-filter/{nameText}")
    @ApiOperation(value = "Get Paged Programs starting with", notes = "Returns list of Programs starting with passed String")
    @ResponseBody
    public List<RoomEntityDto> getRoomsByNameContaining(@PathVariable String nameText,
                                                        @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                        @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        return roomService.getAvailablePagedRoomsByNameContaining(nameText, page, pageSize);
    }

    @GetMapping(path = "/building-filter/{buildingText}")
    @ApiOperation(value = "Get Paged Programs starting with", notes = "Returns list of Programs starting with passed String")
    @ResponseBody
    public List<RoomEntityDto> getBuildingsByNameContaining(@PathVariable String buildingText,
                                                            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        return roomService.getAvailablePagedBuildingsByNameContaining(buildingText, page, pageSize);
    }

    @PatchMapping("/delete/{roomId}")
    public ResponseEntity<RoomEntityDto> removeRoom(@PathVariable Long roomId) {
        var updatedRoom = roomService.deleteRoom(roomId);
        return ok(updatedRoom);
    }

    @PatchMapping("/restore/{roomId}")
    public ResponseEntity<RoomEntityDto> restoreRoom(@PathVariable Long roomId) {
        var updatedRoom = roomService.restoreRoom(roomId);
        return ok(updatedRoom);
    }
}