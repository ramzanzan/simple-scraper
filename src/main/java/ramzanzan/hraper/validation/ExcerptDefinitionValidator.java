package ramzanzan.hraper.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ramzanzan.hraper.model.ExcerptDefinition;

@Component
public class ExcerptDefinitionValidator implements Validator {

    @Autowired private CssSelectorValidator selectorValidator;

    @Override
    public boolean supports(Class<?> clazz) {
        return ExcerptDefinition.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        var ed = (ExcerptDefinition)target;
        ValidationUtils.rejectIfEmpty(errors,"name","","name can't pe empty");
        errors.pushNestedPath("selector");
        ValidationUtils.invokeValidator(selectorValidator,ed.getSelector(),errors);
        errors.popNestedPath();
        if(ed.getLimit()<1) errors.rejectValue("limit","","limit must be > 1");
        if(ed.getType()== ExcerptDefinition.Type.ATTR){
            ValidationUtils.rejectIfEmpty(errors,"attrName","","attrName can't be empty when Type = ATTR");
        }
    }
}
