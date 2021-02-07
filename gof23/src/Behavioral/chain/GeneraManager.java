package chain;

public class GeneraManager extends Leader{

    public GeneraManager(String name) {
        super(name);
    }

    @Override
    public void HandleRequest(LeaveRequest request) {
        if (request.getLeaveDay() < 30){
            System.out.println(request);
            System.out.println("总经理"+name+"审批");
        }else {
            System.out.println("审批不通过");
        }
    }
}
