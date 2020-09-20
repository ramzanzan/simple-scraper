package ramzanzan.hraper.model;

import java.util.LinkedList;
import java.util.List;

import lombok.Data;

import java.util.UUID;

@Data
public class Request {

    private final UUID id = UUID.randomUUID();
    private final boolean withOrigin;
    private final int pageSize;
    private final List<ExcerptDefinition> definitions;
    private final DataPointer pointer;

    private volatile Status status = Status.CREATED;
    private List<Excerpts> excerpts = new LinkedList<>(); //todo
    private volatile Exception exception;

    public Request(DataPointer pointer, List<ExcerptDefinition> definitions, int packSize, boolean withOrigin){
        this.pointer = pointer;
        this.pageSize = packSize;
        this.definitions = definitions;
        this.withOrigin = withOrigin;
    }

    public int getItemsProcessed(){
        return excerpts.size();
    }

    public int getPacksReady(){
        return getItemsProcessed()/pageSize + (getItemsProcessed()%pageSize ==0 ? 0 : 1);
    }

    @Override
    public int hashCode(){
        return id.hashCode();
    }

    public enum Status{
        CREATED,
        PROCESSING,
        COMPLETED,
        COMPLETED_WITH_ERRS
    }
}
