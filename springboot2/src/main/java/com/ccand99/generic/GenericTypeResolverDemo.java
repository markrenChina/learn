package com.ccand99.generic;

import org.springframework.core.GenericTypeResolver;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.springframework.core.GenericTypeResolver.resolveReturnType;
import static org.springframework.core.GenericTypeResolver.resolveReturnTypeArgument;

/**
 * {@link GenericTypeResolver} 示例
 */
public class GenericTypeResolverDemo {

    public static void main(String[] args) throws NoSuchMethodException {
//        Method method = GenericTypeResolverDemo.class.getMethod("getString");
//        //声明类 GenericTypeResolverDemo.class
//        Class<?> returnType = resolveReturnType(method, GenericTypeResolverDemo.class);
//        //常规类作为方法返回值
//        System.out.println(String.class.equals(returnType));
//        //常规类型不具备泛型参数类型
//        System.out.println(resolveReturnTypeArgument(method, GenericTypeResolverDemo.class));
        displayReturnTypeGenericInfo(GenericTypeResolverDemo.class, List.class, "getString");
        //ParameterizedType 需要具体类型
        displayReturnTypeGenericInfo(GenericTypeResolverDemo.class, List.class, "getList");
        // 泛型类型具体化后，字节码中有泛型的描述，才会获得返回
        displayReturnTypeGenericInfo(GenericTypeResolverDemo.class, List.class, "getStringList");

        //TypeVariable
        Map<TypeVariable, Type> typeVariableMap = GenericTypeResolver.getTypeVariableMap(StringList.class);
        System.out.println(typeVariableMap);
    }

    public static <E> List<E> getList() {
        return null;
    }

    public static String getString() {
        return null;
    }

    //泛型参数的具体化
    public static StringList getStringList() {
        return null;
    }

    private static void displayReturnTypeGenericInfo(Class<?> containingClass, Class<?> genericIfc, String methodName, Class... argumentType) throws NoSuchMethodException {
        Method method = containingClass.getMethod(methodName, argumentType);
        //声明类 GenericTypeResolverDemo.class
        Class<?> returnType = resolveReturnType(method, containingClass);
        //常规类作为方法返回值
        System.out.printf("GenericTypeResolver.resolveReturnType(%s,%s) = %s \n", methodName, containingClass.getSimpleName(), returnType);
        //常规类型不具备泛型参数类型

        System.out.printf("GenericTypeResolver.resolveReturnTypeArgument(%s,%s) = %s\n",
                methodName, containingClass.getSimpleName(),
                resolveReturnTypeArgument(method, genericIfc));
    }

    static class StringList extends ArrayList<String> {
        //泛型参数的具体化
    }
}
