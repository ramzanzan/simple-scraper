package ramzanzan.hraper.validation;

import org.jsoup.nodes.Element;
import org.jsoup.select.Selector;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CssSelectorValidator implements Validator {

    private static final Element dummyHtml = new Element("p");

    @Override
    public boolean supports(Class<?> clazz) {
        return String.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        try {
            dummyHtml.select((String)target);
        }catch (Selector.SelectorParseException e){
            errors.rejectValue("","","Malformed css selector :: "+e.getMessage());
        }
    }
}
