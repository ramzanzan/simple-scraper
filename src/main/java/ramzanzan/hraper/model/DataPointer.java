package ramzanzan.hraper.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class DataPointer {
    private String url;
    private Long from;
    private Long to;
    private String itemUrlSelector;
    private Long offset;
    private Long limit;

    public Long getLimit(){
        return limit!=null ? limit : 0L;
    }

    public Long getFrom(){
        return from!=null ? from : 0L;
    }

    public Long getTo(){
        return to!=null ? to : 0L;
    }

    public Long getOffset(){
        return offset!=null ? offset : 0L;
    }

    @JsonIgnore
    public boolean isLimited(){
        return limit!=null && limit>0;
    }

}
