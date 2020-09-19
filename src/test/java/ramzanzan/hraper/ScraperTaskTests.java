package ramzanzan.hraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import ramzanzan.hraper.model.DataPointer;
import ramzanzan.hraper.model.ExcerptDefinition;
import ramzanzan.hraper.model.Request;
import ramzanzan.hraper.service.ScraperTask;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

@SpringBootTest
public class ScraperTaskTests {

    @Autowired ApplicationContext ctx;
    static Request requestSingleItem;
    static Request request2;

    Request getSoloItemRequest(){
        var dp1 = new DataPointer();
        dp1.setPageUrlTemplate("src/test/resources/test_item.html");
        var exd = new ExcerptDefinition();
        exd.setName("part");
        exd.setSelector("div.ingredients-list:nth-child(8) > div:nth-child(2) > p");
        return new Request(dp1, Collections.singletonList(exd), 10, true);
    }

    @Test
    public void soloItemText(){
       var req = getSoloItemRequest();
        req.getDefinitions().get(0).setType(ExcerptDefinition.Type.TEXT);
        var task = new MockDocScraperTask(req);
        task.run();
        Assertions.assertEquals(1, req.getExcerpts().size());
        Assertions.assertEquals(5, req.getExcerpts().get(0).getExcerpts().get("part").size());
        Assertions.assertEquals("Творог 500 г", req.getExcerpts().get(0).getExcerpts().get("part").get(0));
    }

    @Test
    public void soloItemAttr(){
        var req = getSoloItemRequest();
        req.getDefinitions().get(0).setType(ExcerptDefinition.Type.ATTR);
        req.getDefinitions().get(0).setAttrName("data-ingredient-object");
        var task = new MockDocScraperTask(req);
        task.run();
        Assertions.assertEquals(1, req.getExcerpts().size());
        Assertions.assertEquals(5, req.getExcerpts().get(0).getExcerpts().get("part").size());
        Assertions.assertEquals("{\"id\": 13994, \"name\": \"Подсолнечное масло\", \"amount\": \"5 столовых ложек\"}",
                req.getExcerpts().get(0).getExcerpts().get("part").get(4));
    }

    class MockDocScraperTask extends ScraperTask {

        public MockDocScraperTask(Request request) {
            super(request);
        }

        @Override
        protected Document getDocument(String url){
            try {
                return Jsoup.parse(new File(url), "utf-8","eda.ru");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}


