package modu.menu.place.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPlace is a Querydsl query type for Place
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPlace extends EntityPathBase<Place> {

    private static final long serialVersionUID = 1449075320L;

    public static final QPlace place = new QPlace("place");

    public final modu.menu.QBaseTime _super = new modu.menu.QBaseTime(this);

    public final StringPath address = createString("address");

    public final StringPath businessHours = createString("businessHours");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    public final NumberPath<Double> latitude = createNumber("latitude", Double.class);

    public final NumberPath<Double> longitude = createNumber("longitude", Double.class);

    public final StringPath menu = createString("menu");

    public final StringPath name = createString("name");

    public final StringPath ph = createString("ph");

    public final ListPath<modu.menu.placefood.domain.PlaceFood, modu.menu.placefood.domain.QPlaceFood> placeFoods = this.<modu.menu.placefood.domain.PlaceFood, modu.menu.placefood.domain.QPlaceFood>createList("placeFoods", modu.menu.placefood.domain.PlaceFood.class, modu.menu.placefood.domain.QPlaceFood.class, PathInits.DIRECT2);

    public final ListPath<modu.menu.placevibe.domain.PlaceVibe, modu.menu.placevibe.domain.QPlaceVibe> placeVibes = this.<modu.menu.placevibe.domain.PlaceVibe, modu.menu.placevibe.domain.QPlaceVibe>createList("placeVibes", modu.menu.placevibe.domain.PlaceVibe.class, modu.menu.placevibe.domain.QPlaceVibe.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final ListPath<modu.menu.voteItem.domain.VoteItem, modu.menu.voteItem.domain.QVoteItem> voteItems = this.<modu.menu.voteItem.domain.VoteItem, modu.menu.voteItem.domain.QVoteItem>createList("voteItems", modu.menu.voteItem.domain.VoteItem.class, modu.menu.voteItem.domain.QVoteItem.class, PathInits.DIRECT2);

    public QPlace(String variable) {
        super(Place.class, forVariable(variable));
    }

    public QPlace(Path<? extends Place> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPlace(PathMetadata metadata) {
        super(Place.class, metadata);
    }

}

