package ramzanzan.hraper.api.dto;

import lombok.Data;

@Data
public class RequestDTO {
    private ItemPointerDTO pointer;
    private ExcerptDescriptorDTO descriptor;
//    private long offset;
//    private long limit;
    private Long packSize;
}
