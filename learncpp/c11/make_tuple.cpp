//
// Created by mark on 2021/9/25.
//

#include <iostream>

/**
* 不同模板类型，首尾特殊处理的方式
*/
using namespace std;

//print element with index IDX of tuple with MAX elements
template<int IDX, int MAX,typename... Args>
struct PRINT_TUPLE  {
    static void print(ostream& os, const tuple<Args...>& t){
        os << get<IDX>(t) << (IDX +1 == MAX ?"":",");
        PRINT_TUPLE<IDX+1,MAX,Args...>::print(os,t);
    }
};

//partial specialization to end the recursion
template<int MAX,typename... Args>
struct PRINT_TUPLE<MAX,MAX,Args...> {
    static void print(ostream& os,const tuple<Args...>& t){}
};

//output operator for tuples
template <typename... Args>
ostream& operator << (ostream& os,const tuple<Args...>& t){
    os << "[";
    PRINT_TUPLE <0,sizeof...(Args),Args...>::print(os,t);
    return os << "]";
}

int main(){
    cout << make_tuple(7.5,string("hello"),bitset<16>(377),42)
    <<endl;
}