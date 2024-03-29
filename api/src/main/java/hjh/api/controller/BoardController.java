package hjh.api.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hjh.api.domain.dto.board.BoardInfo;
import hjh.api.domain.dto.board.BoardWriteForm;
import hjh.api.domain.dto.board.BoardUpdateForm;
import hjh.api.domain.dto.common.MessageBox;
import hjh.api.domain.type.MessageBoxValid;
import hjh.api.service.BoardService;
import lombok.RequiredArgsConstructor;

// 필수 인수로 생성자를 생성합니다. 
// 필수 인수란 'final' 필드 및 @NonNull과 같은 제약 조건이 있는 필드입니다.
@RequiredArgsConstructor
@RestController
public class BoardController {

  private final BoardService boardService;

  /** 게시판 리스트 */
  @GetMapping("/board/list")
  public MessageBox<List<BoardInfo>> boardList(@RequestParam Integer page) {
    return boardService.getBoard(page);
  }

  /** 게시글 상세페이지 */
  @GetMapping("/board/list/{boardId}")
  public BoardInfo boardListDetail(@PathVariable("boardId") Long boardId) {
    return boardService.getBoardDetail(boardId);
  }

  /** 게시글 조회 */
  @GetMapping("/board/select/{boardId}")
  public BoardInfo boardSelect(@PathVariable("boardId") Long boardId) {
    return boardService.select(boardId);
  }

  /** 게시글 작성 */
  @PostMapping("/board/write")
  public ResponseEntity<MessageBox<BoardInfo>> boardWrite(HttpServletRequest request,
      @RequestBody @Validated BoardWriteForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors())
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageBox.failed(bindingResult));

    MessageBox<BoardInfo> result = boardService.write(form, request);
    MessageBoxValid valid = (MessageBoxValid) result.getValid();
    if (valid == MessageBoxValid.FALSE)
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);

    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  /** 게시글 삭제 */
  @DeleteMapping("/board/delete/{boardId}")
  public ResponseEntity<MessageBox<Boolean>> boardDelete(@PathVariable("boardId") Long boardId,
      HttpServletRequest request) {
    MessageBox<Boolean> result = boardService.delete(boardId, request);
    MessageBoxValid valid = (MessageBoxValid) result.getValid();
    if (valid == MessageBoxValid.FALSE)
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);

    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  /** 게시글 수정 */
  @PutMapping("/board/update")
  public ResponseEntity<MessageBox<BoardInfo>> boardUpdate(HttpServletRequest request,
      @RequestBody @Validated BoardUpdateForm form, BindingResult bindingResult) {
    if (bindingResult.hasErrors())
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageBox.failed(bindingResult));

    MessageBox<BoardInfo> result = boardService.update(form, request);
    MessageBoxValid valid = (MessageBoxValid) result.getValid();
    if (valid == MessageBoxValid.FALSE)
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);

    return ResponseEntity.status(HttpStatus.OK).body(result);
  }
}