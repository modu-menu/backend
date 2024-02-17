package modu.menu.vote.repository;

import modu.menu.vote.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    @Query("""
            select v
            from Vote v
            join fetch v.voteItems vi
            join fetch vi.place p
            """)
    Optional<Vote> findVoteResultById(@Param("voteId") Long voteId);
}
