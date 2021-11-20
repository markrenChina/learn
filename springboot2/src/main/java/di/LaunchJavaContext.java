package di;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 引导启动类
 */
public class LaunchJavaContext {

    private static final User DUMMY_USER = new User("dummy");

    public static Logger logger = Logger.getLogger(LaunchJavaContext.class);

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringContext.class);
        BusinessService service = context.getBean(BusinessService.class);
        //System.out.println(service.calculateSum(DUMMY_USER));
        logger.debug(service.calculateSum(DUMMY_USER));
    }
}
