package hjh.mag.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hjh.mag.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {

}
