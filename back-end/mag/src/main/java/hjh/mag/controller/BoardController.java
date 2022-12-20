package hjh.mag.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hjh.mag.domain.Board;
import hjh.mag.domain.BoardInfo;
import hjh.mag.domain.MessageBox;
import hjh.mag.domain.Valid;
import hjh.mag.service.BoardService;
import lombok.RequiredArgsConstructor;

// 필수 인수로 생성자를 생성합니다. 
// 필수 인수란 'final' 필드 및 @NonNull과 같은 제약 조건이 있는 필드입니다.
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class BoardController {

  private final BoardService boardService;

  @GetMapping("/board/board-list")
  public List<BoardInfo> boardList() {
    return boardService.getBoard();
  }

  @GetMapping("/board/board-list/{boardId}")
  public List<BoardInfo> boardListDetail(@PathVariable("boardId") Long boardId) {
    return boardService.getBoard();
  } 

  @PostMapping("/board/write-form")
  public ResponseEntity<MessageBox> boardWrite(@RequestBody Board board, HttpServletRequest request) throws Exception {
    MessageBox result = boardService.write(board, request);
    Valid valid = (Valid) result.getValid();
    if (valid == Valid.False)
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);

    return ResponseEntity.status(HttpStatus.OK).body(result);

  }
}
