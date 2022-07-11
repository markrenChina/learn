cmake_minimum_required(VERSION 3.16.3)
project(protocol)

set(CMAKE_CXX_STANDARD 17)
if("${CMAKE_CXX_COMPILER_ID}" STREQUAL "MSVC")
    add_compile_options("$<$<C_COMPILER_ID:MSVC>:/utf-8>")
    add_compile_options("$<$<CXX_COMPILER_ID:MSVC>:/utf-8>")
    #add_definitions(-DUNICODE -D_UNICODE)
else()
    set(CMAKE_CXX_FLAGS "$ENV{CXXFLAGS} -rdynamic -g -Wno-deprecated -Werror -Wno-unused-function")
    #Á´½Ópthread
    #target_link_libraries(tcp-demo pthread)
endif()
add_executable(protocol ProtocolStream.cpp)