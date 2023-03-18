package hjh.api.domain.dto.common;

import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import hjh.api.domain.type.MessageBoxValid;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageBox<T> {

    private MessageBoxValid valid;
    private String msg;
    private T body;

    public MessageBox(MessageBoxValid valid, String msg) {
        this.valid = valid;
        this.msg = msg;
    }

    /** 실패 메시지를 생성합니다 */
    public static <T> MessageBox<T> failed(BindingResult bindingResult) {
        List<ObjectError> errors = bindingResult.getAllErrors();

        String errorMessage = "";
        for (int i = 0; i < errors.size(); i++) {
            if (i == 0) {
                errorMessage = errors.get(i).getDefaultMessage();
                break;
            }
        }

        return new MessageBox<>(MessageBoxValid.FALSE, errorMessage);
    }

}
