//
// Created by mark on 2021/9/28.
//
/**
 * 多模板 循环继承示例
 * @tparam Values
 */
template <typename... Values> class tuple;
template<> class tuple<> {};

template<typename Head,typename... Tail>
class tuple<Head, Tail...>: private tuple<Tail...> {
    typedef tuple<Tail...> inherited;
public:
    tuple()= default;
    tuple(Head v, Tail... vtail):m_head(v),inherited(vtail...) {};

    Head head() { return m_head; }
    inherited& tail() { return *this; }
protected:
    Head m_head;
};