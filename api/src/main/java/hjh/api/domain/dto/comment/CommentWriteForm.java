package hjh.api.domain.dto.comment;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CommentWriteForm {
  private Long boardId;

  @NotEmpty(message = "댓글을 입력해 주세요.")
  private String comment;
}
