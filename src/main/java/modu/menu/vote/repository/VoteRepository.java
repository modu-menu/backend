package modu.menu.vote.repository;

import modu.menu.vote.domain.Vote;
import modu.menu.vote.domain.VoteStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    @Query("""
            select v
            from Vote v
            join fetch v.voteItems
            """)
    Optional<Vote> findVoteResultById(@Param("voteId") Long voteId);

    @Query("""
           select vi
           from Vote v
           join VoteItem vi
           join Choice c
           where c.user.id = :userId and v.voteStatus = :voteStatus
           """)
    List<Vote> findByUserIdAndVoteStatus(@Param("userId") Long id, @Param("voteStatus") VoteStatus voteStatus);
}
