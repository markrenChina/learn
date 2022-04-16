//
// Created by mark on 2022/4/4.
//

#include <iostream>
#include <vector>
#include <random>
#include <ctime>

using namespace std;

vector<string> process(){
    default_random_engine randomEngine(time(nullptr));
    bernoulli_distribution u(0.5);
    if (u(randomEngine)){
        return {"true"};
    } else{
        return {"false","markrenChina"};
    }
}

int main(){
    for (const auto& s : process()) {
        cout << s << " ";
    }
    cout << endl;
}