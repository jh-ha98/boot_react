package hjh.api.domain.dto.board;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BoardUpdateForm {
  private Long id;

  @NotEmpty(message = "수정할 제목을 입력해주세요.")
  private String title;

  @NotEmpty(message = "수정할 내용을 입력해주세요.")
  private String content;
}
