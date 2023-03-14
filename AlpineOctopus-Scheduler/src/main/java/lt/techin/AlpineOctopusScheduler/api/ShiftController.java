package lt.techin.AlpineOctopusScheduler.api;

import io.swagger.annotations.ApiOperation;
import lt.techin.AlpineOctopusScheduler.api.dto.ShiftDto;
import lt.techin.AlpineOctopusScheduler.api.dto.ShiftEntityDto;
import lt.techin.AlpineOctopusScheduler.api.dto.ShiftTestDto;
import lt.techin.AlpineOctopusScheduler.exception.SchedulerValidationException;
import lt.techin.AlpineOctopusScheduler.model.Shift;
import lt.techin.AlpineOctopusScheduler.service.ShiftService;
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

import static lt.techin.AlpineOctopusScheduler.api.dto.mapper.ShiftMapper.toShift;
import static lt.techin.AlpineOctopusScheduler.api.dto.mapper.ShiftMapper.toShiftDto;
import static org.springframework.http.ResponseEntity.ok;

@Controller
@RequestMapping("/api/v1/shifts")
@Validated
public class ShiftController {
    public static Logger logger = LoggerFactory.getLogger(SubjectController.class);
    private final ShiftService shiftService;

    public ShiftController(ShiftService shiftService) {
        this.shiftService = shiftService;
    }


//    @GetMapping(path = "/all")
//    @ResponseBody
//    public List<ShiftTestDto> getAllShifts() {
//        return shiftService.getAllShifts();
//    }

    @GetMapping
    @ResponseBody
    public List<ShiftEntityDto> getAvailableShifts() {
        return shiftService.getAllAvailableShifts();
    }


    @GetMapping(path = "/all")
    @ResponseBody
    public List<ShiftTestDto> getAllShifts() {
        return shiftService.getAllShifts();
    }

    @GetMapping(path = "/archive/", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<ShiftEntityDto> getDeletedShifts() {
        return shiftService.getAllDeletedShifts();
    }

    @GetMapping(path = "/page", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<ShiftEntityDto> getPagedAvailableShifts(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                        @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {

        Pageable pageable = PageRequest.of(page, pageSize);
        return shiftService.getAllAvailablePagedShifts(page, pageSize);
    }

    @GetMapping(path = "/archive/page", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<ShiftEntityDto> getPagedDeletedShifts(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                      @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {

        return shiftService.getAllDeletedPagedShifts(page, pageSize);
    }

//    @GetMapping
//    @ResponseBody
//    public List<ShiftEntityDto> getShifts() {
//        return shiftService.getAll().stream()
//                .map(ShiftMapper::toShiftEntityDto)
//                .collect(toList());
//    }

    @GetMapping(path = "/page/all", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<ShiftEntityDto> getPagedAllShifts(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                  @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        return shiftService.getPagedAllShifts(page, pageSize);
    }

    @GetMapping(path = "page/name-filter/{nameText}")
    @ApiOperation(value = "Get Paged Shifts starting with", notes = "Returns list of Shifts starting with passed String")
    @ResponseBody
    public List<ShiftEntityDto> getPagedShiftsByNameContaining(@PathVariable String nameText,
                                                               @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                               @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        return shiftService.getAvailablePagedShiftsByNameContaining(nameText, page, pageSize);
    }


    @PostMapping
    public ResponseEntity<ShiftDto> createShift(@Valid @RequestBody ShiftDto shiftDto) {

        if (shiftService.shiftNameIsUnique(toShift(shiftDto))) {
            var createdShift = shiftService.create(toShift(shiftDto));
            return ok(toShiftDto(createdShift));
        } else {
            throw new SchedulerValidationException("Shift already exists", "Shift name", "Already exists", shiftDto.getName());
        }
    }

    @DeleteMapping("/{shiftId}")
    public ResponseEntity<Void> deleteShift(@PathVariable Long shiftId) {
        var shiftDeleted = shiftService.deleteById(shiftId);

        if (shiftDeleted) {
            return ResponseEntity.noContent().build();
//            new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{shiftId}")
    public ResponseEntity<ShiftDto> updateShift(@PathVariable Long shiftId, @Valid @RequestBody ShiftDto shiftDto) {

        var updatedShift = shiftService.update(shiftId, toShift(shiftDto));
        return ok(toShiftDto(updatedShift));
    }


    @GetMapping(value = "/{shiftId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Shift> getShift(@PathVariable Long shiftId) {
        var shiftOptional = shiftService.getById(shiftId);

        var responseEntity = shiftOptional
                .map(shift -> ok(shift))
                .orElseGet(() -> ResponseEntity.notFound().build());

        return responseEntity;
    }

    @PatchMapping("/delete/{shiftId}")
    public ResponseEntity<ShiftEntityDto> removeShift(@PathVariable Long shiftId) {
        var updatedShift = shiftService.deleteShift(shiftId);
        return ok(updatedShift);
    }

    @PatchMapping("/restore/{shiftId}")
    public ResponseEntity<ShiftEntityDto> restoreShift(@PathVariable Long shiftId) {
        var updatedShift = shiftService.restoreShift(shiftId);
        return ok(updatedShift);
    }
}

