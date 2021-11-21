import com.ccand99.di.BusinessService;
import com.ccand99.di.SpringContext;
import com.ccand99.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringContext.class)
public class BusinessServiceJavaContextTest {

    private static final User DUMMY_USER = new User("dummy");

    @Autowired
    private BusinessService service;

    @Test
    public void testCalculateSum() {
        long sum = service.calculateSum(DUMMY_USER);
        assertEquals(30, sum);
    }
}
