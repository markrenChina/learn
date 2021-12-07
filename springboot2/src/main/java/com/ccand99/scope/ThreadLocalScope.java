package com.ccand99.scope;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.core.NamedThreadLocal;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义 线程作用域
 */
public class ThreadLocalScope implements Scope {

    public static final String SCOPE_NAME = "thread-local";
    private  final NamedThreadLocal<Map<String,Object>> threadLocal = new NamedThreadLocal<>("thread-local-scope"){
        public Map<String,Object> initialValue(){
            return new HashMap<>();
        }
    };

    private Map<String,Object> getContext(){
        return threadLocal.get();
    }

    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        //获取
        Map<String, Object> context = getContext();
        Object object = context.get(name);
        if (object == null) {
            object = objectFactory.getObject();
            context.put(name,object);
        }
        return object;
    }

    @Override
    public Object remove(String name) {
        //移除
        Map<String, Object> context = getContext();
        return context.remove(name);
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback) {
        //注册销毁回调
        //TODO
    }

    @Override
    public Object resolveContextualObject(String key) {
        Map<String, Object> context = getContext();
        return context.get(key);
    }

    @Override
    public String getConversationId() {
        Thread thread = Thread.currentThread();
        return thread.getName() + Thread.currentThread().getId();
    }
}
