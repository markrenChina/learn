#include <thread>
#include <iostream>
#include <sstream>

void worker_thread_func(){
    while (true)
    {
    } 
}
//bash$ g++ -g -o cpp11Thread cpp11_thread_id.cpp -lpthread
int main(int argc, const char** argv) {
    std::thread t(worker_thread_func);
    //获取线程t的ID
    std::thread::id worker_thread_id = t.get_id();
    std::cout << "worker thread id: " << worker_thread_id << std::endl;

    //获取主线程的线程ID
    std::thread::id main_thread_id = std::this_thread::get_id();
    //先将std::thread::id转换为std::ostringstream对象
    std::ostringstream oss;
    oss << main_thread_id;
    //再将std::ostringstream对象转换为std::string
    std::string str = oss.str();
    std::cout << "main thread id: "<< str << std::endl;
    //最后将std:: string 转换为整型值
    unsigned long long threadid = std::stoull(str);
    std::cout << "main thread id: " << threadid << std::endl;
    while (true){}
    return 0;
}
