package ramzanzan.hraper.model;

import java.util.List;
import lombok.Data;
import ramzanzan.hraper.api.dto.RequestDTO;
import ramzanzan.hraper.api.dto.RequestStatusDTO;

import java.util.Collections;
import java.util.UUID;

@Data
public class Request {
    private UUID id;
    private Status status;
    private Long itemsProcessed;
    private Long packsReady;
    private Long packSize;
    private RequestDTO request;

    private List<Excerpt> excerpts;

    public Long getItemsProcessed(){
        return (long)excerpts.size();
    }

    public Long getPacksReady(){
        return getItemsProcessed()/packSize + getItemsProcessed()%packSize==0 ? 0L : 1L;
    }

    @Override
    public int hashCode(){
        return id.hashCode();
    }

    public enum Status{
        PROCESSING
    }
}
