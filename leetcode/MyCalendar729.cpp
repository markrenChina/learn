//
// Created by mark on 2022/7/5.
//

#include <unordered_set>
#include <iostream>

using namespace std;

class MyCalendar {
public:

    MyCalendar() {

    }

    bool book(int start, int end) {
        
    }

private:
    unordered_set<int> starts;
    unordered_set<int> ends;
};


int main() {
    MyCalendar myCalendar;
    cout << myCalendar.book(10, 20) << endl;
    cout << myCalendar.book(15, 25) << endl;
    cout << myCalendar.book(20, 30) << endl;
}