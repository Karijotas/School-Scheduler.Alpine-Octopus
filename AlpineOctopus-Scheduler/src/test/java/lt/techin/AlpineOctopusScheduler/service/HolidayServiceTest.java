package lt.techin.AlpineOctopusScheduler.service;

import lt.techin.AlpineOctopusScheduler.dao.HolidayRepository;
import lt.techin.AlpineOctopusScheduler.exception.SchedulerValidationException;
import lt.techin.AlpineOctopusScheduler.model.Holiday;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HolidayServiceTest {

    @Mock
    HolidayRepository holidayRepository;

    @InjectMocks
    HolidayService holidayService;


    @Test
    void update_holidayNotFound_throwsException() {

        Holiday holiday = lt.techin.AlpineOctopusScheduler.stubs.HolidayCreator.createHoliday(123l);

        when(holidayRepository.findById(123l)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> holidayService.update(123l, holiday))
                .isInstanceOf(SchedulerValidationException.class)
                .hasMessageContaining("Holiday does not exist")
                .hasFieldOrPropertyWithValue("rejectedValue", "123");
    }

    @Test
    public void getAll_ReturnsHolidayTest() {

        List<Holiday> extpectedHolidays = new ArrayList<>();
        extpectedHolidays.add(new Holiday(1l, "pirmas Atostogos", LocalDate.parse("2020-01-16"), LocalDate.parse("2021-09-16"), false));
        extpectedHolidays.add(new Holiday(2l, "antras atostogos", LocalDate.parse("2020-01-16"), LocalDate.parse("2021-09-16"), false));

        Mockito.when(holidayRepository.findAll()).thenReturn(extpectedHolidays);

        List<Holiday> actualHolidays = holidayService.getAll();

        Assertions.assertThat(extpectedHolidays.size()).isEqualTo(actualHolidays.size());


        Assertions.assertThat(extpectedHolidays.get(0)).isEqualTo(actualHolidays.get(0));
        Assertions.assertThat(extpectedHolidays.get(1)).isEqualTo(actualHolidays.get(1));


    }

    @Test
    public void testCreateHoliday() {

        Holiday holiday = lt.techin.AlpineOctopusScheduler.stubs.HolidayCreator.createHoliday(12l);


        Mockito.when(holidayRepository.save(Mockito.any(Holiday.class))).thenReturn(holiday);

        Holiday savedHoliday = holidayService.create(holiday);

        Mockito.verify(holidayRepository, Mockito.times(1)).save(Mockito.any(Holiday.class));


        // i am not sure about asserting in the best way.
        Assertions.assertThat(holiday.getId()).isEqualTo(savedHoliday.getId());

    }

    @Test
    public void testDeleteHoliday() {

        // Arrange
        Holiday deletedItem = lt.techin.AlpineOctopusScheduler.stubs.HolidayCreator.createHoliday(12l);
        Mockito.when(holidayRepository.existsById(12l)).thenReturn(true);

        // Act
        boolean isDeleted = holidayService.deleteById(12l);

        // Assert
        Mockito.verify(holidayRepository, Mockito.times(1)).deleteById(12l);
        Assertions.assertThat(isDeleted).isTrue();

    }

//    @Test
//    public void testDeleteById() {
//
//        // Perform delete
//        holidayService.deleteById(1l);
//
//        // Verify that the delete method was called with the correct room ID
//        Mockito.verify(holidayRepository, Mockito.times(1)).deleteById(1l);
//    }

}