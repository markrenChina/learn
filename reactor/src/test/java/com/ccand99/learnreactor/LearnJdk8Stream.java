package com.ccand99.learnreactor;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LearnJdk8Stream {

    /**
     * 过滤集合不符合条件
     */
    @Test
    public void filter(){
        List<String> strings = Arrays.asList("avd","sdfg","asdeg","ityu");
        //并行流
        //strings.parallelStream();
        //串行流
        List<String> filter = strings.stream().filter( str->
                str.contains("d")
        ).collect(Collectors.toList());
        System.out.println(filter);
    }

    /**
     * 去重
     */
    @Test
    public void distinct(){
        List<String> strings = Arrays.asList("avd","sdfg","asdeg","ityu","avd");
        var distinct = strings.stream().distinct().collect(Collectors.toList());
        System.out.println(distinct);
        //------------对象无法去重（根据hahCode和equals方法判断）---------------------
        var users = new ArrayList<User>();
        users.add(new User("1","a","b"));
        users.add(new User("1","a","f"));
        users.add(new User("2","z","j"));
        var distinctUser = users.stream().distinct().collect(Collectors.toList());
        System.out.println(distinctUser);
    }

    /**
     * 截取 获取n个元素
     */
    @Test
    public void limit(){
        List<String> strings = Arrays.asList("avd","sdfg","asdeg","ityu","avd");
        var limited = strings.stream().limit(2).collect(Collectors.toList());
        System.out.println(limited);
    }

    /**
     * 跳过，抛弃
     */
    @Test
    public void skip(){
        List<String> strings = Arrays.asList("avd","sdfg","asdeg","ityu","avd");
        var skiped = strings.stream().skip(2).collect(Collectors.toList());
        System.out.println(skiped);
    }

    /**
     * 对流中所有元素做统一处理(返回一个新的集合)
     */
    @Test
    public void map(){
        List<String> strings = Arrays.asList("avd","sdfg","asdeg","ityu","avd");
        var mapped = strings.stream().map(str->
                str.concat("_mark")).collect(Collectors.toList());
        System.out.println(mapped);
    }

    /**
     * flatmap会在map处理的基础上把流的内容取出合成一个流来处理
     */
    @Test
    public void flatMap(){
        List<String> strings = Arrays.asList("avd","sdfg","asdeg","ityu","avd");
        var mapped = strings.stream().map(str->
                Stream.of(Arrays.toString(str.toCharArray()))).collect(Collectors.toList());
        System.out.println(mapped);
        var flatmap = strings.stream().flatMap(str->
                Stream.of(Arrays.toString(str.toCharArray()))).collect(Collectors.toList());
        System.out.println(flatmap);
    }

    /**
     * 排序
     */
    @Test
    public void sorted(){
        List<String> strings = Arrays.asList("avd","sdfg","asdeg","ityu","avd");
        List<String> collect = strings.stream().sorted().collect(Collectors.toList());
        System.out.println(collect);

    }

    //------------------终止符-----------------

    /**
     * 集合中是否有一个元素满足条件
     * 省略示例 ：
     * allMatch（全部满足）返回boolean
     * noneMatch（全部不满足）返回boolean
     */
    @Test
    public void anyMatch(){
        List<String> strings = Arrays.asList("avd","sdfg","asdeg","ityu","avd");
        boolean a = strings.stream().anyMatch(s -> s.contains("a"));
        System.out.println(a);
    }
}
