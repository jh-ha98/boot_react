package hjh.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hjh.api.domain.dto.common.MessageBox;
import hjh.api.domain.dto.member.MemberInfo;
import hjh.api.domain.dto.member.MemberSignInForm;
import hjh.api.domain.dto.member.MemberSignUpForm;
import hjh.api.domain.type.MessageBoxValid;
import hjh.api.service.MemberService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController // Controller+ResponseBody
public class MemberController {

  private final MemberService memberService;

  /** 회원 가입 */
  @PostMapping("/member/sign-up")
  public ResponseEntity<MessageBox<MemberInfo>> signUp(@RequestBody @Validated MemberSignUpForm form,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors())
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageBox.failed(bindingResult));

    MessageBox<MemberInfo> result = memberService.signUp(form);
    MessageBoxValid valid = (MessageBoxValid) result.getValid();
    if (valid == MessageBoxValid.FALSE)
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);

    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  /** 회원 인증 */
  @PostMapping("/member/sign-in")
  public ResponseEntity<MessageBox<MemberInfo>> signIn(@RequestBody MemberSignInForm form, HttpServletRequest request)
      throws Exception {
    MessageBox<MemberInfo> result = memberService.signIn(form, request);
    MessageBoxValid valid = (MessageBoxValid) result.getValid();
    if (valid == MessageBoxValid.FALSE)
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);

    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  /** 아이디 중복 확인 */
  @GetMapping("/member/check-id")
  public ResponseEntity<MessageBox<String>> checkId(@RequestParam String loginId) throws Exception {
    MessageBox<String> result = memberService.checkId(loginId);
    MessageBoxValid valid = (MessageBoxValid) result.getValid();
    if (valid == MessageBoxValid.FALSE)
      return ResponseEntity.status(HttpStatus.CONFLICT).body(result);

    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  /** 회원 정보 */
  @GetMapping("/member/info")
  public ResponseEntity<MessageBox<MemberInfo>> memberInfo(HttpServletRequest request) throws Exception {
    MessageBox<MemberInfo> result = memberService.memberInfo(request);
    MessageBoxValid valid = (MessageBoxValid) result.getValid();
    if (valid == MessageBoxValid.FALSE)
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);

    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  /** 로그아웃 */
  @DeleteMapping("/member/sign-out")
  public ResponseEntity<MessageBox<Boolean>> signOut(HttpServletRequest request) throws Exception {
    MessageBox<Boolean> result = memberService.signOut(request);
    MessageBoxValid valid = (MessageBoxValid) result.getValid();
    if (valid == MessageBoxValid.FALSE)
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);

    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  // @PatchMapping("/mamber/update-info")
  // public ResponseEntity<MessageBox> updateMember(@RequestBody Member member)
  // throws Exception {
  // MessageBox result = memberService.updateInfo(loginId, member);
  // Valid valid = (Valid) result.getValid();
  // if (valid == Valid.False)
  // return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);

  // return ResponseEntity.status(HttpStatus.OK).body(result);
  // }
}