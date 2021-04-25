package command;

public class Invoke {

    //可以通过容器放置很多命令，批处理
    private final Command command;

    public Invoke(Command command) {
        this.command = command;
    }

    public void call() {
        //执行前
        System.out.println("发送之前处理");
        command.execute();
        System.out.println("发送之后处理");
    }
}
