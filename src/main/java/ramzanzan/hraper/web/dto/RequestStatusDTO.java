package ramzanzan.hraper.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import ramzanzan.hraper.web.contoller.ScraperController;
import ramzanzan.hraper.model.Request;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
public class RequestStatusDTO extends EntityModel<RequestStatusDTO> {
    private UUID id;
    private Long itemsProcessed;
    private Integer pagesReady;
    private Integer pageSize;
    private Request.Status status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorDetails;

    public RequestStatusDTO(Request request){
        id = request.getId();
        itemsProcessed = (long)request.getItemsProcessed();
        pagesReady = request.getPagesReady();
        pageSize = request.getPageSize();
        status = request.getStatus();
        errorDetails = request.getErrorDetails();
        add(WebMvcLinkBuilder.linkTo(ScraperController.class).slash(id).withSelfRel());
        if(itemsProcessed>0)
            add(WebMvcLinkBuilder.linkTo(ScraperController.class).slash(id)
                .slash("excerpts?page="+1+"&size="+ pageSize).withRel(IanaLinkRelations.START));
    }
}
