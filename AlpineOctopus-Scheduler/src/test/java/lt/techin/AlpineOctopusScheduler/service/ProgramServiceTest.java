package lt.techin.AlpineOctopusScheduler.service;

import lt.techin.AlpineOctopusScheduler.dao.ProgramRepository;
import lt.techin.AlpineOctopusScheduler.exception.SchedulerValidationException;
import lt.techin.AlpineOctopusScheduler.model.Program;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static lt.techin.AlpineOctopusScheduler.stubs.ProgramCreator.createProgram;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProgramServiceTest {

    @Mock
    ProgramRepository programRepository;

    @InjectMocks
    ProgramService programService;

    @Test
    void update_programNotFound_throwsException() {
        var program = createProgram(1l);

        when(programRepository.findById(1l)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> programService.update(1l, program))
                .isInstanceOf(SchedulerValidationException.class)
                .hasMessageContaining("Program does not exist")
                .hasFieldOrPropertyWithValue("rejectedValue", "1");
    }


    @Test
    public void getAll_ReturnsProgramTest() {

        List<Program> expectedPrograms = new ArrayList<>();
        expectedPrograms.add(lt.techin.AlpineOctopusScheduler.stubs.ProgramCreator.createProgram(12l));
        expectedPrograms.add(lt.techin.AlpineOctopusScheduler.stubs.ProgramCreator.createProgram(13l));

        Mockito.when(programRepository.findAll()).thenReturn(expectedPrograms);

        List<Program> actualShifts = programService.getAll();

        Assertions.assertThat(expectedPrograms.size()).isEqualTo(actualShifts.size());


        Assertions.assertThat(expectedPrograms.get(0)).isEqualTo(actualShifts.get(0));
        Assertions.assertThat(expectedPrograms.get(1)).isEqualTo(actualShifts.get(1));


    }

    @Test
    public void testCreateProgram() {

        Program program = lt.techin.AlpineOctopusScheduler.stubs.ProgramCreator.createProgram(12l);


        Mockito.when(programRepository.save(Mockito.any(Program.class))).thenReturn(program);

        Program savedProgram = programService.create(program);

        Mockito.verify(programRepository, Mockito.times(1)).save(Mockito.any(Program.class));


        // i am not sure about asserting in the best way.
        Assertions.assertThat(program.getId()).isEqualTo(savedProgram.getId());

    }

    @Test
    public void testDeleteProgram() {

        Program deletedItem = lt.techin.AlpineOctopusScheduler.stubs.ProgramCreator.createProgram(12l);
        Mockito.when(programRepository.findById(12l)).thenReturn(Optional.of(deletedItem));

        programService.deleteProgram(12l);

        Mockito.verify(programRepository, Mockito.times(1)).save(deletedItem);
        Assertions.assertThat(deletedItem.getDeleted()).isEqualTo(true);

    }

//    @Test
//	public void testDeleteById() {
//
//		// Perform delete
//		programService.deleteById(1l);
//
//		// Verify that the delete method was called with the correct shift ID
//		Mockito.verify(programRepository, Mockito.times(1)).deleteById(1l);
//	}

    @Test
    public void testRestoreShift() {

        Program restoredItem = lt.techin.AlpineOctopusScheduler.stubs.ProgramCreator.createProgram(12l);
        Mockito.when(programRepository.findById(12l)).thenReturn(Optional.of(restoredItem));

        programService.restoreProgram(12l);

        Mockito.verify(programRepository, Mockito.times(1)).save(restoredItem);
        Assertions.assertThat(restoredItem.getDeleted()).isEqualTo(false);

    }


}