package com.ccand99.projectreactor.sampleWeb.functional.password.verification.client;

import reactor.core.publisher.Mono;

public interface PasswordVerificationService {

    Mono<Void> check(String raw, String encoded);
}
