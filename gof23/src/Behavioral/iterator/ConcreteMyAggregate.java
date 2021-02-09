package Behavioral.iterator;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义聚合类
 */
public class ConcreteMyAggregate {
    private List<Object> list = new ArrayList<Object>();

    public void addObject(Object obj){ list.add(obj); }

    public void removeObject(Object obj){ list.remove(obj); }

    public List<Object> getList() {
        return list;
    }

    public void setList(List<Object> list) {
        this.list = list;
    }

    public MyIterator createIterator(){
        return new ConcreteIterator();
    }

    private class ConcreteIterator implements MyIterator{

        private int cursor;

        @Override
        public void first() {
            cursor = 0;
        }

        @Override
        public void next() {
            if (hasNext()){ cursor++; }
        }

        @Override
        public boolean hasNext() {
            //差别最后一位返回值不一样
            //return !isLast();
            return  cursor < list.size();
        }

        @Override
        public boolean isFirst() {
            return cursor == 0;
        }

        @Override
        public boolean isLast() {
            return cursor == list.size() - 1;
        }

        @Override
        public Object getCurrentObj() {
            return list.get(cursor);
        }
    }
}
