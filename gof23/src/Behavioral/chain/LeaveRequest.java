package chain;

/**
 * 封装请加条的基本信息
 */
public class LeaveRequest {

    private String empName;
    private int leaveDay;
    private String reason;

    public LeaveRequest(String empName, int leaveDay, String reason) {
        this.empName = empName;
        this.leaveDay = leaveDay;
        this.reason = reason;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public int getLeaveDay() {
        return leaveDay;
    }

    public void setLeaveDay(int leaveDay) {
        this.leaveDay = leaveDay;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    //为了测试是打印方便
    @Override
    public String toString() {
        return "请假条" +
                " 员工=" + empName +
                " 天数=" + leaveDay +
                " 原因=" + reason ;
    }
}

