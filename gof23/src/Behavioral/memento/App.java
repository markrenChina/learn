package memento;

public class App {

    public static void main(String[] args) {
        CareTaker taker = new CareTaker();
        Emp emp = new Emp("markrenChina",18,100);
        System.out.println("第一次创建对象" + emp);
        //备份
        taker .setMemento(emp.memento());
        emp.setAge(16);
        emp.setSalary(200);
        System.out.println("修改后" + emp);

        //恢复
        emp.recovery(taker.getMemento());
        System.out.println("第三次打印" + emp);

    }
}
