package com.ccand99.rxjava1.operator;

import com.ccand99.rxjava1.base.Utils;
import rx.Observable;

/**
 * zip 示例
 */
public class ZipDemo {

    private static final Observable<Integer> oneToEight = Observable.range(1,8);
    private static final Observable<String> ranks = oneToEight.map(Object::toString);
    private static final Observable<String> files = oneToEight
            .map(x -> 'a'+ x -1)
            .map(ascii -> (char)ascii.intValue())
            .map(ch -> Character.toString(ch));
    private static final Observable<String> squares = files
            .flatMap(file -> ranks.map(rank -> file + rank));

    public static void main(String[] args) {
        //打印8*8 a1 -> h8
        squares.subscribe(Utils::log);
        //打印 a1 b2 -> h8 一共8个
        var squ = files.zipWith(ranks,(f,r) -> f + r).subscribe(Utils::log);
    }
}
