package hjh.mag.domain.dto.member;

import hjh.mag.domain.entity.Member;
import hjh.mag.domain.type.MemberRoll;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MemberInfo {
  private Long memberId;
  private String loginId;
  private String email;
  private MemberRoll roll;

  public MemberInfo(Member member) {
    memberId = member.getId();
    loginId = member.getLoginId();
    email = member.getEmail();
    roll = member.getRoll();
  }
}
