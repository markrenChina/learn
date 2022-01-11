package com.ccand99.generic;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;

public class GenericAPIDemo {

    public static void main(String[] args) {
        //原生类型 primitive types : int long float
        Class intClass = int.class;

        //数组类型 array types : int[],Object[]
        Class objectArrayClass = Object[].class;

        //原始类型 raw types : java.lang.String
        Class rawClass = String.class;

        Class genericClass = ArrayList.class;
        //带参数的泛型类型 Parameterized Type
        ParameterizedType parameterizedType = (ParameterizedType) ArrayList.class.getGenericSuperclass();
        System.out.println(genericClass);
        System.out.println(parameterizedType);
        // ParameterizedType Raw Type = java.util.AbstractList
        System.out.println(parameterizedType.getRawType());
        //泛型类型变量 Type Variable
        Type[] typeVariables = parameterizedType.getActualTypeArguments();
        //Arrays.stream(typeVariables).forEach(System.out::println);
        Arrays.stream(typeVariables)
                .map(TypeVariable.class::cast) // Type -> TypeVariable
                .forEach(System.out::println);
    }
}
