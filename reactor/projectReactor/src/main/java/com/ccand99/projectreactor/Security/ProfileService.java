package com.ccand99.projectreactor.Security;

import reactor.core.publisher.Mono;

public interface ProfileService {

    Mono<Profile> getByUser(String name);
}
