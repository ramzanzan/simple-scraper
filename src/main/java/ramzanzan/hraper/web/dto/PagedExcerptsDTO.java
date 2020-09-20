package ramzanzan.hraper.web.dto;

import lombok.Getter;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.util.Assert;
import ramzanzan.hraper.model.Excerpts;
import ramzanzan.hraper.model.Request;
import ramzanzan.hraper.web.contoller.ScraperController;

@Getter
public class PagedExcerptsDTO extends CollectionModel<Excerpts> {

    private final int size;
    private final int totalPagesCurrently;
    private final int totalElementsCurrently;
    private final boolean isLast;   //todo make json "isLast"

    protected PagedExcerptsDTO(Iterable<Excerpts> content, int size, boolean isLast,
                               int pages, int elements, Link... links){
        super(content,links);
        this.size=size;
        this.isLast=isLast;
        this.totalPagesCurrently=pages;
        totalElementsCurrently=elements;
    }

    public static PagedExcerptsDTO of(Request request, int page, int size){
        Assert.state(page>0,"page must be > 0");
        Assert.state(size>0,"size must be > 0");
        var processed = request.getItemsProcessed();
        var from = (page-1) * size;
        Assert.state(processed<from+1,"such page doesn't exist");
        var to = Math.min(processed, from + size);
        var sublist = request.getExcerpts().subList(from, to);
        var self = WebMvcLinkBuilder.linkTo(ScraperController.class)
                .slash(request.getId()).slash("excerpts?page="+page+"&size="+size).withSelfRel();
        var isLast = processed <= to
                && (request.getStatus() == Request.Status.COMPLETED || request.getStatus() == Request.Status.COMPLETED_WITH_ERRS);
        var pageModel = new PagedExcerptsDTO(sublist,sublist.size(), isLast,
                (int)Math.ceil(((double)processed)/size), processed, self);
        if(page>1)
            pageModel.add(WebMvcLinkBuilder.linkTo(ScraperController.class)
                    .slash(request.getId()).slash("excerpts?page="+(page-1)+"&size="+size).withRel(IanaLinkRelations.PREV));
        if(processed > to)
            pageModel.add(WebMvcLinkBuilder.linkTo(ScraperController.class)
                    .slash(request.getId()).slash("excerpts?page="+(page+1)+"&size="+size).withRel(IanaLinkRelations.NEXT));
        return pageModel;
    }
}
