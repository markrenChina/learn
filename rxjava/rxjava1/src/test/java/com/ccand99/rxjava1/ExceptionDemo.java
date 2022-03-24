package com.ccand99.rxjava1;

import com.google.common.io.Files;
import org.junit.Test;
import rx.Observable;
import rx.observables.BlockingObservable;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.Fail.failBecauseExceptionWasNotThrown;

public class ExceptionDemo {

    @Test
    public void fileNotFoundException(){
        File file = new File("404.txt");
        BlockingObservable<String> fileContents = Observable
                .fromCallable( () -> Files.toString(file, StandardCharsets.UTF_8))
                .toBlocking();

        try {
            fileContents.single();
            failBecauseExceptionWasNotThrown(FileNotFoundException.class);
        }catch (RuntimeException exception){
            assertThat(exception).hasCauseInstanceOf(FileNotFoundException.class);
        }
    }
}
