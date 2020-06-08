package restmodule.models.dtomodels;

import lombok.Getter;
import restmodule.models.User;

@Getter
public class LeaveServerDto {
    private User user;
    private int server_id;
}
