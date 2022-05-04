//
// Created by mark on 2022/5/3.
//

#include "Sneaky.h"

//正确： clobber能访问Sneaky对象的private和protected成员
void clobber(Sneaky &s) {
    s.j = s.prot_mem = 0;
}

//错误：clobber不能访问Base的protected成员
void clobber(Base &b) {
    b.prot_mem = 0;
}

