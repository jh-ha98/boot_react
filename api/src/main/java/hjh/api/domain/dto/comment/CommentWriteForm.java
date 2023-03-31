package hjh.api.domain.dto.comment;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CommentWriteForm {
  @NotNull(message = "잘못된 게시글입니다.")
  private Long boardId;

  @NotEmpty(message = "댓글을 입력해주세요.")
  private String comment;
}
