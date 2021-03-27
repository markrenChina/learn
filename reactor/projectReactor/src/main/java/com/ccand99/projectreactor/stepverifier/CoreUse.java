package com.ccand99.projectreactor.stepverifier;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.springframework.security.authentication.BadCredentialsException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CoreUse {

    public static void main(String[] args) {
        expectNext();
        expectNextCount();
        rule();
        expectNextMatches();
        customAssert();
        error();
        exception();
    }

    private static void expectNext() {
        var res = StepVerifier
                .create(Flux.just("foo","bar"))
                .expectSubscription()
                .expectNext("foo")
                .expectNext("bar")
                .expectComplete()
                .verify();
        System.out.println(res);
    }

    //验证中间数量
    private static void expectNextCount() {
        var res = StepVerifier
                .create(Flux.range(0,100))
                .expectSubscription()
                .expectNext(0)
                .expectNextCount(98)
                .expectNext(99)
                .expectComplete()
                .verify();
        System.out.println(res);
    }

    //匹配规则验证
    //多线程下使用线程安全的.recordWith(ConcurrentLinkedQueue::new)
    private static void rule() {
         Publisher<Wallet> usersWallets = findAllUserWallets();
        var res = StepVerifier
                .create(usersWallets)
                .expectSubscription()
                .recordWith(ArrayList::new)
                .expectNextCount(1)
                .consumeRecordedWith(wallets -> assertThat(
                        wallets,
                        everyItem(hasProperty("owner",equalTo("admin")))
                ))
                .expectComplete()
                .verify();
        System.out.println(res);
    }
    //模拟数据库获取
    public static Publisher<Wallet> findAllUserWallets() {
        return Flux.just(Wallet.wallet(1,"admin",0));
    }

    //基于期望的
    private static void expectNextMatches() {
        var res = StepVerifier
                .create(Flux.just("alpha-foo","betta-bar"))
                .expectSubscription()
                .expectNextMatches(e -> e.startsWith("alpha"))
                .expectNextMatches(e -> e.startsWith("betta"))
                .expectComplete()
                .verify();
        System.out.println(res);
    }

    /**
     * 自定义断言：
     * .assertNext() 是 .consumeNextWith()的别名
     * 与.expectNextMatches()的区别是assertNext 可以抛出异常并且捕获AssertionError
     * 而expectNextMatches只能返回boolean
     */
    private static void customAssert() {
        var res = StepVerifier.create(findAllUserWallets())
                .expectSubscription()
                .assertNext(wallet -> assertThat(
                        wallet,
                        hasProperty("owner",equalTo("admin"))
                ))
                .expectComplete().verify();
        System.out.println(res);
    }

    //验证异常
    private static void error() {
        var res = StepVerifier
                .create(Flux.error(new RuntimeException("Error")))
                .expectError()
                .verify();
        System.out.println(res);
    }

    /**
     * 还可以使用expectErrorMatches()和consumeErrorWith的扩展，进一步进行错误检查
     */
    //异常类型验证
    private static void exception() {
        var res = StepVerifier
                .create(login("admin","wrong"))
                .expectSubscription()
                .expectError(BadCredentialsException.class)
                .verify();
        System.out.println(res);
    }

    //模拟登录方法
    private static Publisher<Boolean> login( String name, String psw) {
        if (name.equals("admin")&&psw.equals("123")){
            return Mono.just(true);
        }else {
            return Mono.error(new BadCredentialsException("error")) ;
        }
    }
}
