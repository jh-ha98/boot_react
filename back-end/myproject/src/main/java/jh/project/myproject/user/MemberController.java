package jh.project.myproject.user;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController // Controller+ResponseBody
@RequestMapping(path = "/api")
public class MemberController {

  @Autowired
  MemberService memberService;

  @PostMapping("/member/sign-up")
  public ResponseEntity<Map<String, Object>> signUp(@RequestBody Member member) throws Exception {
    Map<String, Object> result = memberService.signUp(member);
    SignValid valid = (SignValid) result.get("valid");

    if (valid.equals(SignValid.False))
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);

    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @PostMapping("/member/sign-in")
  public ResponseEntity<Map<String, Object>> signIn(@RequestBody Member member) throws Exception {
    Map<String, Object> result = memberService.signIn(member);
    SignValid valid = (SignValid) result.get("valid");

    if (valid.equals(SignValid.False))
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);

    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @GetMapping("/member/check-id")
  public ResponseEntity<Map<String, Object>> checkId(@RequestParam String loginId) throws Exception {
    Map<String, Object> result = memberService.checkId(loginId);
    SignValid valid = (SignValid) result.get("valid");

    if (valid.equals(SignValid.False))
      return ResponseEntity.status(HttpStatus.CONFLICT).body(result);

    return ResponseEntity.status(HttpStatus.OK).body(result);
  }
}