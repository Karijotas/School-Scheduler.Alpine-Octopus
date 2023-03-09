package lt.techin.AlpineOctopusScheduler.stubs;

import java.util.HashSet;
import java.util.Set;

import lt.techin.AlpineOctopusScheduler.model.Shift;
import lt.techin.AlpineOctopusScheduler.model.Teacher;

public class TeacherCreator {

public static Teacher createTeacher(Long id) {
		
        var teacher = new Teacher();
        
        Set<Shift> shifts = new HashSet<>();
        shifts.add(lt.techin.AlpineOctopusScheduler.stubs.ShiftCreator.createShift(12l));

        teacher.setId(id);
        teacher.setName("Mantvydas");
        teacher.setLoginEmail("login@email.com");
        teacher.setContactEmail("login@email.com");
        teacher.setPhone("+37012312312");
        teacher.setWorkHoursPerWeek(20);
        
        teacher.setTeacherShifts(shifts);

        teacher.setDeleted(false);
         
        return teacher;
    }	
}
