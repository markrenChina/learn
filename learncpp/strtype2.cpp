//
// Created by Ushop on 2021/5/4.
//

#include <iostream>
#include <string>

int main () {
    using namespace std;

    string s1 = "penguin";
    string s2,s3;

    cout<<"You canassign one string object to another:s2 = s1\n";
    s2 = s1;
    cout << "s1: " << s1 << " , s2: " << s2 << endl;
    cout << "You can assign a C-style string to a string object.\n";
    cout << "s2 = \"buzzard\"\n";
    s2 = "buzzard";
    cout <<"s2: "<<s2 <<endl;
    cout << "s3 = s1 + s2 \n";
    s3 = s1 + s2;
    cout << "s3: "<<s3 <<endl;
    cout <<"s1 += s2 \n";
    s1 += s2;
    cout <<"s1 = " <<s1;
    cout << "s2 += \"for a day\"\n";
    s2 += "for a day";
    cout << "s2 = " <<s2 << endl;


    return 0;
}