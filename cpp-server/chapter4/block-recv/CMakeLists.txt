cmake_minimum_required(VERSION 3.16.3)
project(block-recv)

set(CMAKE_CXX_STANDARD 17)
if("${CMAKE_CXX_COMPILER_ID}" STREQUAL "MSVC")
    add_compile_options("$<$<C_COMPILER_ID:MSVC>:/utf-8>")
    add_compile_options("$<$<CXX_COMPILER_ID:MSVC>:/utf-8>")
    #add_definitions(-DUNICODE -D_UNICODE)
else()
    set(CMAKE_CXX_FLAGS "$ENV{CXXFLAGS} -rdynamic -g -Wno-deprecated -Werror -Wno-unused-function")
    #链接pthread
    #target_link_libraries(tcp-demo pthread)
endif()
add_executable(block-recv-client blocking_client_recv.cpp)

SET(EXECUTABLE_OUTPUT_PATH ${PROJECT_SOURCE_DIR}/../bin)