package chain;

public class Director extends Leader{

    public Director(String name) {
        super(name);
    }

    @Override
    public void HandleRequest(LeaveRequest request) {
        if (request.getLeaveDay() < 3){
            System.out.println(request);
            System.out.println("主任"+name+"审批");
        }else {
            if (this.nextLeader !=null){
                this.nextLeader.HandleRequest(request);
            }
        }
    }

}
