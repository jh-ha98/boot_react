package hjh.mag.domain.dto.comment;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CommentWriteForm {
  private Long boardId;
  private String comment;
}
