package modu.menu.participant.repository;

import modu.menu.participant.domain.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    @Query("""
            select p
            from Participant p
            where p.user.id = :userId
            """)
    Optional<Participant> findByUserId(@Param("userId") Long userId);

    @Query("""
            select p
            from Participant p
            where p.user.id = :userId and p.vote.id = :voteId
            """)
    Optional<Participant> findByUserIdAndVoteId(@Param("userId") Long userId, @Param("voteId") Long voteId);
}
