package ramzanzan.hraper.service;

import ramzanzan.hraper.model.Excerpts;
import ramzanzan.hraper.model.Request;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ScraperTask implements Runnable {
    private final Request request;

    public ScraperTask(Request request) {
        this.request = request;
    }

    protected Document getDocument(String url){
        try {
            //todo to props
            return Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.102 Safari/537.36")
                    .timeout(30000)
                    .get();
        }catch (IOException e){
            request.setException(e);
            request.setStatus(Request.Status.INTERRUPTED);
            throw new RuntimeException(e); //todo
        }
    }

    private List<String> getItemURLs(String pageUrl){
        Document doc = getDocument(pageUrl);
        var elements = doc.select(request.getPointer().getItemUrlSelector());
        return elements.stream().map(e->e.absUrl("href")).filter(s->s.length()>0).collect(Collectors.toList());
    }

    private Excerpts getExcerpts(String itemUrl){
        Document doc = getDocument(itemUrl);
        var excerpts = new Excerpts();
        for(var def : request.getDefinitions()){
            Elements elements = doc.select(def.getSelector());
            for(int i=0; i<elements.size() && i<def.getLimit(); i++){
                var e = elements.get(i);
                String excerpt = switch (def.getType()) {
                    case OWN_TEXT -> e.ownText();
                    case TEXT -> e.text();
                    case HTML -> e.html();
                    case OWN_HTML -> {
                        var clone = e.shallowClone();
                        clone.text(e.ownText());
                        yield clone.html();
                    }
                    case ATTR -> e.attr(def.getAttrName());
                };
                excerpts.add(def.getName(), excerpt, def.isSingular());
            }
        }
        if (request.isWithOrigin()) excerpts.setOrigin(itemUrl);
        return excerpts;
    }

    @Override
    public void run() {
        request.setStatus(Request.Status.PROCESSING);
        if (request.isForSingleItem()){
            request.getExcerpts().add(getExcerpts(request.getPointer().getUrl()));
        }else {
            var pointer = request.getPointer();
            long remainingOffset = pointer.getOffset();
            long remainingLimit = pointer.getLimit();
            OUTER:
            for(long i=pointer.getFrom(); i<=pointer.getTo(); i++){
                var urls = getItemURLs(pointer.getUrl().replace(Request.PAGE_PARAMETER,String.valueOf(i)));
                if(remainingOffset>0){
                   if (remainingOffset >= urls.size()) {
                       remainingOffset -= urls.size();
                       continue;
                   }else {
                       urls = urls.subList((int)remainingOffset,urls.size());
                       remainingOffset = 0;
                   }
                }
                for(var url : urls){
                    if(pointer.isLimited() && --remainingLimit<0) break OUTER;
                    request.getExcerpts().add(getExcerpts(url));
                }
            }
        }
        request.setStatus(Request.Status.COMPLETED);
    }
}
