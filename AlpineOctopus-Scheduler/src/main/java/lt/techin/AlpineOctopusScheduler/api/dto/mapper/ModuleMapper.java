package lt.techin.AlpineOctopusScheduler.api.dto.mapper;

import lt.techin.AlpineOctopusScheduler.api.dto.ModuleDto;
import lt.techin.AlpineOctopusScheduler.api.dto.ModuleEntityDto;
import lt.techin.AlpineOctopusScheduler.api.dto.ModuleTestDto;
import lt.techin.AlpineOctopusScheduler.model.Module;

public class ModuleMapper {

    public static ModuleDto toModuleDto(Module module) {
        var moduleDto = new ModuleDto();

        moduleDto.setName(module.getName());
        moduleDto.setDescription(module.getDescription());
//        moduleDto.setCreatedDate(module.getCreatedDate());
//        moduleDto.setModifiedDate(module.getModifiedDate());

        return moduleDto;
    }

    public static Module toModule(ModuleDto moduleDto) {
        var module = new Module();

        module.setName(moduleDto.getName());
        module.setDescription(moduleDto.getDescription());
//        module.setCreatedDate(moduleDto.getCreatedDate());
//        module.setModifiedDate(moduleDto.getModifiedDate());

        return module;
    }

    public static ModuleEntityDto toModuleEntityDto(Module module) {
        var moduleEntityDto = new ModuleEntityDto();

        moduleEntityDto.setId(module.getId());
        moduleEntityDto.setName(module.getName());
        moduleEntityDto.setDescription(module.getDescription());
//        moduleEntityDto.setCreatedDate(module.getCreatedDate());
//        moduleEntityDto.setModifiedDate(module.getModifiedDate());
        moduleEntityDto.setDeleted(module.getDeleted());

        return moduleEntityDto;
    }

    public static Module toModule(ModuleEntityDto moduleEntityDto) {
        var module = new Module();

        module.setId(moduleEntityDto.getId());
        module.setName(moduleEntityDto.getName());
        module.setDescription(moduleEntityDto.getDescription());
//        module.setCreatedDate(moduleEntityDto.getCreatedDate());
//        module.setModifiedDate(moduleEntityDto.getModifiedDate());
        module.setDeleted(moduleEntityDto.getDeleted());

        return module;
    }

    public static ModuleTestDto toModuleTestDto(Module module) {
        var moduleDto = new ModuleTestDto();

        moduleDto.setId(module.getId());
        return moduleDto;
    }

}

