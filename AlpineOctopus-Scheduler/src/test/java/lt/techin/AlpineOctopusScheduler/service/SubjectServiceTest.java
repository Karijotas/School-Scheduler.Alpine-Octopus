package lt.techin.AlpineOctopusScheduler.service;

import lt.techin.AlpineOctopusScheduler.dao.SubjectRepository;
import lt.techin.AlpineOctopusScheduler.exception.SchedulerValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static lt.techin.AlpineOctopusScheduler.stubs.SubjectCreator.createSubject;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubjectServiceTest {


    @Mock
    SubjectRepository subjectRepository;

    @InjectMocks
    SubjectService subjectService;

    @Test
    void update_subjectNotFound_throwsException() {
        var subject = createSubject(1l);

        when(subjectRepository.findById(1l)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> subjectService.update(1l, subject))
                .isInstanceOf(SchedulerValidationException.class)
                .hasMessageContaining("Subject does not exist")
                .hasFieldOrPropertyWithValue("rejectedValue", "1");
    }

}