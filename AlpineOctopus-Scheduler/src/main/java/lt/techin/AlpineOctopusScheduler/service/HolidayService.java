package lt.techin.AlpineOctopusScheduler.service;

import lt.techin.AlpineOctopusScheduler.api.dto.HolidayEntityDto;
import lt.techin.AlpineOctopusScheduler.api.dto.HolidayTestDto;
import lt.techin.AlpineOctopusScheduler.api.dto.mapper.HolidayMapper;
import lt.techin.AlpineOctopusScheduler.dao.HolidayRepository;
import lt.techin.AlpineOctopusScheduler.exception.SchedulerValidationException;
import lt.techin.AlpineOctopusScheduler.model.Holiday;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
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

    public List<Holiday> getAll() {
        return holidayRepository.findAll();
    }

    public List<HolidayTestDto> getAllHolidays() {
        return holidayRepository.findAll().stream()
                .map(HolidayMapper::toHolidayTestDto).collect(Collectors.toList());
    }

    public List<HolidayEntityDto> getAllPagedHolidays(int page, int pageSize) {
        LocalDate startDate = LocalDate.now().withDayOfYear(1); // Get the start of the current year
        List<Holiday> allHolidays = holidayRepository.findAll();

        List<HolidayEntityDto> holidayDtos = allHolidays.stream()
                .filter(holiday -> !holiday.getStartDate().isBefore(startDate)) // Filter holidays to show only those from the start of the current year or later
                .sorted(Comparator.comparing(Holiday::getStartDate))
                .map(HolidayMapper::toHolidayEntityDto)
                .collect(Collectors.toList());

        int fromIndex = page * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, holidayDtos.size());
        return holidayDtos.subList(fromIndex, toIndex);
    }

    @Transactional(readOnly = true)
    public List<HolidayEntityDto> getHolidaysByNameContaining(String nameText) {

        return holidayRepository.findByNameContainingIgnoreCase(nameText)
                .stream()
                .map(HolidayMapper::toHolidayEntityDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<HolidayEntityDto> getHolidaysByDateRange(String startDate, String endDate) {
//
//        //Provided String must be in a YYYY-MM-DD format. Subtracting one in order for the searched day itself to appear in results
//        var start = LocalDate.parse(startDate).minusDays(1);
//        var end = LocalDate.parse(endDate).minusDays(1);
//
//        return holidayRepository.findByDateRange(start, end)
//                .stream()
//
//                .map(HolidayMapper::toHolidayEntityDto)
//                .collect(Collectors.toList());
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        List<Holiday> holidays = holidayRepository.findByStartDateBetween(start, end);
        return holidays.stream()
                .sorted(Comparator.comparing(Holiday::getStartDate))
                .map(HolidayMapper::toHolidayEntityDto)
                .collect(Collectors.toList());
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
        newHoliday.setReccuring(holiday.getReccuring());
        return holidayRepository.save(newHoliday);
    }

    public Holiday update(Long id, Holiday holiday) {
        var existingHoliday = holidayRepository.findById(id)
                .orElseThrow(() -> new SchedulerValidationException("Holiday does not exist", "id", "Holiday not found", id.toString()));
        existingHoliday.setName(holiday.getName());
        existingHoliday.setStartDate(holiday.getStartDate());
        existingHoliday.setEndDate(holiday.getEndDate());
        existingHoliday.setReccuring(holiday.getReccuring());

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

