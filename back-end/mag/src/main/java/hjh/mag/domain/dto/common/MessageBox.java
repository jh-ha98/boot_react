package hjh.mag.domain.dto.common;

import hjh.mag.domain.type.MessageBoxValid;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageBox {

    private MessageBoxValid valid;
    private String msg;
    private Object body;

    public MessageBox(MessageBoxValid valid, String msg) {
        this.valid = valid;
        this.msg = msg;
    }

}
