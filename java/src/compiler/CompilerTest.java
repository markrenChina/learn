package compiler;

import javax.tools.*;
import java.io.File;
import java.util.List;

public class CompilerTest {

    public static void main(String[] args) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        String absolutePath = new File("src/compiler").getAbsolutePath();
        /*int result = compiler.run(null,null,null,
                absolutePath + File.separatorChar +"CompilerTest.java");*/
        File file = new File(absolutePath + File.separatorChar + "CompilerTest.java");
        //编码磁盘文件
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(System.out::println, null, null);
        Iterable<File> files = List.of(file);
        Iterable<JavaFileObject> sources = (Iterable<JavaFileObject>) fileManager.getJavaFileObjectsFromFiles(files);

        //异常处理
        //捕获编译错误，需要传入一个回调DiagnosticListener,错误消息时一个Diagnostic对象，DiagnosticCollector类可以收集诊断信息
        DiagnosticCollector<JavaFileObject> collector = new DiagnosticCollector<>();

        //classes 注解处理 options javac 参数
        JavaCompiler.CompilationTask task = compiler.getTask(
                null,
                fileManager,
                collector,
                null,
                null,
                sources
        );
        collector.getDiagnostics().forEach(System.out::println);
        System.out.println(task.call());
    }
}
