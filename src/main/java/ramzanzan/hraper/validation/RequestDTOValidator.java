package ramzanzan.hraper.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ramzanzan.hraper.model.DataPointer;
import ramzanzan.hraper.web.dto.RequestDTO;

@Component
public class RequestDTOValidator implements Validator {

    @Autowired private ExcerptDefinitionValidator excDefValidator;
    @Autowired private DataPointerValidator pointerValidator;

    @Override
    public boolean supports(Class<?> clazz) {
        return RequestDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        var req = (RequestDTO)target;
        if(req.getPageSize()<1) errors.rejectValue("pageSize","","PageSize must be > 1");

        errors.pushNestedPath("pointer");
        ValidationUtils.invokeValidator(pointerValidator,req.getPointer(),errors);
        errors.popNestedPath();

        var def = req.getExcerptDefinitions();
        if(def.isEmpty()) errors.rejectValue("excerptDefinitions","", "Definitions can't be empty");
        for(int i=0;i<def.size();i++){
            errors.pushNestedPath("excerptDefinitions["+i+"]"); //todo
            ValidationUtils.invokeValidator(excDefValidator,def.get(i),errors);
            errors.popNestedPath();
        }
    }
}
