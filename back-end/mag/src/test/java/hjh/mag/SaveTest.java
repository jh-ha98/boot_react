package hjh.mag;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import hjh.mag.domain.Board;
import hjh.mag.repository.BoardRepository;

@SpringBootTest
@Transactional
public class SaveTest {

  @Autowired
  private BoardRepository boardRepository;

  @DisplayName("spring jpa insert 테스트")
  @Test
  public void insertBoard() {
    Board board = new Board();
    board.setTitle("1234");
    board.setContent("1234");
    board.setMember(null);
    board.setId(null);

    Board savedBoard = boardRepository.save(board);

    // 클래스 동등 비교
    assertInstanceOf(Board.class, savedBoard);
  }

}
