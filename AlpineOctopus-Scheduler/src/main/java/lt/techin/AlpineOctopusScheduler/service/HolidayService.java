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

    public boolean dateRangeOverlap(Holiday holiday) {
//        List<Holiday> holidays = holidayRepository.findAll();
//        for (Holiday h : holidays) {
//            if (h.getId() != holiday.getId() && h.getStartDate().isBefore(holiday.getEndDate()) && holiday.getStartDate().isBefore(h.getEndDate())) {
//                return true;
//            }
//        }
//        return false;
        List<Holiday> holidays = holidayRepository.findAll();
        for (Holiday h : holidays) {
            if (h.getId() != holiday.getId() && (h.getStartDate().isBefore(holiday.getEndDate()) || h.getStartDate().isEqual(holiday.getEndDate())) && (holiday.getStartDate().isBefore(h.getEndDate()) || holiday.getStartDate().isEqual(h.getEndDate()))) {
                return true;
            }
        }
        return false;
    }

    public List<Holiday> getAll() {
        return holidayRepository.findAll();
    }

    public List<HolidayTestDto> getAllHolidays() {
        return holidayRepository.findAll().stream()
                .map(HolidayMapper::toHolidayTestDto).collect(Collectors.toList());
    }

    public List<HolidayEntityDto> getAllPagedHolidays(int page, int pageSize) {
        List<Holiday> allHolidays = holidayRepository.findAll();

        List<HolidayEntityDto> holidayDtos = allHolidays.stream()
                .filter(holiday -> holiday.getStartDate() == null || !holiday.getStartDate().isBefore(LocalDate.now().withDayOfYear(1)))
                .sorted((h1, h2) -> {
                    LocalDate h1StartDate = h1.getStartDate();
                    LocalDate h2StartDate = h2.getStartDate();
                    if (h1StartDate == null && h2StartDate == null) {
                        return 0;
                    } else if (h1StartDate == null) {
                        return -1;
                    } else if (h2StartDate == null) {
                        return 1;
                    } else {
                        return h1StartDate.compareTo(h2StartDate);
                    }
                })
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

