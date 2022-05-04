//
// Created by mark on 2022/5/3.
//

#ifndef CPP_PRIMER_SNEAKY_H
#define CPP_PRIMER_SNEAKY_H

#include "Base.h"

class Sneaky : public Base{
    friend void clobber(Sneaky&);    //能访问 Sneaky::port_mem
    friend void clobber(Base& );     //不能访问 Base::port_mem
    int j;                           //j默认是private
};


#endif //CPP_PRIMER_SNEAKY_H
