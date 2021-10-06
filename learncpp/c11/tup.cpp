//
// Created by mark on 2021/9/28.
//
#include <iostream>
#include <complex>

using namespace std;
/**
 * 多模板 递归复合
 */
template<typename... Values> class tup;
template<> class tup<> {};

template<typename Head,typename... Tail>
class tup<Head,Tail...>
{
    typedef tup<Tail...> composited;
protected:
    composited m_tail;
    Head m_head;
public:
    tup(){}
    tup(Head v, Tail... vtail):m_tail(vtail...),m_head(v){}

    Head head() { return m_head; }
    composited& tail() { return m_tail; }
};

int main() {
    tup<int,float,string> it1(41,6.3,"nico");
    cout << sizeof(it1) << endl;
    cout << it1.head() << endl;
    cout << it1.tail().head() << endl;
    cout << it1.tail().tail().head() << endl;

    tup<string,complex<int>,bitset<16>,double>
            it2("Ace",complex<int>(3,8),bitset<16>(377),3.14159);
    cout << sizeof(it2) << endl;
    cout << it2.head() << endl;
    cout << it2.tail().head() << endl;
    cout << it2.tail().tail().head() << endl;
    cout << it2.tail().tail().tail().head() << endl;
    return 0;
}