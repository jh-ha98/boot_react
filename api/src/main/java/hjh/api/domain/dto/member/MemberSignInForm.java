package hjh.api.domain.dto.member;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MemberSignInForm {

  @NotEmpty(message = "아이디를 입력해주세요.")
  private String loginId;

  @NotEmpty(message = "비밀번호를 입력해주세요.")
  private String password;

}
