#include <iostream>

int main() {

    using namespace std;
    char* fileName = "/Users/workspace/CLionProjects/cpp/nanohttpd sequence.png";
    char* fileName_copy = "/Users/workspace/CLionProjects/cpp/copy.png";
    char* fileName_en = "/Users/workspace/CLionProjects/cpp/en.png";
    FILE *file = fopen(fileName,"r");
    FILE *file_copy = fopen(fileName_copy,"w");
    FILE *file_en = fopen(fileName_en,"w");


    if (!file || !file_copy || !fileName_en) {
        cout  << "文件打开失败" << endl;
        exit(0);
    }


    cout << "copy start " << endl;
    //字符流
    /*
     char buffer[10];
     while (fgets(buffer,10,file)){
        cout << buffer ;
        fputs(buffer,file_copy);
    }*/

    //字节流
    /*size_t buffer[512];
    int len;
    while ((len = fread(buffer,sizeof(size_t),512,file )) != 0) {
        fwrite(buffer, sizeof(size_t),len,file_copy);
    }*/

    //获取文件大小
    //移动到最后
    //fseek(file,0,SEEK_END);
    //计算偏移值
    //long file_size = ftell(file);
    //cout << "file size = " << file_size <<endl;

    //加密
    int c;
    while ((c = fgetc(file)) != EOF) {
        fputc(c^0xffffffff,file_en);
    }

    cout << "copy end" << endl;
    fclose(file);
    fclose(file_copy);
    fclose(file_en);

    //加密


    return 0;
}
