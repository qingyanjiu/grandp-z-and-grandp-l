package live.moku.mqexercise;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Message {

    private int uuid;

    private String content;
}
