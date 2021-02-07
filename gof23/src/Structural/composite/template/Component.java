package composite.template;

/**
 * 抽象组件
 */
public interface Component {

    void operation();
}
//叶子
interface teaf extends Component{

}
//容器
interface Composite extends Component{
     void add(Component component);
     void remove(Component component);
     Component getChild(int index);
}
