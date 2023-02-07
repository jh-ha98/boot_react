package hjh.api;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import hjh.api.domain.entity.Member;
import hjh.api.domain.type.MemberRoll;
import hjh.api.repository.MemberRepository;

@Transactional
@SpringBootTest
public class RollTest {

  @Autowired
  private MemberRepository memberRepository;

  @DisplayName("enum roll type 데이터 적용 확인: User")
  @Test
  public void MemberRollUser() {
    Member member = new Member("1234", "1234", "1234", MemberRoll.USER);

    Member savedMember = memberRepository.save(member);

    assertEquals(MemberRoll.USER, savedMember.getRoll());
  }

  @DisplayName("enum roll type 데이터 적용 확인: Admin")
  @Test
  public void MemberRollAdmin() {
    Member member = new Member("1234", "1234", "1234", MemberRoll.ADMIN);

    Member savedMember = memberRepository.save(member);

    assertEquals(MemberRoll.ADMIN, savedMember.getRoll());
  }

  @DisplayName("roll을 설정 안하면?")
  @Test
  public void MemberNoRoll() {
    Member member = new Member("1234", "1234", "1234", null);

    assertDoesNotThrow(() -> memberRepository.save(member));
  }
}
