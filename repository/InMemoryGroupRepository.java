package repository;

import model.Group;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryGroupRepository implements GroupRepository{

    private final Map<String, Group> store = new HashMap<>();

    @Override
    public Optional<Group> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public void save(Group group) {
        store.put(group.getId(),group);
    }
}
