package lt.techin.AlpineOctopusScheduler.service;

import lt.techin.AlpineOctopusScheduler.dao.GroupsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static lt.techin.AlpineOctopusScheduler.stubs.GroupsCreator.createGroups;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class GroupsServiceTest {

    @Mock
    GroupsRepository groupsRepository;

    @InjectMocks
    GroupService groupService;

    @Test
    void updateGroupThrowsExceptionWhenNotFound() {
        var group = createGroups(1l);

        assertThatThrownBy(() -> groupService.update(1l, group, 1l))
                .isInstanceOf(NullPointerException.class);
    }
}
