package ramzanzan.hraper.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import ramzanzan.hraper.model.DataPointer;
import ramzanzan.hraper.model.ExcerptDefinition;
import ramzanzan.hraper.model.Request;

import java.util.UUID;

@Service
public class ScraperService {

    private final CrudRepository<Request, UUID> repository;

    public ScraperService(CrudRepository<Request, UUID> repository) {
        this.repository = repository;
    }

    public Errors validatePointer(DataPointer pointer){
        return null;
    }

    public Errors validateExcerptSpec(ExcerptDefinition spec){
        return null;
    }

    public Request scrap(DataPointer pointer, ExcerptDefinition spec, Long packSize){
        var req = new Request(pointer,spec,packSize);
        req = repository.save(req);
        //todo
        return req;
    }
}
