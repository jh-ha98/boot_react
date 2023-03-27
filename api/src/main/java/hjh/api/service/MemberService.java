package hjh.api.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hjh.api.domain.dto.common.MessageBox;
import hjh.api.domain.dto.member.MemberInfo;
import hjh.api.domain.dto.member.MemberSignInForm;
import hjh.api.domain.dto.member.MemberSignUpForm;
import hjh.api.domain.entity.Member;
import hjh.api.domain.type.MemberRoll;
import hjh.api.domain.type.MessageBoxValid;
import hjh.api.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberService {

  private final String SESSION_KEY = "member";

  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public MessageBox<MemberInfo> signUp(MemberSignUpForm form) {
    MessageBox<MemberInfo> checkResult = checkMember(form.getLoginId(), form.getEmail());
    if (checkResult.getValid().equals(MessageBoxValid.FALSE)) {
      return checkResult;
    }

    String encodedPassword = passwordEncoder.encode(form.getPassword());
    Member newMember = new Member(form.getLoginId(), encodedPassword, form.getEmail(), MemberRoll.USER);
    Member savedMember = memberRepository.save(newMember);
    MemberInfo memberInfo = new MemberInfo(savedMember);

    return new MessageBox<>(MessageBoxValid.TRUE, "회원가입을 성공했습니다.", memberInfo);
  }

  @Transactional
  public MessageBox<MemberInfo> signIn(MemberSignInForm form, HttpServletRequest request) {
    Member findMember = memberRepository.findByLoginId(form.getLoginId()).orElse(null);

    if (findMember == null)
      return new MessageBox<>(MessageBoxValid.FALSE, "존재하지 않은 회원입니다.");

    if (!passwordEncoder.matches(form.getPassword(), findMember.getPassword()))
      return new MessageBox<>(MessageBoxValid.FALSE, "로그인 실패");

    MemberInfo memberInfo = new MemberInfo(findMember);

    // 세션에 로그인정보(Member 객체) 등록
    HttpSession session = request.getSession();
    session.setAttribute(SESSION_KEY, memberInfo);
    return new MessageBox<>(MessageBoxValid.TRUE, "로그인 성공!", memberInfo);
  }

  /** 중복확인 */
  public MessageBox<String> checkId(String loginId) {
    MessageBox<String> checkResult = checkMember(loginId, "");

    if (checkResult.getValid().equals(MessageBoxValid.FALSE))
      return checkResult;

    return new MessageBox<>(MessageBoxValid.TRUE, "사용할 수 있는 아이디 입니다.", loginId);
  }

  private <T> MessageBox<T> checkMember(String loginId, String email) {
    List<Member> findMembers = memberRepository.findByLoginIdOrEmail(loginId, email);

    // for (int i = 0; i < findMembers.size(); i++) {
    // Member findMember = findMembers.get(i);
    for (Member findMember : findMembers) {
      if (loginId.equals(findMember.getLoginId())) {
        return new MessageBox<>(MessageBoxValid.FALSE, "이미 존재하는 아이디 입니다.");
      }

      if (email.equals(findMember.getEmail())) {
        return new MessageBox<>(MessageBoxValid.FALSE, "이미 존재하는 이메일 입니다.");
      }
    }

    return new MessageBox<>(MessageBoxValid.TRUE, "인증 성공");
  }

  public MessageBox<Boolean> signOut(HttpServletRequest request) {

    try {
      HttpSession session = request.getSession();
      session.invalidate();

      return new MessageBox<>(MessageBoxValid.TRUE, "로그아웃 되엇습니다.");
    } catch (Exception e) {
      log.error("signOut 함수 에러", e);
      return new MessageBox<>(MessageBoxValid.FALSE, "로그아웃 실패");
    }

  }

  public MemberInfo getSessionMember(HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if (session == null)
      return null;

    return (MemberInfo) session.getAttribute(SESSION_KEY);
  }

  public MessageBox<MemberInfo> memberInfo(HttpServletRequest request) {

    MemberInfo sessionMember = getSessionMember(request);
    if (sessionMember == null) {
      return new MessageBox<>(MessageBoxValid.FALSE, "로그인 되어있지 않습니다.");
    }

    return new MessageBox<>(MessageBoxValid.TRUE, "로그인 성공", sessionMember);
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