package ru.practicum.user;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryUserRepository implements UserRepository {

    private final Map<Long, User> users = new HashMap<>();
    private long idSequence = 1;

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }


}
