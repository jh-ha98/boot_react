package hjh.mag.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import hjh.mag.domain.Member;
import hjh.mag.domain.MessageBox;
import hjh.mag.domain.Valid;
import hjh.mag.service.MemberService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController // Controller+ResponseBody
@SessionAttributes("member")
@RequestMapping("/api")
public class MemberController {

  private final MemberService memberService;

  @PostMapping("/member/sign-up")
  public ResponseEntity<MessageBox> signUp(@RequestBody Member member) throws Exception {
    MessageBox result = memberService.signUp(member);
    Valid valid = (Valid) result.getValid();
    if (valid == Valid.False)
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);

    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @PostMapping("/member/sign-in")
  public ResponseEntity<MessageBox> signIn(@RequestBody Member member, HttpServletRequest request) throws Exception {
    MessageBox result = memberService.signIn(member, request);
    Valid valid = (Valid) result.getValid();
    if (valid == Valid.False)
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);

    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @GetMapping("/member/check-id")
  public ResponseEntity<MessageBox> checkId(@RequestParam String loginId) throws Exception {
    MessageBox result = memberService.checkId(loginId);
    Valid valid = (Valid) result.getValid();

    if (valid == Valid.False)
      return ResponseEntity.status(HttpStatus.CONFLICT).body(result);

    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @GetMapping("/member/info")
  public ResponseEntity<MessageBox> memberInfo(HttpServletRequest request) throws Exception {
    MessageBox result = memberService.memberInfo(request);
    Valid valid = (Valid) result.getValid();

    if (valid == Valid.False)
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);

    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @DeleteMapping("/member/sign-out")
  public ResponseEntity<MessageBox> signOut(HttpServletRequest request) throws Exception {
    MessageBox result = memberService.signOut(request);
    Valid valid = (Valid) result.getValid();

    if (valid == Valid.False)
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);

    return ResponseEntity.status(HttpStatus.OK).body(result);
  }
}