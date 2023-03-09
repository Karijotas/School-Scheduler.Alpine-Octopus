package lt.techin.AlpineOctopusScheduler.stubs;

import lt.techin.AlpineOctopusScheduler.model.Shift;

public class ShiftCreator {
	
	public static Shift createShift(Long id) {
		
        var shift = new Shift();

        shift.setId(id);
        shift.setName("Rytaz");
        shift.setStarts(9);
        shift.setEnds(13);
        

        return shift;
    }

}
