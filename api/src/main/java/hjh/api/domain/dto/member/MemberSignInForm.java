package hjh.api.domain.dto.member;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MemberSignInForm {
  private String loginId;
  private String password;
}
