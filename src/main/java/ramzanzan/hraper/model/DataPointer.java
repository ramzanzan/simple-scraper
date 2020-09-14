package ramzanzan.hraper.api.dto;

import lombok.Data;

@Data
public class DataPointer {
    private String url;
    private long from;
    private long to;
    private String selector;
}
