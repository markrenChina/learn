package di;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * application上下文
 */
@Configuration
@ComponentScan(basePackages = {"di"})
public class SpringContext {

}
