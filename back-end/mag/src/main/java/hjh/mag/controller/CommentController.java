package hjh.mag.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hjh.mag.domain.dto.comment.CommentUpdateForm;
import hjh.mag.domain.dto.common.MessageBox;
import hjh.mag.domain.type.MessageBoxValid;
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

  /* 댓글 작성 */
  @PostMapping("/comment/write")
  // 어노테이션 없이 String 등의 Request와 관련 없는 타입의 파라미터가 선언되었을 경우는 @RequestParam(required=false, value={변수명})와 같음
  // 주의점: @RequestParam은 url파라미터로 넘길때만 사용, post와 put에서는 사용하지 말자!!
  public ResponseEntity<MessageBox> commentWrite(String comment, Long boardId, HttpServletRequest request)
      throws Exception {
    MessageBox result = commentService.commentWrite(comment, boardId, request);
    MessageBoxValid valid = (MessageBoxValid) result.getValid();
    if (valid == MessageBoxValid.FALSE)
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);

    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  /* 댓글 삭제 */
  @DeleteMapping("/comment/delete/{commentId}")
  public ResponseEntity<MessageBox> commentDelete(@PathVariable("commentId") Long commentId, HttpServletRequest request)
      throws Exception {
    MessageBox result = commentService.commentDelete(commentId, request);
    MessageBoxValid valid = (MessageBoxValid) result.getValid();
    if (valid == MessageBoxValid.FALSE)
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);

    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  /* 댓글 수정 */
  @PutMapping("/comment/update/{commentId}")
  public ResponseEntity<MessageBox> commentUpdate(@PathVariable("commentId") Long commentId,
      @RequestBody CommentUpdateForm form, HttpServletRequest request) throws Exception {
    MessageBox result = commentService.commentUpdate(commentId, form.getComment(), request);
    MessageBoxValid valid = (MessageBoxValid) result.getValid();
    if (valid == MessageBoxValid.FALSE)
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);

    return ResponseEntity.status(HttpStatus.OK).body(result);
  }
}
