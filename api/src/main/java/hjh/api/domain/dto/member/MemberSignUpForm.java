package hjh.api.domain.dto.member;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MemberSignUpForm {

  @NotEmpty(message = "아이디를 입력해주세요.")
  private String loginId;
  
  @NotEmpty(message = "비밀번호를 입력해주세요.")
  private String password;
  
  @NotEmpty(message = "이메일을 입력해주세요.")
  private String email;

}
