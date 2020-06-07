package serverwebsocket.messages;

import com.google.gson.JsonElement;
import lombok.Getter;

@Getter
public class rtcIdMessage extends BaseMessage{

    private String user_id;
    private JsonElement rtc_id;
}
