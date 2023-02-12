package lt.techin.AlpineOctopusScheduler.api.dto.mapper;

import lt.techin.AlpineOctopusScheduler.api.dto.ModuleDto;
import lt.techin.AlpineOctopusScheduler.api.dto.ModuleEntityDto;
import lt.techin.AlpineOctopusScheduler.model.Module;

public class ModuleMapper {

    public static ModuleDto toModuleDto(Module module) {
        var moduleDto = new ModuleDto();

        moduleDto.setName(module.getName());
        moduleDto.setDescription(module.getDescription());

        return moduleDto;
    }

    public static Module toModule(ModuleDto moduleDto) {
        var module = new Module();

        module.setName(moduleDto.getName());
        module.setDescription(moduleDto.getDescription());

        return module;
    }

    public static ModuleEntityDto toModuleEntityDto(Module module) {
        var moduleDto = new ModuleEntityDto();

        moduleDto.setId(module.getId());
        moduleDto.setName(module.getName());
        moduleDto.setDescription(module.getDescription());

        return moduleDto;
    }

    public static Module toModule(ModuleEntityDto moduleEntityDto) {
        var module = new Module();

        module.setId(moduleEntityDto.getId());
        module.setName(moduleEntityDto.getName());
        module.setDescription(moduleEntityDto.getDescription());

        return module;
    }

}

