package hjh.mag.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import hjh.mag.domain.dto.common.MessageBox;
import hjh.mag.domain.dto.member.MemberInfo;
import hjh.mag.domain.dto.member.MemberSignInForm;
import hjh.mag.domain.dto.member.MemberSignUpForm;
import hjh.mag.domain.entity.Member;
import hjh.mag.domain.type.MemberRoll;
import hjh.mag.domain.type.MessageBoxValid;
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

  public MessageBox signUp(MemberSignUpForm form) throws Exception {
    try {
      if (form.getLoginId().equals("")) {
        return new MessageBox(MessageBoxValid.FALSE, "아이디를 입력해주세요.");
      }
      if (form.getPassword().equals("")) {
        return new MessageBox(MessageBoxValid.FALSE, "비밀번호를 입력해주세요.");
      }
      if (form.getEmail().equals("")) {
        return new MessageBox(MessageBoxValid.FALSE, "이메일을 입력해주세요.");
      }

      MessageBox checkResult = checkMember(form.getLoginId(), form.getEmail());
      if (checkResult.getValid().equals(MessageBoxValid.FALSE)) {
        return (MessageBox) checkResult;
      }

      String encodedPassword = passwordEncoder.encode(form.getPassword());
      Member newMember = new Member(form.getLoginId(), encodedPassword, form.getEmail(), MemberRoll.USER);
      Member savedMember = memberRepository.save(newMember);
      MemberInfo memberInfo = new MemberInfo(savedMember);

      return new MessageBox(MessageBoxValid.TRUE, "회원가입을 성공했습니다 ><", memberInfo);

    } catch (Exception e) {
      log.error("signUp error:", e);

      return new MessageBox(MessageBoxValid.FALSE, "알수없는 에러.");

    }
  }

  public MessageBox signIn(MemberSignInForm form, HttpServletRequest request) throws Exception {
    try {
      if (form.getLoginId().equals("")) {
        return new MessageBox(MessageBoxValid.FALSE, "아이디를 입력해주세요.");
      }
      if (form.getPassword().equals("")) {
        return new MessageBox(MessageBoxValid.FALSE, "비밀번호를 입력해주세요.");
      }

      Member findMember = memberRepository.findByLoginId(form.getLoginId()).orElse(null);

      if (findMember == null)
        return new MessageBox(MessageBoxValid.FALSE, "존재하지 않은 회원입니다.");

      if (!passwordEncoder.matches(form.getPassword(), findMember.getPassword()))
        return new MessageBox(MessageBoxValid.FALSE, "로그인 실패");

      MemberInfo memberInfo = new MemberInfo(findMember);

      // 세션에 로그인정보(Member 객체) 등록
      HttpSession session = request.getSession();
      session.setAttribute(SESSION_KEY, memberInfo);
      return new MessageBox(MessageBoxValid.TRUE, "로그인 성공!", memberInfo);
    } catch (Exception e) {
      log.error("signIn error:", e);

      return new MessageBox(MessageBoxValid.FALSE, "알수없는 에러.");
    }
  }

  public MessageBox checkId(String loginId) throws Exception {

    try {
      MessageBox checkResult = checkMember(loginId, "");

      if (checkResult.getValid().equals(MessageBoxValid.FALSE))
        return checkResult;

      return new MessageBox(MessageBoxValid.TRUE, "사용할 수 있는 아이디 입니다.", loginId);

    } catch (Exception e) {
      log.error("checkId error:", e);
      return new MessageBox(MessageBoxValid.FALSE, "알수없는 에러.");
    }
  }

  private MessageBox checkMember(String loginId, String email) {
    if (loginId.equals("")) {
      return new MessageBox(MessageBoxValid.FALSE, "아이디를 입력해주세요.");
    }

    List<Member> findMembers = memberRepository.findByLoginIdOrEmail(loginId, email);

    // for (int i = 0; i < findMembers.size(); i++) {
    // Member findMember = findMembers.get(i);
    for (Member findMember : findMembers) {
      if (loginId.equals(findMember.getLoginId())) {
        return new MessageBox(MessageBoxValid.FALSE, "이미 존재하는 아이디 입니다.");
      }

      if (email.equals(findMember.getEmail())) {
        return new MessageBox(MessageBoxValid.FALSE, "이미 존재하는 이메일 입니다.");
      }
    }

    return new MessageBox(MessageBoxValid.TRUE, "인증 성공");

  }

  public MessageBox signOut(HttpServletRequest request) throws Exception {

    try {
      HttpSession session = request.getSession();
      session.invalidate();

      return new MessageBox(MessageBoxValid.TRUE, "로그아웃 되엇습니다.");
    } catch (Exception e) {
      return new MessageBox(MessageBoxValid.FALSE, "로그아웃 실패");
    }

  }

  public MemberInfo getSessionMember(HttpServletRequest request) throws Exception {
    HttpSession session = request.getSession(false);
    if (session == null)
      return null;

    return (MemberInfo) session.getAttribute(SESSION_KEY);
  }

  public MessageBox memberInfo(HttpServletRequest request) throws Exception {

    MemberInfo sessionMember = getSessionMember(request);
    if (sessionMember == null) {
      return new MessageBox(MessageBoxValid.FALSE, "로그인 되어있지 않습니다.");
    }

    return new MessageBox(MessageBoxValid.TRUE, "로그인 성공", sessionMember);
  }

  public void updateMember(Member member, HttpServletRequest request) throws Exception {
    // Member sessionMember = getSessionMember(request);
    // Long memberId = sessionMember.getId();

    // Member findMember = memberRepository.findById(memberId).get();
    // findMember.setEmail(member.getEmail());
    // findMember.setPassword(passwordEncoder.encode(member.getPassword()));

    // memberRepository.save(findMember);

    // 원본 데이터 ->db
    // 원본데이터 가져올때 식별자로 구분
    // 로그인한 아이디 식별자 원본 데이터 조회

  }

}