package com.ccand99.tacos.h2db;

import com.ccand99.tacos.model.Taco;

public interface TacoRepository {
    
    Taco save(Taco design);
}
