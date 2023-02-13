package lt.techin.AlpineOctopusScheduler.api.dto;

import java.time.LocalDateTime;

public interface ProgramsDtoForSearch {

    String getName();

    String getDescription();

    LocalDateTime getCreatedDate();

    LocalDateTime getModifiedDate();

}
