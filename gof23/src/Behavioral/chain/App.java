package chain;

public class App {

    public static void main(String[] args) {
        Leader a = new Director("张三");
        Leader b = new Manager("李四");
        Leader c = new GeneraManager("王五");

        a.setNextLeader(b);
        b.setNextLeader(c);

        LeaveRequest request = new LeaveRequest("ABC",1,"XXXX");
        a.HandleRequest(request);
    }
}
