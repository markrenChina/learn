//
// Created by Ushop on 2021/9/23.
//

#include <iostream>
#include <cmath>

//structure declarations
struct polar{
    double distance;  //distance from origin
    double angle;   // direction from origin
};

struct rect {
    double x;   // horizontal distance from origin
    double y;   // vertical distan from origin
};

//prototypes
//pass by value
polar rect_to_polar(rect xypos);
void show_polar(polar dapos);
//pass by point
void rect_to_polar(const rect* pxy, polar* pda);
void show_polar(const polar* pda);

int main() {
    using namespace std;
    rect rplace;
    polar pplace;

    cout << "Enter the x and y values: ";
    //利用cin作为while循环的条件判断输入
    while (cin >> rplace.x >> rplace.y) { //slick usr of cin
        //pplace = rect_to_polar(rplace);  //pass by value
        rect_to_polar(&rplace, &pplace);  //pass bu point
        //show_polar(pplace);   // pass by value
        show_polar(&pplace);   // pass by value
        cout << "Next two numbers (q to quit):";
    }
    cout << "Done."<< endl;
    return 0;
}

//convert rectangular to polar coordinates
polar rect_to_polar(rect xypos) {
    using namespace std;
    polar answer;

    answer.distance = sqrt(xypos.x * xypos.x + xypos.y * xypos.y);
    answer.angle = atan2(xypos.y,xypos.x);
    return answer; //returns a polar structure
}
void rect_to_polar(const rect* pxy, polar *pda){
    using namespace std;
    pda -> distance = sqrt(pxy ->x * pxy -> x + pxy->y * pxy -> y);
    pda -> angle = atan2(pxy->y,pxy->x);
}

//show polar coordinates, converting angle to degrees
void show_polar(polar dapos){
    using namespace std;
    const double Rad_to_deg = 57.29577951;

    cout << "distance = " << dapos.distance;
    cout << ", angle = " << dapos.angle * Rad_to_deg;
    cout << " degrees\n";
}
void show_polar(const polar * pda){
    using namespace std;
    const double Rad_to_deg = 57.29577951;

    cout << "distance = "<<pda -> distance;
    cout << ", angle = " << pda->angle * Rad_to_deg;
    cout << " degrees\n";
}