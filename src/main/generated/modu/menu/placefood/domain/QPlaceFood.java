package modu.menu.placefood.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPlaceFood is a Querydsl query type for PlaceFood
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPlaceFood extends EntityPathBase<PlaceFood> {

    private static final long serialVersionUID = 2139067352L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPlaceFood placeFood = new QPlaceFood("placeFood");

    public final modu.menu.food.domain.QFood food;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final modu.menu.place.domain.QPlace place;

    public QPlaceFood(String variable) {
        this(PlaceFood.class, forVariable(variable), INITS);
    }

    public QPlaceFood(Path<? extends PlaceFood> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPlaceFood(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPlaceFood(PathMetadata metadata, PathInits inits) {
        this(PlaceFood.class, metadata, inits);
    }

    public QPlaceFood(Class<? extends PlaceFood> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.food = inits.isInitialized("food") ? new modu.menu.food.domain.QFood(forProperty("food")) : null;
        this.place = inits.isInitialized("place") ? new modu.menu.place.domain.QPlace(forProperty("place")) : null;
    }

}

