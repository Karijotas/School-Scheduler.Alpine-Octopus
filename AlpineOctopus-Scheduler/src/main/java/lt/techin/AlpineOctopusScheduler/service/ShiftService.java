package lt.techin.AlpineOctopusScheduler.service;

import lt.techin.AlpineOctopusScheduler.api.dto.ShiftEntityDto;
import lt.techin.AlpineOctopusScheduler.api.dto.ShiftTestDto;
import lt.techin.AlpineOctopusScheduler.api.dto.mapper.ShiftMapper;
import lt.techin.AlpineOctopusScheduler.dao.ShiftRepository;
import lt.techin.AlpineOctopusScheduler.exception.SchedulerValidationException;
import lt.techin.AlpineOctopusScheduler.model.Shift;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShiftService {

    ShiftRepository shiftRepository;

    @Autowired
    public ShiftService(ShiftRepository shiftRepository) {
        this.shiftRepository = shiftRepository;
    }


    public List<Shift> getAll() {
        return shiftRepository.findAll();
    }

    public List<ShiftTestDto> getAllShifts() {
        return shiftRepository.findAll().stream()
                .map(ShiftMapper::toShiftTestDto).collect(Collectors.toList());
    }

    public List<ShiftEntityDto> getPagedAllShifts(int page, int pageSize) {

        Pageable pageable = PageRequest.of(page, pageSize);
        return shiftRepository.findAll(pageable).stream()
                .map(ShiftMapper::toShiftEntityDto).collect(Collectors.toList());
    }

    public Optional<Shift> getById(Long id) {
        return shiftRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<ShiftEntityDto> getPagedShiftsByNameContaining(String nameText, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return shiftRepository.findByNameContainingIgnoreCase(nameText, pageable).stream()
                .map(ShiftMapper::toShiftEntityDto).collect(Collectors.toList());
    }

    public Shift create(Shift shift) {
        return shiftRepository.save(shift);
    }

    public Shift update(Long id, Shift shift) {
        var existingShift = shiftRepository.findById(id)
                .orElseThrow(() -> new SchedulerValidationException("Shift does not exist",
                        "id", "Shift not found", id.toString()));

        existingShift.setName(shift.getName());
        existingShift.setStarts(shift.getStarts());
        existingShift.setEnds(shift.getEnds());
        existingShift.setCreatedDate(shift.getCreatedDate());
        existingShift.setModifiedDate(shift.getModifiedDate());

        return shiftRepository.save(existingShift);
    }

    public Boolean deleteById(Long id) {
        try {
            shiftRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

}
