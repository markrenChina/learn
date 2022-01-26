package script;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.*;
import java.io.PrintWriter;
import java.io.StringWriter;

public class EngineDemo {

    public static void main(String[] args) throws ScriptException {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        scriptEngineManager.getEngineFactories().stream()
                .map(e -> e.getEngineName() + " " + e.getMimeTypes())
                .forEach(System.out::println);

        //引入javascript引擎
        ScriptEngine js = scriptEngineManager.getEngineByName("nashorn");

        //调用js代码
        js.eval("n = 0.1");
        js.put("m", 0.2);
        Object res = js.eval("n+ m");
        System.out.println(res);

        //查询并发执行脚本是否安全
        Object param = js.getFactory().getParameter("THREADING");
        System.out.println(param);

        //对象交互
        js.put("button", new JButton());
        js.eval("button.text = 'OK'");
        JButton button = (JButton) js.get("button");
        System.out.println(button.getText());

        //js重定向
        var writer = new StringWriter();
        js.getContext().setWriter(new PrintWriter(writer, true));
    }
}
