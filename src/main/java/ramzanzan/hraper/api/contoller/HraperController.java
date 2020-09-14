package ramzanzan.hraper.api.contoller;


import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ramzanzan.hraper.api.dto.RequestDTO;
import ramzanzan.hraper.api.dto.RequestStatusDTO;
import ramzanzan.hraper.model.Excerpt;

import java.util.UUID;

@RestController
@RequestMapping(
        path = "/request",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_PROBLEM_JSON_VALUE }
    )
public class HraperController {

    @PostMapping
    public RequestStatusDTO postRequest(@RequestBody RequestDTO request){
        return null;
    }

    @GetMapping("{id}")
    public RequestStatusDTO getRequestStatus(@PathVariable UUID id){
        return null;

    }

    @GetMapping("{id}/{page}")
    public PagedModel<EntityModel<Excerpt>> getData(@PathVariable UUID id, @PathVariable Long page){
        return null;
    }
}
