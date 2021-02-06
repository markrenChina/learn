package com.ccand99.learnreactor;


//generic imports to help with simpler IDEs (ie tech.io)
import java.util.*;
import java.util.function.*;
import java.time.*;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Learn how to turn Reactive API to blocking one.
 *
 * @author Sebastien Deleuze
 */
public class Part10ReactiveToBlocking {

    //========================================================================================

    // TODO Return the user contained in that Mono
    User monoToValue(Mono<User> mono) {
        return mono.block();
    }

//========================================================================================

    // TODO Return the users contained in that Flux
    Iterable<User> fluxToValues(Flux<User> flux) {
        return flux.toIterable();
    }

}
