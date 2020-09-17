package ramzanzan.hraper.web.dto;

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

    private Exception e;

    public RequestStatusDTO(Request request){
        id = request.getId();
        itemsProcessed = (long)request.getItemsProcessed();
        pagesReady = request.getPacksReady();
        pageSize = request.getPageSize();
        status = request.getStatus();
        e=request.getException();
        add(WebMvcLinkBuilder.linkTo(ScraperController.class).slash(id).withSelfRel());
        add(WebMvcLinkBuilder.linkTo(ScraperController.class).slash(id)
                .slash("excerpts?page="+1+"&size="+ pageSize).withRel(IanaLinkRelations.START));
    }
}
