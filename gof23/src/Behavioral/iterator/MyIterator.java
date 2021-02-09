package Behavioral.iterator;

/**
 * Demo for iterator
 */
public interface MyIterator {

    void first();
    void next();
    boolean hasNext();

    boolean isFirst();
    boolean isLast();

    //获取游标指定对象
    Object getCurrentObj();
}
