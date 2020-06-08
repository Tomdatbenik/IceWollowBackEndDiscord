package restmodule.models.dtomodels;

import lombok.Getter;
import restmodule.models.User;

@Getter
public class AddServerDto {
    private User owner;
    private String name;
}
