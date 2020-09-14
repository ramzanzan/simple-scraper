package ramzanzan.hraper.model;

import lombok.Data;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class Excerpt {
    private Map<String,List<String>> attributes;
}
