cmake_minimum_required(VERSION 3.16.3)
project(17shared-mutex)

set(CMAKE_CXX_STANDARD 17)
if("${CMAKE_CXX_COMPILER_ID}" STREQUAL "MSVC")
    add_compile_options("$<$<C_COMPILER_ID:MSVC>:/utf-8>")
    add_compile_options("$<$<CXX_COMPILER_ID:MSVC>:/utf-8>")
    add_definitions(-DUNICODE -D_UNICODE)
    add_executable(17shared-mutex TestSharedMutexBenchmark.cpp)
else()
    add_executable(17shared-mutex TestSharedMutexBenchmark.cpp)
    #链接pthread
    target_link_libraries(17shared-mutex pthread)
endif()
