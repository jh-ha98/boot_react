package hjh.mag.domain.dto.board;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BoardWriteForm {
  private String title;
  private String content;
}
