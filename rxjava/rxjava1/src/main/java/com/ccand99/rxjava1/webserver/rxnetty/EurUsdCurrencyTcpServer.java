package com.ccand99.rxjava1.webserver.rxnetty;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.reactivex.netty.protocol.tcp.server.TcpServer;
import rx.Observable;

/**
 * 简单的货币服务器
 */
public class EurUsdCurrencyTcpServer {

    private static final BigDecimal RATE = new BigDecimal("1.06448");

    public static void main(String[] args) {
        TcpServer.newServer(8080)
                .<String,String>pipelineConfigurator(pipeline -> {
                    //将ByteBuf序列重新排列为行数据的序列
                    pipeline.addLast(new LineBasedFrameDecoder(1024));
                    //解码器将包含所有数据行的ByteBuf转换为String实例
                    pipeline.addLast(new StringDecoder(StandardCharsets.UTF_8));
                })
                .start(connection -> {
                    Observable<String> output = connection
                            .getInput()
                            .map(BigDecimal::new)
                            .flatMap(EurUsdCurrencyTcpServer::eurToUsd);
                    return connection.writeStringAndFlushOnEach(output);
                })
                .awaitShutdown();
    }


    static Observable<String> eurToUsd(BigDecimal eur){
        return Observable.just(eur.multiply(RATE))
                .map(amount ->"\n"+ eur + " EUR is " + amount +" USD\n")
                .delay(1, TimeUnit.SECONDS);
    }
}
