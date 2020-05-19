package restmodule.models.dtomodels;

import lombok.Getter;
import restmodule.models.User;

@Getter
public class JoinServerDto {
    private User user;
    private String code;
}
