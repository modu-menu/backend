package modu.menu.user.service;

import lombok.RequiredArgsConstructor;
import modu.menu.core.exception.Exception500;
import modu.menu.core.response.ErrorMessage;
import modu.menu.user.domain.User;
import modu.menu.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void join(User user) {
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new Exception500(ErrorMessage.ADD_USERS_DB_ERROR);
        }
    }
}
