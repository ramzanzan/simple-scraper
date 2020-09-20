package ramzanzan.hraper.model;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ExcerptDefinition {
    private String name;
    private String selector;
    private Type type;
    private String attrName;
    private Integer limit = Integer.MAX_VALUE;

    @JsonIgnore
    public boolean isSingular(){
        return limit==1;
    }

    public enum Type{
        TEXT,
        OWN_TEXT,
        HTML,
        OWN_HTML,
        ATTR
    }
}
