package ramzanzan.hraper.web.dto;

import lombok.Data;
import org.springframework.validation.annotation.Validated;
import ramzanzan.hraper.model.DataPointer;
import ramzanzan.hraper.model.ExcerptDefinition;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Validated
public class RequestDTO {
    private DataPointer pointer;
    private List<ExcerptDefinition> excerptDefinitions;
    private Integer pageSize;
    private boolean withOrigin = true;
}
