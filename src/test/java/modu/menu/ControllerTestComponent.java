package modu.menu;

import com.fasterxml.jackson.databind.ObjectMapper;
import modu.menu.user.api.UserController;
import modu.menu.user.repository.UserRepository;
import modu.menu.user.service.UserService;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@WebMvcTest(value = {
        UserController.class
})
public abstract class ControllerTestComponent {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected UserService userService;

    @MockBean
    protected UserRepository userRepository;
}
