package modu.menu.vibe.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QVibe is a Querydsl query type for Vibe
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVibe extends EntityPathBase<Vibe> {

    private static final long serialVersionUID = -564051908L;

    public static final QVibe vibe = new QVibe("vibe");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<VibeType> type = createEnum("type", VibeType.class);

    public QVibe(String variable) {
        super(Vibe.class, forVariable(variable));
    }

    public QVibe(Path<? extends Vibe> path) {
        super(path.getType(), path.getMetadata());
    }

    public QVibe(PathMetadata metadata) {
        super(Vibe.class, metadata);
    }

}

