package modu.menu.place.reposiotry;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import modu.menu.place.domain.Place;
import modu.menu.placefood.domain.QPlaceFood;
import modu.menu.placevibe.domain.QPlaceVibe;
import modu.menu.vibe.domain.QVibe;
import modu.menu.vote.service.dto.VoteItemServiceResponse;
import org.springframework.stereotype.Repository;

import java.util.List;

import static modu.menu.food.domain.QFood.food;
import static modu.menu.place.domain.QPlace.place;
import static modu.menu.placefood.domain.QPlaceFood.placeFood;
import static modu.menu.placevibe.domain.QPlaceVibe.placeVibe;
import static modu.menu.vibe.domain.QVibe.vibe;

@Repository
public class PlaceQueryRepositoryImpl implements PlaceQueryRepository {

    private final JPAQueryFactory query;

    public PlaceQueryRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public List<VoteItemServiceResponse> findVoteItemList(Place content) {
        List<VoteItemServiceResponse> result = query.select(Projections.constructor(
                        VoteItemServiceResponse.class,
                        place.id,
                        place.name,
                        food.type,
                        place.imageUrl,
                        vibe.type,
                        place.address
                ))
                .from(place)
                .leftJoin(placeFood.place, place)
                .leftJoin(placeFood.food, food)
                .leftJoin(placeVibe.place, placeFood.place)
                .leftJoin(vibe, placeVibe.vibe)
                .where(place.id.eq(content.getId()))
                .fetch();
        return result;
    }
}
