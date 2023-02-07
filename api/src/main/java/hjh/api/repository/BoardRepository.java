package hjh.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hjh.api.domain.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {

}
