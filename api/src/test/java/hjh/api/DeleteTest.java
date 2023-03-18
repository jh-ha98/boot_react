package hjh.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;

import hjh.api.domain.dto.common.MessageBox;
import hjh.api.domain.dto.member.MemberInfo;
import hjh.api.domain.entity.Board;
import hjh.api.domain.entity.Member;
import hjh.api.repository.BoardRepository;
import hjh.api.service.BoardService;

@SpringBootTest
@Transactional // 테스트용, 실제 DB저장 안됨
public class DeleteTest {

  @Autowired
  private BoardService boardService;

  @Autowired
  private BoardRepository boardRepository;

  @Test
  @DisplayName("boardService delete method test 인증되지 않은 사용자")
  public void noAuth() throws Exception {

    // 실제 HttpSession 을 사용할 수 없으니, MockHttpSession 을 사용하여 검증로직을 작성
    HttpServletRequest request = new MockHttpServletRequest();
    MessageBox<Boolean> box = boardService.delete(123L, request);

    System.out.println(box.getMsg());

    assertEquals("게시글은 인증된 사용자만 삭제할 수 있습니다.", box.getMsg());
  }

  @Test
  @DisplayName("boardService delete method test 허용되지 않은 사용자")
  public void differentMember() throws Exception {

    // 새로운 사용자를 만들고
    // 가짜 로그인 아이디 생성
    Member fakeMember = new Member("loginId", null, null, null);

    // 새로운 게시판을 만들고
    // 가짜 게시판을 작성
    Board fakeBoard = new Board("타이틀", "콘텐츠", fakeMember);
    boardRepository.save(fakeBoard); // 가짜로 작성한 게시글 저장

    Board findBoard = boardRepository.findById(fakeBoard.getId()).orElseThrow(); // 게시글을 작성한 아이디와 비교

    HttpServletRequest request = new MockHttpServletRequest();
    Member sessionMember = new Member("로그인아이디", null, null, null);
    MemberInfo sessionMemberInfo = new MemberInfo(sessionMember);

    request.getSession().setAttribute("member", sessionMemberInfo); // 세션에서 로그인한 멤버

    MessageBox<Boolean> box = boardService.delete(findBoard.getId(), request); // boardId로 게시글 삭제

    System.out.println(box.getMsg());

    assertEquals("해당 게시글을 삭제 할 수 없습니다.", box.getMsg());
  }

  @Test
  @DisplayName("boardService delete method test 성공했을 때")
  public void sameMember() throws Exception {

    Member fakeMember = new Member("로그인아이디", null, null, null);

    // 새로운 게시판을 만들고
    // 가짜 게시판을 작성
    Board fakeBoard = new Board("타이틀", "콘텐츠", fakeMember);
    boardRepository.save(fakeBoard);

    Board findBoard = boardRepository.findById(fakeBoard.getId()).orElseThrow();

    HttpServletRequest request = new MockHttpServletRequest();
    Member sessionMember = new Member("로그인아이디", null, null, null);
    MemberInfo sessionMemberInfo = new MemberInfo(sessionMember);

    request.getSession().setAttribute("member", sessionMemberInfo);

    MessageBox<Boolean> box = boardService.delete(findBoard.getId(), request);

    System.out.println(box.getMsg());

    assertEquals("게시글이 삭제 되었습니다.", box.getMsg());
  }

}
