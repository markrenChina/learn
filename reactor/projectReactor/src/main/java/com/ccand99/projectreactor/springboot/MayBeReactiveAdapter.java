package com.ccand99.projectreactor.springboot;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import org.springframework.core.ReactiveAdapter;
import org.springframework.core.ReactiveTypeDescriptor;

/**
 * Rxjava Maybe类型转换 示例
 * 假定在toPublisher前，兼容性已经经过ReactiveAdapter#getReativeType方法检查
 */
public class MayBeReactiveAdapter extends ReactiveAdapter {

    public MayBeReactiveAdapter() {
        super(
                //定义
                ReactiveTypeDescriptor.singleOptionalValue(Maybe.class,Maybe::empty),
                //转换方式
                rawMaybe -> ((Maybe<?>) rawMaybe).toFlowable(),
                //转回方式
                publisher -> Flowable.fromPublisher(publisher).singleElement()
        );
    }
}
