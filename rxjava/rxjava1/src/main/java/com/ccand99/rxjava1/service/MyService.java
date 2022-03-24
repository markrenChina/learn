package com.ccand99.rxjava1.service;

import rx.Observable;

import java.time.LocalDate;

public interface MyService {

    Observable<LocalDate> externalCall();
}
