package lt.techin.AlpineOctopusScheduler.stubs;

import lt.techin.AlpineOctopusScheduler.dao.ProgramRepository;
import lt.techin.AlpineOctopusScheduler.model.Groups;

public class GroupsCreator {

    private static ProgramRepository programRepository;

    public static Groups createGroups(Long id) {
        var groups = new Groups();

        groups.setId(id);
        groups.setName("Nauja");
        groups.setSchoolYear(2024);
        groups.setStudentAmount(32);
        groups.setShift("Rytas");

        return groups;
    }
}
