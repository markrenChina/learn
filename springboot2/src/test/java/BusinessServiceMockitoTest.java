import di.BusinessService;
import di.BusinessServiceImpl;
import di.DataService;
import di.Data;
import di.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class BusinessServiceMockitoTest {
  private static final User DUMMY_USER = new User("dummy");

  @Mock
  private DataService dataService;

  @InjectMocks
  private BusinessService service = new BusinessServiceImpl();

  @Test
  public void testCalculateSum() {
    BDDMockito.given(dataService.retrieveData(Matchers.any(User.class)))
    .willReturn(Arrays.asList(new Data(10), new Data(15), new Data(25)));

    long sum = service.calculateSum(DUMMY_USER);
    assertEquals(10 + 15 + 25, sum);
  }
}
