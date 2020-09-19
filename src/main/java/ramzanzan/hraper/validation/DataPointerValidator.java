package ramzanzan.hraper.validation;

import org.jsoup.nodes.Element;
import org.jsoup.select.Selector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import ramzanzan.hraper.model.DataPointer;

import java.net.MalformedURLException;
import java.net.URL;

@Component
@Validated
public class DataPointerValidator implements Validator {

    @Autowired private CssSelectorValidator selectorValidator;

    @Override
    public boolean supports(Class<?> clazz) {
        return DataPointer.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        var p = (DataPointer)target;

        if(!p.getPageUrlTemplate().contains(DataPointer.PAGE_PARAMETER)) {
            errors.rejectValue("pageUrlTemplate","","Page parameter doesn't found. Page parameter must be {page}");
        }else {
            try {
                var url = new URL(p.getPageUrlTemplate().replace(DataPointer.PAGE_PARAMETER,"0"));
                if(!(url.getProtocol().equals("http") || url.getProtocol().equals("https")))
                    errors.rejectValue("pageUrlTemplate","","Protocol must be http or https");
            } catch (MalformedURLException e) {
                errors.rejectValue("url","", "pageUrlTemplate isn't valid URL after {page} substitution :: "+e.getMessage());
            }
        }

        if(p.getFrom()<0) errors.rejectValue("from","","from must be > 0");
        if(p.getFrom()>p.getTo()) errors.rejectValue("to","","to must be > From");

        errors.pushNestedPath("itemUrlSelector");
        ValidationUtils.invokeValidator(selectorValidator,p.getItemUrlSelector(),errors);
        errors.popNestedPath();

        if(p.getOffset()<0) errors.rejectValue("offset","","offset must be > 0");
        if(p.getLimit()<1) errors.rejectValue("offset","","offset must be > 1");
    }
}
