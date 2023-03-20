package lt.techin.AlpineOctopusScheduler.api;

import io.swagger.annotations.ApiOperation;
import lt.techin.AlpineOctopusScheduler.api.dto.HolidayDto;
import lt.techin.AlpineOctopusScheduler.api.dto.HolidayEntityDto;
import lt.techin.AlpineOctopusScheduler.api.dto.HolidayTestDto;
import lt.techin.AlpineOctopusScheduler.api.dto.mapper.HolidayMapper;
import lt.techin.AlpineOctopusScheduler.exception.SchedulerValidationException;
import lt.techin.AlpineOctopusScheduler.model.Holiday;
import lt.techin.AlpineOctopusScheduler.service.HolidayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static lt.techin.AlpineOctopusScheduler.api.dto.mapper.HolidayMapper.*;
import static org.springframework.http.ResponseEntity.ok;

@Controller
@RequestMapping("/api/v1/holidays")
@Validated
public class HolidayController {
    private final Logger logger = LoggerFactory.getLogger(RoomController.class);
    private final HolidayService holidayService;

    public HolidayController(HolidayService holidayService) {
        this.holidayService = holidayService;
    }

    //////////////////////////////////////////
    @GetMapping
    @ResponseBody
    public List<HolidayEntityDto> getAllHolidays() {
        return holidayService.getAll().stream()
                .map(HolidayMapper::toHolidayEntityDto)
                .collect(toList());
    }

    @GetMapping(path = "/all")
    @ResponseBody
    public List<HolidayTestDto> getAllHoliday() {
        return holidayService.getAllHolidays();
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Holiday> getHoliday(@PathVariable Long id) {
        var holidayOptional = holidayService.getById(id);

        var responseEntity = holidayOptional
                .map(holiday -> ok(holiday))
                .orElseGet(() -> ResponseEntity.notFound().build());

        return responseEntity;
    }

    @PostMapping
    public ResponseEntity<HolidayEntityDto> createHoliday(@Valid @RequestBody HolidayEntityDto holidayDto) {
        if (holidayService.classIsUnique(toHoliday(holidayDto))) {
            var createdHoliday = holidayService.create(toHoliday(holidayDto));
            return ok(toHolidayEntityDto(createdHoliday));
        } else {
            throw new SchedulerValidationException("Class already exists", "Class name & building adress", "Already exists", holidayDto.getName());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<HolidayDto> updateHoliday(@PathVariable Long id, @Valid @RequestBody HolidayDto holidayDto) {
        var updatedHoliday = holidayService.update(id, toHoliday(holidayDto));
        return ok(toHolidayDto(updatedHoliday));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHoliday(@PathVariable Long id) {
        var holidayDeleted = holidayService.deleteById(id);

        if (holidayDeleted) {
            return ResponseEntity.noContent().build();
//            new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.notFound().build();
    }

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
    @ApiOperation(value = "Get Paged Holidays starting with", notes = "Returns list of Holidayss starting with passed String")
    @ResponseBody
    public List<HolidayEntityDto> getHolidaysByNameContaining(@PathVariable String nameText,
                                                              @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                              @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        return holidayService.getAvailablePagedHolidaysByNameContaining(nameText, page, pageSize);
    }

//    @GetMapping(path = "/building-filter/{buildingText}")
//    @ApiOperation(value = "Get Paged Programs starting with", notes = "Returns list of Programs starting with passed String")
//    @ResponseBody
//    public List<RoomEntityDto> getBuildingsByNameContaining(@PathVariable String buildingText,
//                                                            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
//                                                            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
//        return roomService.getAvailablePagedBuildingsByNameContaining(buildingText, page, pageSize);
//    }

}
