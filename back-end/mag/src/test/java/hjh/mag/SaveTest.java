package hjh.mag;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import hjh.mag.domain.entity.Board;
import hjh.mag.repository.BoardRepository;

@SpringBootTest
@Transactional
public class SaveTest {

  @Autowired
  private BoardRepository boardRepository;

  @DisplayName("spring jpa insert 테스트")
  @Test
  public void insertBoard() {
    Board board = new Board("1234", "1234", null);
    Board savedBoard = boardRepository.save(board);

    // 클래스 동등 비교
    assertInstanceOf(Board.class, savedBoard);
  }

}
