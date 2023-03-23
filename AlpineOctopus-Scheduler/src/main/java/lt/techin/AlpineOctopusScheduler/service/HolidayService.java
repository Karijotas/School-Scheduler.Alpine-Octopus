package lt.techin.AlpineOctopusScheduler.service;

import lt.techin.AlpineOctopusScheduler.api.dto.HolidayEntityDto;
import lt.techin.AlpineOctopusScheduler.api.dto.HolidayTestDto;
import lt.techin.AlpineOctopusScheduler.api.dto.mapper.HolidayMapper;
import lt.techin.AlpineOctopusScheduler.dao.HolidayRepository;
import lt.techin.AlpineOctopusScheduler.exception.SchedulerValidationException;
import lt.techin.AlpineOctopusScheduler.model.Holiday;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HolidayService {
    private final HolidayRepository holidayRepository;


    @Autowired
    public HolidayService(HolidayRepository holidayRepository) {
        this.holidayRepository = holidayRepository;

    }

    public boolean classIsUnique(Holiday holiday) {
        return holidayRepository.findAll()
                .stream()
                .noneMatch(holiday1 -> holiday1.getName().equals(holiday.getName()));
    }

    public List<HolidayTestDto> getAllHolidays() {
        return holidayRepository.findAll().stream()
                .map(HolidayMapper::toHolidayTestDto).collect(Collectors.toList());
    }

    public List<HolidayEntityDto> getAllPagedHolidays(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return holidayRepository.findAll(pageable).stream()
                .map(HolidayMapper::toHolidayEntityDto).collect(Collectors.toList());
    }

    public List<Holiday> getAll() {
        return holidayRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<HolidayEntityDto> getHolidaysByNameContaining(String nameText) {

        return holidayRepository.findByNameContainingIgnoreCase(nameText).stream()
                .map(HolidayMapper::toHolidayEntityDto).collect(Collectors.toList());
    }

    public Optional<Holiday> getById(Long id) {
        return holidayRepository.findById(id);
    }

    public Holiday create(Holiday holiday) {
        var newHoliday = new Holiday();
        newHoliday.setId(holiday.getId());
        newHoliday.setName(holiday.getName());
        newHoliday.setStartDate(holiday.getStartDate());
        newHoliday.setEndDate(holiday.getEndDate());
        return holidayRepository.save(newHoliday);

    }

    public Holiday update(Long id, Holiday holiday) {
        var existingHoliday = holidayRepository.findById(id)
                .orElseThrow(() -> new SchedulerValidationException("Holiday does not exist", "id", "Holiday not found", id.toString()));
        existingHoliday.setName(holiday.getName());
        existingHoliday.setStartDate(holiday.getStartDate());
        existingHoliday.setEndDate(holiday.getEndDate());

        return holidayRepository.save(existingHoliday);
    }

    public boolean deleteById(Long id) {
        if (holidayRepository.existsById(id)) {
            holidayRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
