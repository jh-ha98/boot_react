package hjh.mag.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hjh.mag.domain.MessageBox;
import hjh.mag.domain.Valid;
import hjh.mag.service.CommentService;
import lombok.RequiredArgsConstructor;

// 필수 인수로 생성자를 생성합니다. 
// 필수 인수란 'final' 필드 및 @NonNull과 같은 제약 조건이 있는 필드입니다.
// @Autowired 어노테이션 없이 의존성 주입
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CommentController {

  private final CommentService commentService;

  @PostMapping("/comment/write")
  /** 어노테이션 없이 String 등의 Request와 관련 없는 타입의 파라미터가 선언되었을 경우는 @RequestParam(required=false, value={변수명})와 같음 */
  public ResponseEntity<MessageBox> commentSave(String comment, Long boardId, HttpServletRequest request)
      throws Exception {
    MessageBox result = commentService.commentSave(comment, boardId, request);
    Valid valid = (Valid) result.getValid();
    if (valid == Valid.False)
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);

    return ResponseEntity.status(HttpStatus.OK).body(result);
  }
}
