package ramzanzan.hraper.api.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.EntityModel;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
public class RequestStatusDTO extends EntityModel<RequestStatusDTO> {
    private UUID id;
    private Status status;
    private Long itemsProcessed;
    private Long packsReady;
    private Long packSize;

    public enum Status{
        PROCESSING
    }
}
