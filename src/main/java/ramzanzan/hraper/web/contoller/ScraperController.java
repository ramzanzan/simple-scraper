package ramzanzan.hraper.web.contoller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ramzanzan.hraper.web.dto.RequestDTO;
import ramzanzan.hraper.web.dto.RequestStatusDTO;
import ramzanzan.hraper.model.Request;
import ramzanzan.hraper.service.ScraperService;
import ramzanzan.hraper.web.dto.PagedExcerptsDTO;

import java.util.UUID;

@RestController
@RequestMapping(
        path = "/request",
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_PROBLEM_JSON_VALUE }
    )
public class ScraperController {

    @Autowired private CrudRepository<Request,UUID> repository;
    @Autowired private ScraperService service;

    //todo validation
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public RequestStatusDTO postRequest(@RequestBody RequestDTO requestDto){
//        var errs = service.validateExcerptDefinitions(requestDto.getExcerptDefinitions());
//        service.validatePointer(requestDto.getPointer());
        var request = service.scrap(requestDto.getPointer(), requestDto.getExcerptDefinitions(),
                requestDto.getPackSize(), requestDto.isWithOrigin());
        return new RequestStatusDTO(request);
    }

    @GetMapping("{id}")
    public RequestStatusDTO getRequestStatus(@PathVariable UUID id){
        var request = repository.findById(id).orElseThrow(RuntimeException::new); //todo
        return new RequestStatusDTO(request);
    }

    @GetMapping("{id}/excerpts")
    public PagedExcerptsDTO getData(@PathVariable UUID id, @RequestParam(defaultValue = "1") int page, @RequestParam Integer size){
        var request = repository.findById(id).orElseThrow(); //todo
        if(size==null) size= request.getPageSize();
        return PagedExcerptsDTO.of(request,page,size);
    }
}
