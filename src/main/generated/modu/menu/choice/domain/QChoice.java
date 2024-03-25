package modu.menu.choice.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChoice is a Querydsl query type for Choice
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChoice extends EntityPathBase<Choice> {

    private static final long serialVersionUID = -1167218350L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChoice choice = new QChoice("choice");

    public final modu.menu.QBaseTime _super = new modu.menu.QBaseTime(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final modu.menu.user.domain.QUser user;

    public final modu.menu.voteItem.domain.QVoteItem voteItem;

    public QChoice(String variable) {
        this(Choice.class, forVariable(variable), INITS);
    }

    public QChoice(Path<? extends Choice> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChoice(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChoice(PathMetadata metadata, PathInits inits) {
        this(Choice.class, metadata, inits);
    }

    public QChoice(Class<? extends Choice> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new modu.menu.user.domain.QUser(forProperty("user")) : null;
        this.voteItem = inits.isInitialized("voteItem") ? new modu.menu.voteItem.domain.QVoteItem(forProperty("voteItem"), inits.get("voteItem")) : null;
    }

}

