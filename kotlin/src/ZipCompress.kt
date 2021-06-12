
import java.io.*
import java.util.zip.*

object ZipCompress {

    @Throws(Exception::class)
    fun zipFile2PathAdler32(srcFile: File,targetPath: String,adler32: Long) {
        if (!File(targetPath).exists()){
            File(targetPath).mkdir()
        }
        FileInputStream(srcFile).use { fileInputStream ->
            CheckedInputStream(fileInputStream,Adler32()).use { checkedInputStream ->
                ZipInputStream(checkedInputStream).use { zipInputStream ->
                    BufferedInputStream(zipInputStream).use { bufferedInputStream ->
                        val tempFileList = ArrayList<File>()
                        var ze: ZipEntry?= zipInputStream.nextEntry
                        while (ze  != null){
                            if (ze.isDirectory && !File(targetPath,ze.name).exists()) {
                                File(targetPath,ze.name).mkdir()
                            }else {
                                val targetFile = File(targetPath,"${ze.name}.temp")
                                FileOutputStream(targetFile).use { fileOutputStream ->
                                    tempFileList.add(targetFile)
                                    bufferedInputStream.copyTo(fileOutputStream)
                                }
                            }
                            ze = zipInputStream.nextEntry
                        }
                        if(checkedInputStream.checksum.value == adler32) {
                            println("校验成功")
                            //重命名
                            for (f in tempFileList) {
                                val file = File(f.absolutePath.replace(".temp",""))
                                if (file.exists()) {
                                    file.delete()
                                }
                                f.renameTo(file)
                            }
                        } else {
                            println("校验失败")
                            //删除
                            for (f in tempFileList) {
                                f.delete()
                            }
                        }
                    }
                }
            }
        }
    }

    @Throws(Exception::class)
    fun zipFile2Path2(srcFile: File,targetPath: String) {
        if (!File(targetPath).exists()){
            File(targetPath).mkdir()
        }
        val zipFile = ZipFile(srcFile)
        val entrys = zipFile.entries()
        while (entrys.hasMoreElements()) {
            val entry = entrys.nextElement()
            if (entry.isDirectory&& !File(targetPath,entry.name).exists()) {
                File(targetPath,entry.name).mkdir()
            }else {
                File(targetPath, entry.name).outputStream().use { outputStream ->
                    zipFile.getInputStream(entry).use { inputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }
            }
        }
    }
}