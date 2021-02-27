package com.ccand99.projectreactor.Security;

import reactor.core.publisher.Mono;

public class DefaultProfileService implements ProfileService {

    @Override
    public Mono<Profile> getByUser(String name) {
        return Mono.empty();
    }
}
