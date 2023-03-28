package lt.techin.AlpineOctopusScheduler.stubs;

import lt.techin.AlpineOctopusScheduler.model.Holiday;

import java.time.LocalDate;

public class HolidayCreator {
    public static Holiday createHoliday(Long id) {
        var holiday = new Holiday();

        holiday.setId(id);
        holiday.setName("naujos atostogos");
        holiday.setStartDate(LocalDate.parse("2019-08-16"));
        holiday.setEndDate(LocalDate.parse("2021-09-20"));
        holiday.setReccuring(true);

        return holiday;
    }
}
