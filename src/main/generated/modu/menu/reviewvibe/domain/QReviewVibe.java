package modu.menu.reviewvibe.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReviewVibe is a Querydsl query type for ReviewVibe
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReviewVibe extends EntityPathBase<ReviewVibe> {

    private static final long serialVersionUID = -1742933620L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReviewVibe reviewVibe = new QReviewVibe("reviewVibe");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final modu.menu.review.domain.QReview review;

    public final modu.menu.vibe.domain.QVibe vibe;

    public QReviewVibe(String variable) {
        this(ReviewVibe.class, forVariable(variable), INITS);
    }

    public QReviewVibe(Path<? extends ReviewVibe> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReviewVibe(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReviewVibe(PathMetadata metadata, PathInits inits) {
        this(ReviewVibe.class, metadata, inits);
    }

    public QReviewVibe(Class<? extends ReviewVibe> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.review = inits.isInitialized("review") ? new modu.menu.review.domain.QReview(forProperty("review"), inits.get("review")) : null;
        this.vibe = inits.isInitialized("vibe") ? new modu.menu.vibe.domain.QVibe(forProperty("vibe")) : null;
    }

}

