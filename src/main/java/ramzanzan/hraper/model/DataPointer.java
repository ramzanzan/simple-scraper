package ramzanzan.hraper.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.*;

@Data

public class DataPointer {

    public static final String PAGE_PARAMETER = "{page}";

    private String pageUrlTemplate = "";
    private long from;
    private long to;
    private String itemUrlSelector = "";    //todo
    private long offset = 0L;
    private long limit = Long.MAX_VALUE;

    @JsonIgnore
    public boolean isLimited(){
        return true;
    }

    public Iterable<String> getURLs(){
        return () -> new Iterator<>() {
            long current = from;
            @Override
            public boolean hasNext() {
                return current<=to;
            }
            @Override
            public String next() {
                if(current>to) throw new NoSuchElementException();
                return pageUrlTemplate.replace(PAGE_PARAMETER,String.valueOf(current++));
            }
        };
    }

}
