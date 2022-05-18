//
// Created by mark on 2022/5/14.
//
#include <iostream>
#include <regex>

int main(){
    std::string src = "morgan (201) 555-2368 862-555-0123\r\n"
                      "drew (973)555.0130\r\n"
                      "lee (609) 555-0132 2015550175 800.555-0000\r\n";
    std::string phone =
            R"((\()?(\d{3})(\))?([-. ])?(\d{3})([-. ])?(\d{4}))";
    std::regex regex(phone);  //寻找模式所用的regex对象
    std::smatch m;
    //std::string s;
    std::string fmt = "$2.$5.$7";//将号码格式改为ddd.ddd.dddd
    //从输入文件中读取每条记录
    std::cout << std::regex_replace(src,regex,fmt) ;
    std::cout << "=====================================" << std::endl;
    std::string fmt2 = "$2.$5.$7 \r\n";//在最后一部分号码后放置空格作为分隔符
    //通知regex_replace只拷贝它替换的文本
    std::cout << std::regex_replace(src,regex,fmt2,std::regex_constants::format_no_copy);

}