package lt.techin.AlpineOctopusScheduler.service;

import static lt.techin.AlpineOctopusScheduler.stubs.ModuleCreator.createModule;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import lt.techin.AlpineOctopusScheduler.dao.ModuleRepository;
import lt.techin.AlpineOctopusScheduler.exception.SchedulerValidationException;


@ExtendWith(MockitoExtension.class)
class ModuleServiceTest {


    @Mock
    ModuleRepository moduleRepository;

    @InjectMocks
    ModuleService moduleService;

    @Test
    void update_moduleNotFound_throwsException() {
        var module = createModule(1l);

        when(moduleRepository.findById(1l)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> moduleService.update(1l, module))
                .isInstanceOf(SchedulerValidationException.class)
                .hasMessageContaining("Module does not exist")
                .hasFieldOrPropertyWithValue("rejectedValue", "1");
    }

}