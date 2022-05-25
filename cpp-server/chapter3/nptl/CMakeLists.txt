cmake_minimum_required(VERSION 3.16.3)
project(nptl)

set(CMAKE_CXX_STANDARD 17)
add_executable(nptl linuxtls.cpp)
#链接pthread
target_link_libraries(nptl pthread)
