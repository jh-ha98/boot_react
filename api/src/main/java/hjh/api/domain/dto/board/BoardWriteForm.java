package hjh.api.domain.dto.board;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BoardWriteForm {

  @NotEmpty(message = "제목을 입력해주세요.")
  private String title;

  @NotEmpty(message = "내용을 입력해주세요.")
  private String content;
}
