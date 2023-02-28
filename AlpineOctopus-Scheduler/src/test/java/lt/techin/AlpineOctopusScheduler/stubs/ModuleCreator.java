package lt.techin.AlpineOctopusScheduler.stubs;


import lt.techin.AlpineOctopusScheduler.model.Module;


public class ModuleCreator {

    public static Module createModule(Long id) {
        var module = new Module();

        module.setId(id);
        module.setName("Tikslieji mokslai");
        module.setDescription("skaiciukai zenkliukai");
        module.setCreatedDate(null);
        module.setModifiedDate(null);

        return module;
    }
}
