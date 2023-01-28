package hjh.mag;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;

import hjh.mag.domain.dto.common.MessageBox;
import hjh.mag.domain.entity.Board;
import hjh.mag.domain.entity.Member;
import hjh.mag.repository.BoardRepository;
import hjh.mag.service.BoardService;

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
    MessageBox box = boardService.delete(123L, request);

    System.out.println(box.getMsg());

    assertEquals("게시글은 인증된 사용자만 삭제할 수 있습니다.", box.getMsg());
  }

  @Test
  @DisplayName("boardService delete method test 허용되지 않은 사용자")
  public void differentMember() throws Exception {

    Member fakeMember = new Member(); // 새로운 사용자를 만들고
    fakeMember.setLoginId("loginId"); // 가짜 로그인 아이디 생성

    Board fakeBoard = new Board(); // 새로운 게시판을 만들고
    fakeBoard.setTitle("타이틀"); // 가짜 게시판을 작성
    fakeBoard.setContent("콘텐츠");
    fakeBoard.setMember(fakeMember);
    boardRepository.save(fakeBoard); // 가짜로 작성한 게시글 저장

    Board findBoard = boardRepository.findById(fakeBoard.getId()).orElseThrow(); // 게시글을 작성한 아이디와 비교

    HttpServletRequest request = new MockHttpServletRequest();
    Member sessionMember = new Member();
    sessionMember.setLoginId("로그인아이디");

    request.getSession().setAttribute("member", sessionMember); // 세션에서 로그인한 멤버

    MessageBox box = boardService.delete(findBoard.getId(), request); //boardId로 게시글 삭제

    System.out.println(box.getMsg());

    assertEquals("해당 게시글을 삭제 할 수 없습니다.", box.getMsg());
  }

  @Test
  @DisplayName("boardService delete method test 성공했을 때")
  public void sameMember() throws Exception {

    Member fakeMember = new Member();
    fakeMember.setLoginId("로그인아이디");

    Board fakeBoard = new Board();
    fakeBoard.setTitle("타이틀");
    fakeBoard.setContent("콘텐츠");
    fakeBoard.setMember(fakeMember);
    boardRepository.save(fakeBoard);

    Board findBoard = boardRepository.findById(fakeBoard.getId()).orElseThrow();

    HttpServletRequest request = new MockHttpServletRequest();
    Member sessionMember = new Member();
    sessionMember.setLoginId("로그인아이디");

    request.getSession().setAttribute("member", sessionMember);

    MessageBox box = boardService.delete(findBoard.getId(), request);

    System.out.println(box.getMsg());

    assertEquals("게시글이 삭제 되었습니다.", box.getMsg());
  }

}
