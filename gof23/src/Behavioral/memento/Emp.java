package memento;

/**
 * 源发器
 */
public class Emp {

    private String ename;
    private int age;
    private double salary;

    //备忘操作，并返回备忘录对象
    public EmpMemento memento(){
        return new EmpMemento(this);
    }

    //数据恢复
    public void recovery(EmpMemento empMemento){
        this.ename = empMemento.getEname();
        this.age = empMemento.getAge();
        this.salary = empMemento.getSalary();
    }

    public Emp(String ename, int age, double salary) {
        this.ename = ename;
        this.age = age;
        this.salary = salary;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    //方便测试重新toString
    @Override
    public String toString() {
        return "名字='" + ename + '\'' +
                ", 年龄=" + age +
                ", 工资=" + salary;
    }
}
