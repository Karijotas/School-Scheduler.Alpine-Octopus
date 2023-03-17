package lt.techin.AlpineOctopusScheduler.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import lt.techin.AlpineOctopusScheduler.dao.GroupsRepository;
import lt.techin.AlpineOctopusScheduler.exception.SchedulerValidationException;
import lt.techin.AlpineOctopusScheduler.model.Groups;
import lt.techin.AlpineOctopusScheduler.model.Shift;

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
	
	
	@Test
	public void getAll_ReturnsGroupTest() {

		List<Groups> expectedGroups = new ArrayList<>();
        expectedGroups.add(lt.techin.AlpineOctopusScheduler.stubs.GroupCreator.createGroup(13l));
        expectedGroups.add(lt.techin.AlpineOctopusScheduler.stubs.GroupCreator.createGroup(13l));

        Mockito.when(groupRepository.findAll()).thenReturn(expectedGroups);

        List<Groups> actualShifts = groupService.getAll();

        Assertions.assertThat(expectedGroups.size()).isEqualTo(actualShifts.size());
        
        
        Assertions.assertThat(expectedGroups.get(0)).isEqualTo(actualShifts.get(0));
        Assertions.assertThat(expectedGroups.get(1)).isEqualTo(actualShifts.get(1));
		
	}
	
	@Test
    public void testCreateGroup() {
		
		Groups group = lt.techin.AlpineOctopusScheduler.stubs.GroupCreator.createGroup(12l); 
        
        
        Mockito.when(groupRepository.save(Mockito.any(Groups.class))).thenReturn(group);

        Groups savedGroup = groupService.create(group);
        
        Mockito.verify(groupRepository, Mockito.times(1)).save(Mockito.any(Groups.class));


        // i am not sure about asserting in the best way.
        Assertions.assertThat(group.getId()).isEqualTo(savedGroup.getId());
	
	}
	
	@Test
	public void testDeleteGroup() {
		
		Groups deletedItem = lt.techin.AlpineOctopusScheduler.stubs.GroupCreator.createGroup(12l);
        Mockito.when(groupRepository.findById(12l)).thenReturn(Optional.of(deletedItem));

        groupService.deleteGroup(12l);

        Mockito.verify(groupRepository, Mockito.times(1)).save(deletedItem);
        Assertions.assertThat(deletedItem.getDeleted()).isEqualTo(true); 
	}
	
	@Test
	public void testDeleteById() {
		
		// Perform delete
		groupService.deleteById(1l);
		
		// Verify that the delete method was called with the correct shift ID
		Mockito.verify(groupRepository, Mockito.times(1)).deleteById(1l);
	}
	
	@Test
	public void testRestoreShift() {
		
		Groups restoredItem = lt.techin.AlpineOctopusScheduler.stubs.GroupCreator.createGroup(12l);
        Mockito.when(groupRepository.findById(12l)).thenReturn(Optional.of(restoredItem));

        groupService.restoreGroup(12l);

        Mockito.verify(groupRepository, Mockito.times(1)).save(restoredItem);
        Assertions.assertThat(restoredItem.getDeleted()).isEqualTo(false);
		
	}


}
