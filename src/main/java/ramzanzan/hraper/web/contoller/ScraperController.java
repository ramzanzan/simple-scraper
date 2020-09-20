package ramzanzan.hraper.web.contoller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ramzanzan.hraper.validation.RequestDTOValidator;
import ramzanzan.hraper.web.dto.RequestDTO;
import ramzanzan.hraper.web.dto.RequestStatusDTO;
import ramzanzan.hraper.model.Request;
import ramzanzan.hraper.service.ScraperService;
import ramzanzan.hraper.web.dto.PagedExcerptsDTO;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@RestController
@RequestMapping(
        path = "/request",
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_PROBLEM_JSON_VALUE }
    )
public class ScraperController {

    @Autowired private CrudRepository<Request,UUID> repository;
    @Autowired private ScraperService service;
    @Autowired private RequestDTOValidator requestDTOValidator;

    @InitBinder
    public void configBinder(WebDataBinder binder){
        binder.addValidators(requestDTOValidator);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public RequestStatusDTO postRequest(@RequestBody @Validated RequestDTO requestDto) {
        var request = service.scrap(requestDto.getPointer(), requestDto.getExcerptDefinitions(),
                requestDto.getPageSize(), requestDto.isWithOrigin());
        return new RequestStatusDTO(request);
    }

    @GetMapping("{id}")
    public RequestStatusDTO getRequestStatus(@PathVariable UUID id){
        var request = repository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"there isn't request with such id :: "+id));
        return new RequestStatusDTO(request);
    }

    @GetMapping("{id}/excerpts")
    public PagedExcerptsDTO getData(@PathVariable @NotNull UUID id,
                                    @RequestParam(defaultValue = "1") @Min(1) int page,
                                    @RequestParam @Min(1) Integer size){
        var request = repository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"there isn't request with such id :: "+id));
        if(size==null) size= request.getPageSize();
        var processed = request.getItemsProcessed();
        if (processed==0 || processed<(page-1)*size)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"page unavailable (now or in future too)");
        return PagedExcerptsDTO.of(request,page,size);
    }
}
