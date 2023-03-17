package lt.techin.AlpineOctopusScheduler.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import lt.techin.AlpineOctopusScheduler.dao.GroupsRepository;
import lt.techin.AlpineOctopusScheduler.exception.SchedulerValidationException;
import lt.techin.AlpineOctopusScheduler.model.Groups;

@ExtendWith(MockitoExtension.class)
public class GroupServiceTest {

	@Mock
	GroupsRepository  groupRepository;

	@InjectMocks
	GroupService groupService;
	
	@Test
    void update_groupNotFound_throwsException() {
    	
        Groups group = lt.techin.AlpineOctopusScheduler.stubs.GroupCreator.createGroup(10l);

        when(groupRepository.findById(10l)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> groupService.replace(10l, group))
                .isInstanceOf(SchedulerValidationException.class)
                .hasMessageContaining("Group does not exist")
                .hasFieldOrPropertyWithValue("rejectedValue", "10");
    }

}
