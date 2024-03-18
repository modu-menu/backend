package modu.menu;

import com.fasterxml.jackson.databind.ObjectMapper;
import modu.menu.core.auth.jwt.JwtProvider;
import modu.menu.place.api.PlaceController;
import modu.menu.place.service.PlaceService;
import modu.menu.review.api.ReviewController;
import modu.menu.review.service.ReviewService;
import modu.menu.user.api.UserController;
import modu.menu.user.service.UserService;
import modu.menu.vote.api.VoteController;
import modu.menu.vote.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@WebMvcTest(controllers = {
        UserController.class,
        VoteController.class,
        ReviewController.class,
        PlaceController.class
})
public abstract class ControllerTestSupporter {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected JwtProvider jwtProvider;

    @MockBean
    protected UserService userService;

    @MockBean
    protected ReviewService reviewService;

    @MockBean
    protected PlaceService placeService;

    @MockBean
    protected VoteService voteService;
}
