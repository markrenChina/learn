package com.ccand99.awesome.pub2sub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@RestController
public class TemperatureController {

    private final Logger log =LoggerFactory.getLogger(this.getClass());

    //CopyOnWriteArraySet 能让我们在修改时进行迭代操作
    private final Set<SseEmitter> clients = new CopyOnWriteArraySet<>();

    @RequestMapping(value = "temperature-stream", method = RequestMethod.GET)
    public SseEmitter events(HttpServletRequest request) {
        SseEmitter emitter = new SseEmitter();
        clients.add(emitter);

        //在错误或断开链接时从客户端删除发射器
        emitter.onTimeout(() -> clients.remove(emitter));
        emitter.onCompletion(() -> clients.remove(emitter));
        return emitter;
    }

    //@Async 将方法标记为异步执行的候选方法，它将在手动配置的线程池中被调用
    @Async
    //从Spring接受事件，依据是参数
    @EventListener
    public void handleMessage(Temperature temperature) {
        List<SseEmitter> deadEmitters = new ArrayList<>();
        clients.forEach(emitter ->
        {
            try {
                emitter.send(temperature, MediaType.APPLICATION_JSON);
            } catch (IOException e) {
                log.error(e.getMessage());
                deadEmitters.add(emitter);
            }
        });
        clients.removeAll(deadEmitters);
    }
}
