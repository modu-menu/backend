package modu.menu.voteItem.repository;

import modu.menu.voteItem.domain.VoteItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteItemRepository extends JpaRepository<VoteItem, Long> {
}
