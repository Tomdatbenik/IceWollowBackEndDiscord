package serverwebsocket.messages;

import lombok.Getter;

@Getter
public class InitIdMessage extends BaseMessage{

    private String user_id;
    private String rtc_id;
}
