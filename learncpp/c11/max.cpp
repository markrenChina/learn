//
// Created by mark on 2021/9/24.
//

#include <initializer_list>
#include <iostream>

/**
 * 利用initializer_list 接受可变参数
 */
template<typename _ForwardIterator>
inline _ForwardIterator
max_element(_ForwardIterator __fist,
            _ForwardIterator __last);


struct _Iter_less_iter {
    template<typename _Iterator1,
            typename _Iterator2>
    bool
    operator()(
            _Iterator1 __it1,
            _Iterator2 __it2
    ) { return *__it1 < *__it2; }
};

inline _Iter_less_iter
__iter_less_iter() { return _Iter_less_iter(); }

template<typename _Tp>
inline _Tp
max(std::initializer_list<_Tp> __l) {
    return *max_element(__l.begin(), __l.end());
}

template<typename _ForwardIterator,
        typename _Compare>
_ForwardIterator __max_element(_ForwardIterator __first,
                               _ForwardIterator __last,
                               _Compare __comp) {
    if (__first == __last) return __first;
    _ForwardIterator __result = __first;
    while (++__first != __last) {
        if (__comp(__result, __first)) {
            __result = __first;
        }
    }
    return __result;
}

template<typename _ForwardIterator>
inline _ForwardIterator
max_element(_ForwardIterator __fist,
            _ForwardIterator __last) {
    return __max_element(__fist, __last,
                         __iter_less_iter());
}

int main() {
    std::cout << max({57, 48, 60, 100, 20, 18}) << std::endl;
    return 0;
}