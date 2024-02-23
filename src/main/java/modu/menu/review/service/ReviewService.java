package modu.menu.review.service;

import lombok.RequiredArgsConstructor;
import modu.menu.place.reposiotry.PlaceRepository;
import modu.menu.review.api.request.CreateReviewRequest;
import modu.menu.review.api.request.VibeRequest;
import modu.menu.review.domain.Review;
import modu.menu.review.domain.ReviewStatus;
import modu.menu.review.repository.ReviewRepository;
import modu.menu.reviewvibe.domain.ReviewVibe;
import modu.menu.reviewvibe.repository.ReviewVibeRepository;
import modu.menu.user.repository.UserRepository;
import modu.menu.vibe.repository.VibeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewVibeRepository reviewVibeRepository;
    private final VibeRepository vibeRepository;

    // 리뷰 등록
    @Transactional
    public void createReview(Long userId, Long placeId, CreateReviewRequest createReviewRequest) {

        Review review = reviewRepository.save(Review.builder()
                .user(userRepository.findById(userId).get())
                .place(placeRepository.findById(placeId).get())
                .rating(createReviewRequest.getRating())
                .participants(createReviewRequest.getParticipants())
                .hasRoom(createReviewRequest.getHasRoom())
                .content(createReviewRequest.getContent())
                .status(ReviewStatus.ACTIVE)
                .build());

        if (createReviewRequest.getVibes() != null
                && !createReviewRequest.getVibes().isEmpty()) {
            for (VibeRequest vr : createReviewRequest.getVibes()) {
                reviewVibeRepository.save(
                        ReviewVibe.builder()
                                .review(review)
                                .vibe(vibeRepository.findByVibeType(vr.getType()).get())
                                .build()
                );
            }
        }
    }
}
