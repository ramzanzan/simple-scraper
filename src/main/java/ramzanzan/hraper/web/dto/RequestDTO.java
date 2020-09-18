package ramzanzan.hraper.web.dto;

import lombok.Data;
import ramzanzan.hraper.model.DataPointer;
import ramzanzan.hraper.model.ExcerptDefinition;

import java.util.List;

@Data
public class RequestDTO {
    private DataPointer pointer;
    private List<ExcerptDefinition> excerptDefinitions;
    private Integer pageSize;
    private boolean withOrigin;
}
