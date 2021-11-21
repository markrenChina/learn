package com.ccand99.di;
import com.ccand99.domain.User;

import java.util.List;


public interface DataService {
    List<Data> retrieveData(User user);
}
