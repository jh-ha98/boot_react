package hjh.api.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hjh.api.domain.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

  Optional<Member> findByLoginId(String loginId);

  List<Member> findByLoginIdOrEmail(String loginId, String email);

}