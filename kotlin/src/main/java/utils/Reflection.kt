package utils

//import com.ibm.jvm.dtfjview.commands.helpers.ClassOutput.printMethods

import java.io.PrintStream
import java.lang.reflect.Modifier
import java.util.*

fun main(args: Array<String>){
    //read class name from command line args or user input
    if (args.isNotEmpty()){
        printClassInfo(args[0])
    }else{
        val input = Scanner(System.`in`);
        println("Enter class name :")
        printClassInfo(input.next())
    }
}

fun printClassInfo(className: String){
    //print class name and superclass name (if !=Object)
    val clazz = Class.forName(className)
    val superClazz = clazz.superclass
     //public private
    val printStream: PrintStream = System.out
    with(printStream){
        printModifiers(clazz,printStream)
        append("Class ").append(className)
        if (superClazz != null && superClazz != Any::class.java){
            append(" extends ").append(superClazz.name).append(" {\n")
        }
        printConstructors(clazz,this)
        append("\n")
        printMethods(clazz,this)
        append("\n")
        printFields(clazz,this)
        append("}")
    }
    printStream.flush()
}


fun printModifiers(clazz: Class<*>, ps: PrintStream){
    val modifiers = Modifier.toString(clazz.modifiers)
    if (modifiers.isNotEmpty()) {
        ps.append(modifiers).append(" ")
    }
}
fun printModifiers(modifiers: Int, ps: PrintStream){
    val modifierStr = Modifier.toString(modifiers)
    if (modifierStr.isNotEmpty()) {
        ps.append(modifierStr).append(" ")
    }
}

fun printParameterTypes(classes: Array<Class<*>>, printStream: PrintStream) {
    with(printStream){
        append(" (")
        for (i in classes.indices){
            if (i > 0){
                append(", ")
            }
            append(classes[i].name)
        }
        append(");\n")
    }
}

fun printMethods(clazz: Class<*>, ps: PrintStream) {
    with(ps){
        clazz.declaredMethods.forEach { method ->
            append("\t")
            printModifiers(method.modifiers,ps)
            append(method.returnType.name).append(" ")
            append(method.name)
            printParameterTypes(method.parameterTypes,this)
        }
    }

}

fun printFields(clazz: Class<*>, ps: PrintStream) {
    clazz.fields.forEach {  field->
        ps.append("\t")
        printModifiers(field.modifiers,ps)
        ps.append(field.type.name).append(" ").append(field.name).append(";\n")
    }
}

fun printConstructors(clazz: Class<*>, ps: PrintStream) {
    clazz.declaredConstructors.forEach {  constructor ->
        with(ps){
            append("\t")
            printModifiers(constructor.modifiers,ps)
            append(constructor.name)
            printParameterTypes(constructor.parameterTypes,this)
        }
    }
}



