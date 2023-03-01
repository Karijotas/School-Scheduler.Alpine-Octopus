package lt.techin.AlpineOctopusScheduler.service;

import lt.techin.AlpineOctopusScheduler.api.dto.ShiftEntityDto;
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

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ShiftService {

    ShiftRepository shiftRepository;

    private final Validator validator;

    @Autowired
    public ShiftService(ShiftRepository shiftRepository, Validator validator) {
        this.shiftRepository = shiftRepository;
        this.validator = validator;
    }

    void validateInputWithInjectedValidator(Shift shift) {
        Set<ConstraintViolation<Shift>> violations = validator.validate(shift);
        if (!violations.isEmpty()) {
            throw new SchedulerValidationException(violations.toString(), "Shift", "Error in shift entity", shift.toString());
        }
    }

    public boolean shiftNameIsUnique(Shift shift) {
        return shiftRepository.findAll()
                .stream()
                .noneMatch(shift1 -> shift1.getName().equals(shift.getName()));
    }

    public List<Shift> getAll() {
        return shiftRepository.findAll();
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
