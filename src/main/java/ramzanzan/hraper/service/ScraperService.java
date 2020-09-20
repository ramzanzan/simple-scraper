package ramzanzan.hraper.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import ramzanzan.hraper.model.DataPointer;
import ramzanzan.hraper.model.ExcerptDefinition;
import ramzanzan.hraper.model.Request;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class ScraperService {

    private final CrudRepository<Request, UUID> repository;
    private final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public ScraperService(CrudRepository<Request, UUID> repository) {
        this.repository = repository;
    }

    public Errors validatePointer(DataPointer pointer){
        return null;
    }

    public Errors validateExcerptDefinitions(Iterable<ExcerptDefinition> definitions){
        return null;
    }

    public Request scrap(DataPointer pointer, List<ExcerptDefinition> definitions, int packSize, boolean withOrigin){
        var req = new Request(pointer,definitions,packSize, withOrigin);
        req = repository.save(req);
        executor.submit(new ScraperTask(req));
        return req;
    }
}
