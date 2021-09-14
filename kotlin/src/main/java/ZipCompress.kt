
import java.io.*
import java.util.zip.*

/**
 * @author markrenChina
 * zip工具类
 */
object ZipCompress {

    /**
     * @return 成功返0 校验失败 101
     */
    @Throws(Exception::class)
    fun zipFile2PathAdler32(srcFile: File, targetPath: String, adler32: Long): Int {
        if (!File(targetPath).exists()) {
            File(targetPath).mkdir()
        }
        return zipInputStream2PathAdler32(srcFile.inputStream(), targetPath, adler32)
    }


    @Throws(Exception::class)
    fun zipInputStream2PathAdler32(ins: InputStream, file: File, adler32: Long): Int =
        zipInputStream2PathAdler32(ins, file.absolutePath, adler32)

    /**
     * @return 成功返0 校验失败 101
     */
    @Throws(Exception::class)
    fun zipInputStream2PathAdler32(ins: InputStream, targetPath: String, adler32: Long): Int {
        ins.use { fileInputStream ->
            CheckedInputStream(fileInputStream, Adler32()).use { checkedInputStream ->
                val tempFileList = ArrayList<File>()
                ZipInputStream(checkedInputStream).use { zipInputStream ->
                    BufferedInputStream(zipInputStream).use { bufferedInputStream ->
                        var ze: ZipEntry? = zipInputStream.nextEntry
                        while (ze != null) {
                            if (ze.isDirectory && !File(targetPath, ze.name).exists()) {
                                println("创建目录 ${ze.name}")
                                File(targetPath, ze.name).mkdir()
                            } else {
                                println("发现文件 ${ze.name}")
                                val targetFile = File(targetPath, "${ze.name}.temp")
                                try {
                                    FileOutputStream(targetFile).use { fileOutputStream ->
                                        tempFileList.add(targetFile)
                                        bufferedInputStream.copyTo(fileOutputStream)
                                    }
                                } catch (e: FileNotFoundException) {
                                    if (createParentDir(targetFile)) {
                                        FileOutputStream(targetFile).use { fileOutputStream ->
                                            tempFileList.add(targetFile)
                                            bufferedInputStream.copyTo(fileOutputStream)
                                        }
                                    }
                                }
                            }
                            ze = zipInputStream.nextEntry
                        }
                    }
                }
                if (checkedInputStream.checksum.value == adler32) {
                    println("校验成功")
                    //重命名
                    for (f in tempFileList) {
                        val file = File(f.absolutePath.replace(".temp", ""))
                        if (file.exists()) {
                            file.delete()
                        }
                        f.renameTo(file)
                    }
                } else {
                    println("校验失败 ${checkedInputStream.checksum.value}")
                    //删除
                    for (f in tempFileList) {
                        f.delete()
                    }
                    return 101
                }
            }
        }
        return 0
    }

    fun createParentDir(file: File): Boolean {
        return File(file.absolutePath.substringBeforeLast(File.separatorChar)).mkdirs()
    }

    @Throws(Exception::class)
    fun zipFile2PathNoVerify(srcFile: File, file: File): Int =
        zipFile2PathNoVerify(srcFile, file.absolutePath)

    @Throws(Exception::class)
    fun zipFile2PathNoVerify(srcFile: File, targetPath: String): Int {
        if (!File(targetPath).exists()) {
            File(targetPath).mkdir()
        }
        val zipFile = ZipFile(srcFile)
        val entries = zipFile.entries()
        while (entries.hasMoreElements()) {
            val entry = entries.nextElement()
            if (entry.isDirectory) {
                File(targetPath, entry.name).mkdir()
            } else {
                try {
                    File(targetPath, entry.name).outputStream().use { outputStream ->
                        zipFile.getInputStream(entry).use { inputStream ->
                            inputStream.copyTo(outputStream)
                        }
                    }
                } catch (e: FileNotFoundException) {
                    File(targetPath, entry.name).outputStream().use { outputStream ->
                        zipFile.getInputStream(entry).use { inputStream ->
                            inputStream.copyTo(outputStream)
                        }
                    }
                }
            }
        }
        return 0
    }


}