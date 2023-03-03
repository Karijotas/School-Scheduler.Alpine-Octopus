//package lt.techin.AlpineOctopusScheduler.service;
//
//import lt.techin.AlpineOctopusScheduler.dao.ProgramRepository;
//import lt.techin.AlpineOctopusScheduler.exception.SchedulerValidationException;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Optional;
//
//import static lt.techin.AlpineOctopusScheduler.stubs.ProgramCreator.createProgram;
//import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class ProgramServiceTest {
//
//    @Mock
//    ProgramRepository programRepository;
//
//    @InjectMocks
//    ProgramService programService;
//
//    @Test
//    void update_programNotFound_throwsException() {
//        var program = createProgram(1l);
//
//        when(programRepository.findById(1l)).thenReturn(Optional.empty());
//
//        assertThatThrownBy(() -> programService.update(1l, program))
//                .isInstanceOf(SchedulerValidationException.class)
//                .hasMessageContaining("Program does not exist")
//                .hasFieldOrPropertyWithValue("rejectedValue", "1");
//    }
//}