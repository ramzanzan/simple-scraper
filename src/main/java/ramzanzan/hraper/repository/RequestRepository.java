package ramzanzan.hraper.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.CrudRepositoryExtensionsKt;
import org.springframework.stereotype.Repository;
import ramzanzan.hraper.model.Request;

import java.util.*;

@Repository
public class RequestRepository implements CrudRepository<Request, UUID>{

    private final Map<UUID,Request> storage = new HashMap<>(1024);

    @Override
    public <S extends Request> S save(S entity) {
       if(entity.getId()==null) entity.setId(UUID.randomUUID());
       storage.put(entity.getId(),entity);
       return entity;
    }

    @Override
    public <S extends Request> Iterable<S> saveAll(Iterable<S> entities) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Request> findById(UUID uuid) {
        return Optional.ofNullable(storage.get(uuid));
    }

    @Override
    public boolean existsById(UUID uuid) {
        return storage.containsKey(uuid);
    }

    @Override
    public Iterable<Request> findAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterable<Request> findAllById(Iterable<UUID> uuids) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long count() {
        return storage.size();
    }

    @Override
    public void deleteById(UUID uuid) {
        storage.remove(uuid);
    }

    @Override
    public void delete(Request entity) {
        storage.remove(entity.getId());
    }

    @Override
    public void deleteAll(Iterable<? extends Request> entities) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException();
    }
}
