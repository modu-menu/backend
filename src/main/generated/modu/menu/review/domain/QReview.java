package modu.menu.review.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReview is a Querydsl query type for Review
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReview extends EntityPathBase<Review> {

    private static final long serialVersionUID = 1911165056L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReview review = new QReview("review");

    public final modu.menu.QBaseTime _super = new modu.menu.QBaseTime(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final EnumPath<HasRoom> hasRoom = createEnum("hasRoom", HasRoom.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> participants = createNumber("participants", Integer.class);

    public final modu.menu.place.domain.QPlace place;

    public final NumberPath<Integer> rating = createNumber("rating", Integer.class);

    public final EnumPath<ReviewStatus> status = createEnum("status", ReviewStatus.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final modu.menu.user.domain.QUser user;

    public final modu.menu.vote.domain.QVote vote;

    public QReview(String variable) {
        this(Review.class, forVariable(variable), INITS);
    }

    public QReview(Path<? extends Review> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReview(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReview(PathMetadata metadata, PathInits inits) {
        this(Review.class, metadata, inits);
    }

    public QReview(Class<? extends Review> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.place = inits.isInitialized("place") ? new modu.menu.place.domain.QPlace(forProperty("place")) : null;
        this.user = inits.isInitialized("user") ? new modu.menu.user.domain.QUser(forProperty("user")) : null;
        this.vote = inits.isInitialized("vote") ? new modu.menu.vote.domain.QVote(forProperty("vote")) : null;
    }

}

