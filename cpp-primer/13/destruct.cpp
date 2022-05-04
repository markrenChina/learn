//
// Created by mark on 2022/4/27.
//
#include <iostream>
#include <memory>

using namespace std;

struct child{
    //using ptr = unique_ptr<child>;
    using ptr = shared_ptr<child>;

    child() {cout << "child" << endl;}

    ~child() {
        cout << "~child" << endl;
    }
};

class parent{

public:
    explicit parent():mChild(new child){
        cout << "parent" << endl;
    }

    parent(const child::ptr &mChild) : mChild(mChild) {}

    ~parent() {
        cout << "~parent" << endl;
    }

private:
    child::ptr mChild;
};


int main(){

    child::ptr c{new child()};
    {
        auto* p = new parent(c);
        delete p;
    }
    cout << "end" << endl;
}