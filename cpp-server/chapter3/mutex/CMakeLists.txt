cmake_minimum_required(VERSION 3.16.3)
project(mutex)

set(CMAKE_CXX_STANDARD 17)
if("${CMAKE_CXX_COMPILER_ID}" STREQUAL "MSVC")
    add_compile_options("$<$<C_COMPILER_ID:MSVC>:/utf-8>")
    add_compile_options("$<$<CXX_COMPILER_ID:MSVC>:/utf-8>")
    add_definitions(-DUNICODE -D_UNICODE)
    add_executable(mutex mutex.cpp)
else()

    add_executable(mutex mutex.cpp)
    #链接pthread
    target_link_libraries(mutex pthread)
endif()
