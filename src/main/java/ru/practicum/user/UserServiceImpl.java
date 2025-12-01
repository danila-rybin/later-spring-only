package ru.practicum.user;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Value("${app.welcome-message}")
    private String welcomeMessage;

    @Override
    public List<User> getAllUsers() {
        log.info("Запрос на получение всех пользователей");
        log.debug("Welcome message: {}", welcomeMessage);
        return repository.findAll();
    }

    @Override
    public User saveUser(User user) {
        log.info("Сохранение нового пользователя: {}", user);
        return repository.save(user);
    }
}
