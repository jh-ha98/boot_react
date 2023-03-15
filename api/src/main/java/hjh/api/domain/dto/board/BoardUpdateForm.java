package hjh.api.domain.dto.board;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BoardUpdateForm {
  private Long id;
  private String title;
  private String content;
}
