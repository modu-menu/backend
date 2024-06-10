package modu.menu.choice.repository;

import modu.menu.choice.domain.Choice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChoiceRepository extends JpaRepository<Choice, Long> {

    @Query("""
            select count(*)
            from Choice c
            where c.voteItem.id = :voteItemId
            """)
    int countByVoteItemId(@Param("voteItemId") Long id);

    @Query("""
            select c
            from Choice c
            where c.user.id = :userId and c.voteItem.id in :voteItemIds
            """)
    Optional<Choice> findByUserIdAndVoteItemIds(@Param("userId") Long userId, @Param("voteItemIds") List<Long> voteItemIds);
}
