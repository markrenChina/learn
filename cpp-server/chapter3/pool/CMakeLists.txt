cmake_minimum_required(VERSION 3.16.3)
project(thread-pool)

set(CMAKE_CXX_STANDARD 17)
if("${CMAKE_CXX_COMPILER_ID}" STREQUAL "MSVC")
    add_compile_options("$<$<C_COMPILER_ID:MSVC>:/utf-8>")
    add_compile_options("$<$<CXX_COMPILER_ID:MSVC>:/utf-8>")
    add_definitions(-DUNICODE -D_UNICODE)
    add_executable(thread-pool TaskPool.cpp test.cpp)
else()
    add_executable(thread-pool TaskPool.cpp test.cpp)
    #链接pthread
    target_link_libraries(thread-pool pthread)
endif()
