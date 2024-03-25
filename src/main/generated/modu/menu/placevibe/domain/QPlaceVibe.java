package modu.menu.placevibe.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPlaceVibe is a Querydsl query type for PlaceVibe
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPlaceVibe extends EntityPathBase<PlaceVibe> {

    private static final long serialVersionUID = 775290584L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPlaceVibe placeVibe = new QPlaceVibe("placeVibe");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final modu.menu.place.domain.QPlace place;

    public final modu.menu.vibe.domain.QVibe vibe;

    public QPlaceVibe(String variable) {
        this(PlaceVibe.class, forVariable(variable), INITS);
    }

    public QPlaceVibe(Path<? extends PlaceVibe> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPlaceVibe(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPlaceVibe(PathMetadata metadata, PathInits inits) {
        this(PlaceVibe.class, metadata, inits);
    }

    public QPlaceVibe(Class<? extends PlaceVibe> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.place = inits.isInitialized("place") ? new modu.menu.place.domain.QPlace(forProperty("place")) : null;
        this.vibe = inits.isInitialized("vibe") ? new modu.menu.vibe.domain.QVibe(forProperty("vibe")) : null;
    }

}

