package modu.menu.choice.repository;

import modu.menu.choice.domain.Choice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChoiceRepository extends JpaRepository<Choice, Long> {

    @Query("""
            select count(*)
            from Choice c
            where c.voteItem.id = :voteItemId
            """)
    int countByVoteItemId(@Param("voteItemId") Long id);
}
