package com.ccand99.generic;

import org.springframework.core.ResolvableType;

/**
 * {@link ResolvableType} Demo
 */
public class ResolvableTypeDemo {

    public static void main(String[] args) {
        //工厂创建
        // StringList <- ArrayList <- AbstractList <- List
        ResolvableType resolvableType = ResolvableType.forClass(GenericTypeResolverDemo.StringList.class);
        System.out.println(resolvableType.getSuperType()); //java.util.ArrayList<java.lang.String>
        System.out.println(resolvableType.getSuperType().getSuperType());//java.util.AbstractList<java.lang.String>
        //resolve 会变成具体的类型
        System.out.println(resolvableType.asCollection().resolve()); // 获取了 row Type
        //获取索引0 的具体类型 多参数时 下一个参数是上一个参数的childType
        System.out.println(resolvableType.asCollection().resolveGeneric(0));
    }
}
