package modu.menu.agreementhistory.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAgreementHistory is a Querydsl query type for AgreementHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAgreementHistory extends EntityPathBase<AgreementHistory> {

    private static final long serialVersionUID = -1487517884L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAgreementHistory agreementHistory = new QAgreementHistory("agreementHistory");

    public final modu.menu.QBaseTime _super = new modu.menu.QBaseTime(this);

    public final modu.menu.agreement.domain.QAgreement agreement;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final modu.menu.user.domain.QUser user;

    public QAgreementHistory(String variable) {
        this(AgreementHistory.class, forVariable(variable), INITS);
    }

    public QAgreementHistory(Path<? extends AgreementHistory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAgreementHistory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAgreementHistory(PathMetadata metadata, PathInits inits) {
        this(AgreementHistory.class, metadata, inits);
    }

    public QAgreementHistory(Class<? extends AgreementHistory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.agreement = inits.isInitialized("agreement") ? new modu.menu.agreement.domain.QAgreement(forProperty("agreement")) : null;
        this.user = inits.isInitialized("user") ? new modu.menu.user.domain.QUser(forProperty("user")) : null;
    }

}

