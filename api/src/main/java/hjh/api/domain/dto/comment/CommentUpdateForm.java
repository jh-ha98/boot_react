package hjh.api.domain.dto.comment;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CommentUpdateForm {
  @NotEmpty(message = "수정할 내용을 입력해 주세요.")
  private String comment;
}
