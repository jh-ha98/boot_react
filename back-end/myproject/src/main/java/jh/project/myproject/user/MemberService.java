package jh.project.myproject.user;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  MemberRepository memberRepository;

  public Map<String, Object> signUp(Member member) throws Exception {
    Map<String, Object> result = new HashMap<>();

    try {
      if (member.getLoginId().equals("")) {
        result.put("valid", SignValid.False);
        result.put("msg", "아이디를 입력해주세요.");
        result.put("body", null);
        return result;
      } else if (member.getPassword().equals("")) {
        result.put("valid", SignValid.False);
        result.put("msg", "비밀번호를 입력해주세요.");
        result.put("body", null);
        return result;
      } else if (member.getEmail().equals("")) {
        result.put("valid", SignValid.False);
        result.put("msg", "이메일을 입력해주세요.");
        result.put("body", null);
        return result;
      } else {
        Member savedMember = memberRepository.save(member);
        result.put("valid", SignValid.True);
        result.put("msg", "회원가입을 성공했습니다><");
        result.put("body", savedMember);
        return result;
      }
    } catch (Exception e) {
      logger.error("signUp error:", e);
      result.put("valid", SignValid.False);
      result.put("msg", "알수없는 에러.");
      result.put("body", null);
      return result;
    }
  }

  public Map<String, Object> signIn(Member member) throws Exception {
    Map<String, Object> result = new HashMap<>();

    try {
      if (member.getLoginId().equals("")) {
        result.put("valid", SignValid.False);
        result.put("msg", "아이디를 입력해주세요.");
        result.put("body", null);
        return result;
      }
      if (member.getPassword().equals("")) {
        result.put("valid", SignValid.False);
        result.put("msg", "비밀번호를 입력해주세요.");
        result.put("body", null);
        return result;
      }

      Member findMember = memberRepository.findByLoginId(member.getLoginId()).orElse(new Member());

      if (member.getPassword().equals(findMember.getPassword())) {
        findMember.setPassword(null);
        result.put("valid", SignValid.True);
        result.put("msg", "로그인 성공");
        result.put("body", findMember);
        return result;
      }
      result.put("valid", SignValid.False);
      result.put("msg", "로그인 실패");
      result.put("body", null);
      return result;
    } catch (Exception e) {
      logger.error("signIn error:", e);
      result.put("valid", SignValid.False);
      result.put("msg", "알수없는 에러.");
      result.put("body", null);
      return result;
    }
  }

  public Map<String, Object> checkId(String loginId) throws Exception {
    Map<String, Object> result = new HashMap<>();

    boolean existsLoginId = memberRepository.existsByLoginId(loginId);

    try {
      if (existsLoginId) {
        result.put("valid", SignValid.False);
        result.put("msg", "중복된 아이디 입니다.");
        result.put("body", null);
        return result;
      } else {
        result.put("valid", SignValid.True);
        result.put("msg", "사용할 수 있는 아이디 입니다.");
        result.put("body", loginId);
        return result;
      }
    } catch (Exception e) {
      logger.error("checkId error:", e);
      result.put("valid", SignValid.False);
      result.put("msg", "알수없는 에러.");
      result.put("body", null);
      return result;
    }
  }

}