package modu.menu.oauth.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOauth is a Querydsl query type for Oauth
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOauth extends EntityPathBase<Oauth> {

    private static final long serialVersionUID = 1459530872L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOauth oauth = new QOauth("oauth");

    public final modu.menu.QBaseTime _super = new modu.menu.QBaseTime(this);

    public final StringPath accessToken = createString("accessToken");

    public final StringPath ci = createString("ci");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> oauthId = createNumber("oauthId", Long.class);

    public final EnumPath<Provider> provider = createEnum("provider", Provider.class);

    public final StringPath refreshToken = createString("refreshToken");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final modu.menu.user.domain.QUser user;

    public QOauth(String variable) {
        this(Oauth.class, forVariable(variable), INITS);
    }

    public QOauth(Path<? extends Oauth> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOauth(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOauth(PathMetadata metadata, PathInits inits) {
        this(Oauth.class, metadata, inits);
    }

    public QOauth(Class<? extends Oauth> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new modu.menu.user.domain.QUser(forProperty("user")) : null;
    }

}

