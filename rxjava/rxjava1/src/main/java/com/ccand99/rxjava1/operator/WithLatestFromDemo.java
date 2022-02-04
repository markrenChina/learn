package com.ccand99.rxjava1.operator;

import com.ccand99.rxjava1.base.Flows;
import com.ccand99.rxjava1.base.Utils;

import java.util.concurrent.TimeUnit;

import static com.ccand99.rxjava1.base.Utils.sleep;

/**
 * 只有主导流发送的下游，主导流发送下游时，只取副的最新值。
 */
public class WithLatestFromDemo {

    public static void main(String[] args) {
        //.startWith 可以在没有元素产生时提供默认值
        var fast = Flows.flow10.map(i -> "fast " + i).take(5).startWith("null");
        var slow = Flows.flow12.map(i -> "slow " + i).take(5);
        //副流没有值产生时，主流的事件会丢失
        slow.withLatestFrom(fast,(e1, e2) -> e1 +" " +e2)
                .subscribe(Utils::log);
        sleep(3, TimeUnit.SECONDS);
    }
}
