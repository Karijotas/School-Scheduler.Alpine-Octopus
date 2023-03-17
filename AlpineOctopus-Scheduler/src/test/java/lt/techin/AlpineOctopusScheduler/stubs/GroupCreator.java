package lt.techin.AlpineOctopusScheduler.stubs;

import lt.techin.AlpineOctopusScheduler.model.Groups;
import lt.techin.AlpineOctopusScheduler.model.Program;
import lt.techin.AlpineOctopusScheduler.model.Shift;

public class GroupCreator {

public static Groups createGroup(Long id) {
		
        var group = new Groups();
        
        Program program = new Program();
        program.setId(1l);
        
        Shift shift = lt.techin.AlpineOctopusScheduler.stubs.ShiftCreator.createShift(1l);

        group.setId(id);
        group.setName("Nauja Grupe");
        group.setSchoolYear(2023);
        group.setStudentAmount(25);
        
        group.setProgram(program);
        group.setShift(shift);
        


        return group;
    }
	
	
}
