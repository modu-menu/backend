package modu.menu.vote.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVote is a Querydsl query type for Vote
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVote extends EntityPathBase<Vote> {

    private static final long serialVersionUID = -618789980L;

    public static final QVote vote = new QVote("vote");

    public final modu.menu.QBaseTime _super = new modu.menu.QBaseTime(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<modu.menu.participant.domain.Participant, modu.menu.participant.domain.QParticipant> participants = this.<modu.menu.participant.domain.Participant, modu.menu.participant.domain.QParticipant>createList("participants", modu.menu.participant.domain.Participant.class, modu.menu.participant.domain.QParticipant.class, PathInits.DIRECT2);

    public final ListPath<modu.menu.review.domain.Review, modu.menu.review.domain.QReview> reviews = this.<modu.menu.review.domain.Review, modu.menu.review.domain.QReview>createList("reviews", modu.menu.review.domain.Review.class, modu.menu.review.domain.QReview.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final ListPath<modu.menu.voteItem.domain.VoteItem, modu.menu.voteItem.domain.QVoteItem> voteItems = this.<modu.menu.voteItem.domain.VoteItem, modu.menu.voteItem.domain.QVoteItem>createList("voteItems", modu.menu.voteItem.domain.VoteItem.class, modu.menu.voteItem.domain.QVoteItem.class, PathInits.DIRECT2);

    public final EnumPath<VoteStatus> voteStatus = createEnum("voteStatus", VoteStatus.class);

    public QVote(String variable) {
        super(Vote.class, forVariable(variable));
    }

    public QVote(Path<? extends Vote> path) {
        super(path.getType(), path.getMetadata());
    }

    public QVote(PathMetadata metadata) {
        super(Vote.class, metadata);
    }

}

