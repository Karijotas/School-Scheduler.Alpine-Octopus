package lt.techin.AlpineOctopusScheduler.service;

import static lt.techin.AlpineOctopusScheduler.stubs.ModuleCreator.createModule;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

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

import lt.techin.AlpineOctopusScheduler.dao.ModuleRepository;
import lt.techin.AlpineOctopusScheduler.model.Module;
import lt.techin.AlpineOctopusScheduler.model.Shift;


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
//package lt.techin.AlpineOctopusScheduler.service;
//
//import lt.techin.AlpineOctopusScheduler.dao.ModuleRepository;
//import lt.techin.AlpineOctopusScheduler.exception.SchedulerValidationException;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Optional;
//
//import static lt.techin.AlpineOctopusScheduler.stubs.ModuleCreator.createModule;
//import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
//import static org.mockito.Mockito.when;
//
//
//@ExtendWith(MockitoExtension.class)
//class ModuleServiceTest {
//
//
//    @Mock
//    ModuleRepository moduleRepository;
//
//    @InjectMocks
//    ModuleService moduleService;
//
//    @Test
//    void update_moduleNotFound_throwsException() {
//        var module = createModule(1l);
//
//        when(moduleRepository.findById(1l)).thenReturn(Optional.empty());
//
//        assertThatThrownBy(() -> moduleService.update(1l, module))
//                .isInstanceOf(SchedulerValidationException.class)
                .hasMessageContaining("Module does not exist")
                .hasFieldOrPropertyWithValue("rejectedValue", "1");
    }
    
    @Test
	public void getAll_ReturnsModuleTest() {

		List<Module> expectedModules = new ArrayList<>();
        expectedModules.add(lt.techin.AlpineOctopusScheduler.stubs.ModuleCreator.createModule(12l));
        expectedModules.add(lt.techin.AlpineOctopusScheduler.stubs.ModuleCreator.createModule(13l));

        Mockito.when(moduleRepository.findAll()).thenReturn(expectedModules);

        List<Module> actualShifts = moduleService.getAll();

        Assertions.assertThat(expectedModules.size()).isEqualTo(actualShifts.size());
        
        
        Assertions.assertThat(expectedModules.get(0)).isEqualTo(actualShifts.get(0));
        Assertions.assertThat(expectedModules.get(1)).isEqualTo(actualShifts.get(1));
		
	}
    
    @Test
    public void testCreateModule() {
		
        Module module = lt.techin.AlpineOctopusScheduler.stubs.ModuleCreator.createModule(12l); 
        
        
        Mockito.when(moduleRepository.save(Mockito.any(Module.class))).thenReturn(module);

        Module savedModule = moduleService.create(module);
        
        Mockito.verify(moduleRepository, Mockito.times(1)).save(Mockito.any(Module.class));


        // i am not sure about asserting in the best way.
        Assertions.assertThat(module.getId()).isEqualTo(savedModule.getId());
	
	}
    
    @Test
	public void testDeleteModule() {
		
		Module deletedItem = lt.techin.AlpineOctopusScheduler.stubs.ModuleCreator.createModule(12l); 
        Mockito.when(moduleRepository.findById(12l)).thenReturn(Optional.of(deletedItem));

        moduleService.deleteModule(12l);

        Mockito.verify(moduleRepository, Mockito.times(1)).save(deletedItem);
        Assertions.assertThat(deletedItem.getDeleted()).isEqualTo(true); 
		
	}
    
    @Test
	public void testDeleteById() {
		
		// Perform delete
		moduleService.deleteById(1l);
		
		// Verify that the delete method was called with the correct shift ID
		Mockito.verify(moduleRepository, Mockito.times(1)).deleteById(1l);
	}
    
    
    @Test
	public void testRestoreModule() {
		
		Module restoredItem = lt.techin.AlpineOctopusScheduler.stubs.ModuleCreator.createModule(12l);
        Mockito.when(moduleRepository.findById(12l)).thenReturn(Optional.of(restoredItem));

        moduleService.restoreModule(12l);

        Mockito.verify(moduleRepository, Mockito.times(1)).save(restoredItem);
        Assertions.assertThat(restoredItem.getDeleted()).isEqualTo(false);
		
	}
    
    
    
    

}