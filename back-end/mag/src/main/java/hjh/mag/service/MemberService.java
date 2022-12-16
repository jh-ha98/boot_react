package hjh.mag.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import hjh.mag.domain.Member;
import hjh.mag.domain.MessageBox;
import hjh.mag.domain.Valid;
import hjh.mag.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

  private final String SESSION_KEY = "member";

  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;

  public MessageBox signUp(Member member) throws Exception {
    try {
      if (member.getLoginId().equals("")) {
        return new MessageBox(Valid.False, "아이디를 입력해주세요.", null);
      }
      if (member.getPassword().equals("")) {
        return new MessageBox(Valid.False, "비밀번호를 입력해주세요.", null);
      }
      if (member.getEmail().equals("")) {
        return new MessageBox(Valid.False, "이메일을 입력해주세요.", null);
      }

      MessageBox checkResult = checkMember(member);
      if (checkResult.getValid().equals(Valid.False)) {
        return (MessageBox) checkResult;
      }

      member.setPassword(passwordEncoder.encode(member.getPassword()));
      Member savedMember = memberRepository.save(member);

      return new MessageBox(Valid.True, "회원가입을 성공했습니다 ><", savedMember);

    } catch (Exception e) {
      log.error("signUp error:", e);

      return new MessageBox(Valid.False, "알수없는 에러.", null);

    }
  }

  public MessageBox signIn(Member member, HttpServletRequest request) throws Exception {
    try {
      if (member.getLoginId().equals("")) {
        return new MessageBox(Valid.False, "아이디를 입력해주세요.", null);
      }
      if (member.getPassword().equals("")) {
        return new MessageBox(Valid.False, "비밀번호를 입력해주세요.", null);
      }

      Member findMember = memberRepository.findByLoginId(member.getLoginId()).orElse(new Member());

      if (passwordEncoder.matches(member.getPassword(), findMember.getPassword())) {
        findMember.setPassword(null);

        // return new MessageBox(Valid.True, "로그인 성공!", findMember);

        // 세션에 로그인정보(Member 객체) 등록
        HttpSession session = request.getSession();
        session.setAttribute(SESSION_KEY, findMember);
        return new MessageBox(Valid.True, "로그인 성공!", findMember);
      }
      return new MessageBox(Valid.False, "로그인 실패", null);

    } catch (Exception e) {
      log.error("signIn error:", e);

      return new MessageBox(Valid.False, "알수없는 에러.", null);
    }
  }

  public MessageBox checkId(String loginId) throws Exception {

    try {
      Member member = new Member();
      member.setLoginId(loginId);

      MessageBox checkResult = checkMember(member);

      if (checkResult.getValid().equals(Valid.False))
        return checkResult;

      return new MessageBox(Valid.True, "사용할 수 있는 아이디 입니다.", loginId);

    } catch (Exception e) {
      log.error("checkId error:", e);
      return new MessageBox(Valid.False, "알수없는 에러.", null);
    }
  }

  private MessageBox checkMember(Member member) {
    if (member.getLoginId().equals("")) {
      return new MessageBox(Valid.False, "아이디를 입력해주세요.", null);
    }

    List<Member> findMembers = memberRepository.findByLoginIdOrEmail(member.getLoginId(), member.getEmail());

    // for (int i = 0; i < findMembers.size(); i++) {
    // Member findMember = findMembers.get(i);
    for (Member findMember : findMembers) {
      if (member.getLoginId().equals(findMember.getLoginId())) {
        return new MessageBox(Valid.False, "이미 존재하는 아이디 입니다.", null);
      }

      if (member.getEmail().equals(findMember.getEmail())) {
        return new MessageBox(Valid.False, "이미 존재하는 이메일 입니다.", null);
      }
    }

    return new MessageBox(Valid.True, "인증 성공");

  }

  public MessageBox signOut(HttpServletRequest request) throws Exception {

    try {
      HttpSession session = request.getSession();
      session.invalidate();

      return new MessageBox(Valid.True, "로그아웃 되엇습니다.", null);
    } catch (Exception e) {
      return new MessageBox(Valid.False, "로그아웃 실패", null);
    }

  }

  public Member getSessionMember(HttpServletRequest request) throws Exception {
    HttpSession session = request.getSession(false);
    if (session == null)
      return null;

    return (Member) session.getAttribute(SESSION_KEY);
  }

  public MessageBox memberInfo(HttpServletRequest request) throws Exception {

    Member sessionMember = getSessionMember(request);
    if (sessionMember == null) {
      return new MessageBox(Valid.False, "로그인 되어있지 않습니다.", null);
    }

    return new MessageBox(Valid.True, "로그인 성공", sessionMember);
  }
}