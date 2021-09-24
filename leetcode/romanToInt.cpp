//
// Created by mark on 2021/9/23.
//
#include <iostream>
#include <string>

using namespace std;

int romanToInt(string s) {
    int res = 0;
    size_t len = strlen(s.c_str());
    for (int i = 0; i < len; ++i){
        switch (s[i]) {
            case 'I': {
                int temp = i + 1;
                if (temp <= len ){
                    if(s[temp] == 'V'){
                        res += 4;
                        i++;
                        continue;
                    }else if(s[temp] == 'X'){
                        res += 9;
                        i++;
                        continue;
                    }
                }
                res += 1;
            } break;
            case 'V': res += 5 ;break;
            case 'X': {
                int temp = i + 1;
                if (temp < len ){
                    if(s[temp] == 'L'){
                        res += 40;
                        i++;
                        continue;
                    }else if(s[temp] == 'C'){
                        res += 90;
                        i++;
                        continue;
                    }
                }
                res += 10;
            }break;
            case 'L': res+= 50; break;
            case 'C': {
                int temp = i + 1;
                if (temp < len ){
                    if(s[temp] == 'D'){
                        res += 400;
                        i++;
                        continue;
                    }else if(s[temp] == 'M'){
                        res += 900;
                        i++;
                        continue;
                    }
                }
                res += 100;
            }break;
            case 'D': res += 500; break;
            case 'M': res += 1000; break;
        }
    }
    return res;
}

int main(){
    cout << romanToInt("III") << endl;
    cout << romanToInt("IV") << endl;
    cout << romanToInt("IX") << endl;
    cout << romanToInt("LVIII") << endl;
    cout << romanToInt("MCMXCIV") << endl;
    cout << romanToInt("DCXXI") << endl;
}

