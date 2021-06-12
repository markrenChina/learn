package com.ccand99.apt_compiler

import com.ccand99.apt_annotations.BindView
import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.*
import java.io.IOException
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements

@AutoService(Processor::class)
class AptProcessor : AbstractProcessor() {

    private var mFiler: Filer? = null

    private var mElementUtils: Elements? = null

    override fun init(processingEnv: ProcessingEnvironment?) {
        super.init(processingEnv)
        mFiler = processingEnv?.filer
        mElementUtils = processingEnv?.elementUtils
    }

    //指定处理的版本
    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latestSupported()
    }

    //给到需要处理的注解
    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        val types: LinkedHashSet<String> = LinkedHashSet()
        getSupportedAnnotations().forEach { clazz: Class<out Annotation> ->
            types.add(clazz.canonicalName)
        }

        return types
    }

    private fun getSupportedAnnotations(): Set<Class<out Annotation>> {
        val annotations: LinkedHashSet<Class<out Annotation>> = LinkedHashSet()
        // 需要解析的自定义注解
        annotations.add(BindView::class.java)
        return annotations
    }

    /**
    KotlinPoet 官方helloWorld示例：

    val greeterClass = ClassName("", "Greeter")
    val file = FileSpec.builder("", "HelloWorld")
    .addType(TypeSpec.classBuilder("Greeter")
    .primaryConstructor(FunSpec.constructorBuilder()
    .addParameter("name", String::class).build())
    .addProperty(PropertySpec.builder("name", String::class)
    .initializer("name").build())
    .addFunction(FunSpec.builder("greet")
    .addStatement("println(%P)", "Hello, \$name").build())
    .build())
    .addFunction(FunSpec.builder("main")
    .addParameter("args", String::class, VARARG)
    .addStatement("%T(args[0]).greet()", greeterClass).build())
    .build()

    file.writeTo(System.out)

    ——————————————————————————————————
    class Greeter(val name: String) {
    fun greet() {println("""Hello, $name""")}}

    fun main(vararg args: String) {Greeter(args[0]).greet()}
     */

    override fun process(
        p0: MutableSet<out TypeElement>?,
        roundEnvironment: RoundEnvironment?
    ): Boolean {
        //解析属性 activity ->list<Element>
        val elementMap = LinkedHashMap<Element, ArrayList<Element>>()

        // 有注解就会进来
        roundEnvironment?.getElementsAnnotatedWith(BindView::class.java)?.forEach { element ->
            //注解 属性 名称
            println("element.simpleName ------------> ${element.simpleName}")
            //注解 所在类 名称
            println("element.enclosingElement.simpleName ------------> ${element.enclosingElement.simpleName}")
            //按照 类 整理 属性
            val enclosingElement = element.enclosingElement
            var viewBindElements = elementMap[enclosingElement]
            if (viewBindElements == null) {
                viewBindElements = ArrayList()
                elementMap[enclosingElement] = viewBindElements
            }
            viewBindElements.add(element)
        }

        // 生成代码
        elementMap.entries.forEach {

            val clazz = it.key
            val viewBindElements = it.value
            println("clazz------------> ${clazz.simpleName}")
            // 继承接口名称获取
            val interfaceClassName = ClassName("com.ccand99.apt", "Unbinder")
            //动态获取包名
            val packageName = mElementUtils?.getPackageOf(clazz)?.qualifiedName?.toString()
                ?: throw RuntimeException("无法获取包名")
            val activityStr = clazz.simpleName.toString()
            val activityKtClass = ClassName(packageName, activityStr)
            val callSuperClassName = ClassName("androidx.annotation", "CallSuper")
            val findByIdUtilsClass = ClassName("com.ccand99.apt", "Utils")
            // 类 属性
            val property = PropertySpec.builder("target", activityKtClass.copy(nullable = true))
                .initializer("target")
                .mutable()//var 不加val
                .addModifiers(KModifier.PRIVATE)
            //构造构造函数
            val constructorMethodBuilder = FunSpec.constructorBuilder()
                .addParameter("target", activityKtClass.copy(nullable = true))//java 不需要传类型 可空
            viewBindElements.forEach { element ->
                val resId = element.getAnnotation(BindView::class.java).value
                //constructorMethodBuilder.addComment("target.${element.simpleName} = \$T.findViewById(source,$resId)",findByIdUtilsClass)
                constructorMethodBuilder.addStatement(
                    "target?.${element.simpleName} = %T.findViewById(target,$resId)",
                    findByIdUtilsClass
                )
            }
            // 构造类
            // public final 类kotlin为KModifier
            val clazzBuilder = TypeSpec.classBuilder("${clazz.simpleName}ViewBinding")
                .addModifiers(KModifier.FINAL, KModifier.PUBLIC)
                //构造函数
                .primaryConstructor(constructorMethodBuilder.build())
                .addProperty(property.build())
                .addSuperinterface(interfaceClassName)

            //生成类方法
            val unbindMethodBuilder = FunSpec.builder("unbind")
                .addAnnotation(callSuperClassName)
                .addModifiers(KModifier.OVERRIDE)
                .addStatement("this.target = target")
                .addStatement("if (target == null) throw IllegalStateException(\"Binding already cleares.\")")
                .addStatement("target = null")

            viewBindElements.forEach { element ->
                unbindMethodBuilder.addStatement("target?.${element.simpleName} = null")
            }


            //clazzBuilder.addFunction(constructorMethodBuilder.build())
            clazzBuilder.addFunction(unbindMethodBuilder.build())

            //生成类文件
            val classFile = FileSpec.builder(packageName, "${clazz.simpleName}ViewBinding")
                .addType(clazzBuilder.build())
                .addComment("测试 自动生成")
                .build()

            //classFile.writeTo(System.out)


            //输出的文件映射
            try {
                mFiler?.let { filer -> classFile.writeTo(filer) }
            } catch (e: IOException) {
                println(e.message)
            }

        }

        return false
    }
}