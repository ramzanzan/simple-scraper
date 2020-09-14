package ramzanzan.hraper.api.dto;

import lombok.Data;
import java.util.List;

@Data
public class ExcerptDescriptorDTO {
    private List<ExcerptAttributeDescriptorDTO> attributes;
}
