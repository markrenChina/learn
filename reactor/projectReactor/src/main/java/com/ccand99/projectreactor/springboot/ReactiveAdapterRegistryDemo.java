package com.ccand99.projectreactor.springboot;


import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ReactiveAdapter;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.core.ReactiveTypeDescriptor;

public class ReactiveAdapterRegistryDemo {

    @Bean
    public void maybeDemo() {
        ReactiveAdapterRegistry
                //单例实例
                .getSharedInstance()
                .registerReactiveType(
                        //定义
                        ReactiveTypeDescriptor.singleOptionalValue(Maybe.class,Maybe::empty),
                        //转换方式
                        rawMaybe -> ((Maybe<?>) rawMaybe).toFlowable(),
                        //转回方式
                        publisher -> Flowable.fromPublisher(publisher).singleElement()
                );
    }

    public static void main(String[] args) {
        //获得适配器
        ReactiveAdapter maybeAdapter = ReactiveAdapterRegistry
                .getSharedInstance()
                .getAdapter(Maybe.class);
    }
}
