package ru.practicum.item;

import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ItemRepositoryImpl implements ItemRepository {

    private final Map<Long, List<Item>> storage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public List<Item> findByUserId(long userId) {
        return storage.getOrDefault(userId, Collections.emptyList());
    }

    @Override
    public Item save(Item item) {
        long newId = idGenerator.getAndIncrement();
        item.setId(newId);

        storage.computeIfAbsent(item.getUserId(), id -> new ArrayList<>())
                .add(item);

        return item;
    }

    @Override
    public void deleteByUserIdAndItemId(long userId, long itemId) {
        List<Item> items = storage.get(userId);
        if (items == null) return;

        items.removeIf(item -> item.getId() == itemId);
    }
}
