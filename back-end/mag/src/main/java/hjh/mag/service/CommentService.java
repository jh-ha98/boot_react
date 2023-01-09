package hjh.mag.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import hjh.mag.domain.Board;
import hjh.mag.domain.Comment;
import hjh.mag.domain.CommentInfo;
import hjh.mag.domain.Member;
import hjh.mag.domain.MessageBox;
import hjh.mag.domain.Valid;
import hjh.mag.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j // log 출력
@RequiredArgsConstructor
@Service
public class CommentService {

  private final CommentRepository commentRepository;
  private final MemberService memberService;

  public MessageBox commentSave(String commentText, Long boardId, HttpServletRequest request) throws Exception {
    Member sessionMember = memberService.getSessionMember(request);

    if (sessionMember == null)
      return new MessageBox(Valid.False, "댓글은 인증된 사용자만 작성할 수 있습니다.");

    try {
      if (commentText.equals("")) {
        return new MessageBox(Valid.False, "댓글을 입력해 주세요!");
      }

      Board board = new Board();
      board.setId(boardId);

      Comment comment = new Comment();
      comment.setBoard(board);
      comment.setComment(commentText);
      comment.setMember(sessionMember);

      Comment savedComment = commentRepository.save(comment);

      return new MessageBox(Valid.True, "댓글이 작성 되었습니다.", CommentInfo.generate(savedComment));

    } catch (Exception e) {
      log.error("commentSave error", e);

      return new MessageBox(Valid.False, "알수없는 에러.");
    }
  }
}
