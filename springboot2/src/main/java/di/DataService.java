package di;
import java.util.List;


public interface DataService {
    List<Data> retrieveData(User user);
}
