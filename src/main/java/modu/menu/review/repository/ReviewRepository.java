package modu.menu.review.repository;

import modu.menu.review.domain.Review;
import modu.menu.vote.domain.VoteStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("""
            select count(r)
            from Review r
            join User u
            join Choice c
            join VoteItem vi
            join Vote v
            join Place p
            where u.id = :userId and v.voteStatus = :voteStatus and p.id in :placeIds
            """)
    int countByPlaceIdsUserIdAndVoteStatus(@Param("placeIds") List<Long> placeIds, @Param("userId") Long userId, @Param("voteStatus") VoteStatus voteStatus);
}
