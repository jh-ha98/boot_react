package hjh.mag.domain.dto.member;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MemberSignUpForm {
  private String loginId;
  private String password;
  private String email;

}
