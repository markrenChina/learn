package com.ccand99.learnreactor;

//generic imports to help with simpler IDEs (ie tech.io)
import java.util.*;
import java.util.function.*;
import java.time.*;
import reactor.test.StepVerifier;

import reactor.core.publisher.Flux;

import static org.assertj.core.api.Assertions.*;

/**
 * Learn how to use StepVerifier to test Mono, Flux or any other kind of Reactive Streams Publisher.
 *
 * @author Sebastien Deleuze
 * @see <a href="https://projectreactor.io/docs/test/release/api/reactor/test/StepVerifier.html">StepVerifier Javadoc</a>
 */

public class Part03StepVerifier {

    //========================================================================================

    // TODO Use StepVerifier to check that the flux parameter emits "foo" and "bar" elements then completes successfully.
    void expectFooBarComplete(Flux<String> flux) {
        StepVerifier.create(flux)
                .expectNext("foo")
                .expectNext("bar")
                .verifyComplete();
    }

//========================================================================================

    // TODO Use StepVerifier to check that the flux parameter emits "foo" and "bar" elements then a RuntimeException error.
    void expectFooBarError(Flux<String> flux) {
        StepVerifier.create(flux)
                .expectNext("foo","bar")
                .verifyError(RuntimeException.class);
    }

//========================================================================================

    // TODO Use StepVerifier to check that the flux parameter emits a User with "swhite"username
    // and another one with "jpinkman" then completes successfully.
    void expectSkylerJesseComplete(Flux<User> flux) {
        StepVerifier.create(flux)
                .expectNextMatches(user -> user.getUsername().equals("swhite"))
                .expectNextMatches(user -> user.getUsername().equals("ipinkman"))
                .expectComplete();
    }


//========================================================================================

    // TODO Expect 10 elements then complete and notice how long the test takes.
    void expect10Elements(Flux<Long> flux) {
        StepVerifier.create(flux)
                .expectNextCount(10)
                .verifyComplete();
    }

//========================================================================================

    /**
     * The next one is even worse:
     * it emits 1 element per second, completing only after having emitted 3600 of them!
     * Since we don't want our tests to run for hours, we need a way to speed that up while
     * still being able to assert the data itself (eliminating the time factor).
     * Fortunately, StepVerifier comes with a virtual time option: by using
     * StepVerifier.withVirtualTime(Supplier<Publisher>), the verifier will temporarily
     * replace default core Schedulers (the component that define the execution context in Reactor).
     * All these default Scheduler are replaced by a single instance of a VirtualTimeScheduler,
     * which has a virtual clock that can be manipulated.
     * In order for the operators to pick up that Scheduler, you should lazily build your operator
     * chain inside the lambda passed to withVirtualTime.
     * You must then advance time as part of your test scenario, by calling either thenAwait(Duration)
     * or expectNoEvent(Duration). The former simply advances the clock, while the later additionally
     * fails if any unexpected event triggers during the provided duration (note that almost all the time
     * there will at least be a "subscription" event even though the clock hasn't advanced, so you should
     * usually put a expectSubscription() after .withVirtualTime() if you're going to use expectNoEvent
     * right after).
     *
     * StepVerifier.withVirtualTime(() -> Mono.delay(Duration.ofHours(3)))
     *             .expectSubscription()
     *             .expectNoEvent(Duration.ofHours(2))
     *             .thenAwait(Duration.ofHours(1))
     *             .expectNextCount(1)
     *             .expectComplete()
     *             .verify();
     */
    // TODO Expect 3600 elements at intervals of 1 second, and verify quicker than 3600s
    // by manipulating virtual time thanks to StepVerifier#withVirtualTime, notice how long the test takes
    void expect3600Elements(Supplier<Flux<Long>> supplier) {
        StepVerifier.withVirtualTime(supplier)
                .thenAwait(Duration.ofSeconds(1))
                .expectNextCount(3600)
                .verifyComplete();
    }

    private void fail() {
        throw new AssertionError("workshop not implemented");
    }

}
