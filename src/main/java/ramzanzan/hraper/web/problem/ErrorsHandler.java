package ramzanzan.hraper.web.problem;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.zalando.problem.spring.web.advice.ProblemHandling;

@ControllerAdvice
public class ErrorsHandler implements ProblemHandling {
}
