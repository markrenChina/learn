package chain;

public abstract class Leader {

    protected String name;

    protected Leader nextLeader;

    public Leader(String name) {
        this.name = name;
    }

    //设置责任类中的后一个对象
    public void setNextLeader(Leader nextLeader) {
        this.nextLeader = nextLeader;
    }

    /**
     *  处理请求的核心业务方法
     */
    public abstract void HandleRequest(LeaveRequest request);
}
