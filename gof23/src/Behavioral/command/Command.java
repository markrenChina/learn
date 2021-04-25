package command;

public interface Command {
    //实际业务可以设计不同的返回值
    void execute();
}

class ConcreteCommand implements  Command {

    private final Receiver receiver; //执行者

    public ConcreteCommand(Receiver receiver) {
        this.receiver = receiver;
    }


    @Override
    public void execute() {
        System.out.println("命令执行之前处理");
        receiver.action();
        System.out.println("命令执行之后处理");
    }
}
