package modu.menu.place.reposiotry;

import modu.menu.place.domain.Place;
import modu.menu.vote.service.dto.VoteItemServiceResponse;

import java.util.List;

public interface PlaceQueryRepository {
    List<VoteItemServiceResponse> findVoteItemList(Place storeId);
}
