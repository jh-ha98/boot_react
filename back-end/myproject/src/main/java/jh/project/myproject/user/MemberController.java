package jh.project.myproject.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

@RestController // Controller+ResponseBody
@SessionAttributes("member")
@RequestMapping("/api")
public class MemberController {

  @Autowired
  MemberService memberService;

  @Autowired
  PasswordEncoder passwordEncoder;

  @PostMapping("/member/sign-up")
  public ResponseEntity<Map<String, Object>> signUp(@RequestBody Member member) throws Exception {
    Map<String, Object> result = memberService.signUp(member);
    SignValid valid = (SignValid) result.get("valid");

    if (valid.equals(SignValid.False))
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);

    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @PostMapping("/member/sign-in")
  public ResponseEntity<Map<String, Object>> signIn(@RequestBody Member member, HttpServletRequest request)
      throws Exception {
    Map<String, Object> result = memberService.signIn(member);

    HttpSession session = request.getSession();

    SignValid valid = (SignValid) result.get("valid");

    if (valid.equals(SignValid.False))
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);

    session.setAttribute("result", result);
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

  @GetMapping("/member/info")
  public Object memberInfo(HttpServletRequest request) throws Exception {
    HttpSession session = request.getSession();
    Map<String, Object> result = (Map) session.getAttribute("result");

    if (result == null) {
      result = new HashMap<String, Object>();
      result.put("valid", SignValid.False);
      result.put("msg", "로그인 되어있지 않습니다.");
      result.put("body", null);
    }

    return result;
  }

  @DeleteMapping("/member/sign-out")
  public Map<String, Object> signOut(HttpServletRequest request) {
    Map<String, Object> result = new HashMap<>();

    try {
      HttpSession session = request.getSession();
      session.invalidate();

      result.put("valid", SignValid.True);
      result.put("msg", "로그아웃 되엇습니다");
      result.put("body", null);
    } catch (Exception e) {
      result.put("valid", SignValid.False);
      result.put("msg", "로그아웃 실패");
      result.put("body", null);
    }

    return result;
  }

  @GetMapping("/member/password/test")
  public String getMethodName() {
    String pass1 = "1234";
    String pass2 = "12341";
    String encoded = passwordEncoder.encode(pass1);
    boolean result1 = passwordEncoder.matches(pass1, encoded);
    boolean result2 = passwordEncoder.matches(pass2, encoded);

    System.out.println(encoded);
    System.out.println(result1);
    System.out.println(result2);

    return "{ \"test\": 123 }";
  }

}