package hjh.mag.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageBox {

    private Valid valid;
    private String msg;
    private Object body;

    public MessageBox(Valid valid, String msg) {
        this.valid = valid;
        this.msg = msg;
    }

}
