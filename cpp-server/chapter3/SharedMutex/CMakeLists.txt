cmake_minimum_required(VERSION 3.19)
project(shared-mutex)

set(CMAKE_CXX_STANDARD 17)

add_executable(shared-mutex SharedMutex.cpp test.cpp)