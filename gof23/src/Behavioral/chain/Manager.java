package chain;

public class Manager extends Leader{

    public Manager(String name) {
        super(name);
    }

    @Override
    public void HandleRequest(LeaveRequest request) {
        if (request.getLeaveDay() < 10){
            System.out.println(request);
            System.out.println("经理"+name+"审批");
        }else {
            if (this.nextLeader !=null){
                this.nextLeader.HandleRequest(request);
            }
        }
    }
}
