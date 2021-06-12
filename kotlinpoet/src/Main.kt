import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeSpec

class Main {
}

fun main() {
    val clazzBuilder = TypeSpec.classBuilder("ViewBinding")
        .addModifiers(KModifier.FINAL, KModifier.PUBLIC)
    val classFile = FileSpec.builder("com.ccand99.apt", "ViewBinding")
        .addType(clazzBuilder.build())
        .addComment("测试 自动生成")
        .build()
    classFile.writeTo(System.out)
}