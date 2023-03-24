package lt.techin.AlpineOctopusScheduler.api;

import lt.techin.AlpineOctopusScheduler.api.dto.HolidayDto;
import lt.techin.AlpineOctopusScheduler.api.dto.HolidayEntityDto;
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


    @GetMapping
    @ResponseBody
    public List<HolidayEntityDto> getAllHolidays() {
        return holidayService.getAll().stream()
                .map(HolidayMapper::toHolidayEntityDto)
                .collect(toList());
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
        boolean holidayDeleted = holidayService.deleteById(id);
        if (holidayDeleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping(path = "/page", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<HolidayEntityDto> getPagedHolidays(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                   @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {

        return holidayService.getAllPagedHolidays(page, pageSize);

    }

    //    @GetMapping(path = "/name-filter/{nameText}")
//    @ApiOperation(value = "Get Holidays starting with", notes = "Returns list of Holidays starting with passed String")
//    @ResponseBody
//    public List<HolidayEntityDto> getHolidaysByNameContaining(@PathVariable String nameText) {
//        return holidayService.getHolidaysByNameContaining(nameText);
//    }
//
//    @GetMapping(path = "/date-filter", produces = MediaType.APPLICATION_JSON_VALUE)
//    @ApiOperation(value = "Get Holidays starting with", notes = "Returns list of Holidays starting with passed String")
//    @ResponseBody
//    public List<HolidayEntityDto> getHolidaysByDateRange(
//            @RequestParam(value = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
//            @RequestParam(value = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
//        return holidayService.getHolidaysByDateRange(startDate, endDate);
//    }
//    @GetMapping(path = "page/starting-date-filter/{startingDate}")
//    @ApiOperation(value = "Get paged schedules by starting date", notes = "Returns list of holidays by starting date with passed String")
//    @ResponseBody
//    public List<HolidayEntityDto> getPagedHolidaysByStartingDate(@PathVariable String startingDate,
//                                                                 @RequestParam(value = "page", defaultValue = "0", required = false) int page,
//                                                                 @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
//
//        return holidayService.getPagedHolidaysByStartingDate(startingDate, page, pageSize);
//    }
//
//    @GetMapping(path = "page/name-filter/{nameText}")
//    @ApiOperation(value = "Get paged Holidays starting with", notes = "Returns list of Holidays starting with passed String")
//    @ResponseBody
//    public List<HolidayEntityDto> getPagedHolidaysByNameContaining(@PathVariable String nameText,
//                                                                   @RequestParam(value = "page", defaultValue = "0", required = false) int page,
//                                                                   @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
//
//        return holidayService.getPagedHolidaysByNameContaining(nameText, page, pageSize);
//    }


}
