package memento;


/**
 *  负责人类
 *  管理
 */
public class CareTaker {

    //可以引入栈或者list，或者引入序列化，持久化
    private EmpMemento memento;

    public EmpMemento getMemento() {
        return memento;
    }

    public void setMemento(EmpMemento memento) {
        this.memento = memento;
    }
}
