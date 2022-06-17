cmake_minimum_required(VERSION 3.16.3)
project(linux-poll-server)

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

add_executable(epoll-server epoll_server.cpp)
add_executable(epoll-server-write-lt epoll_server_write_event_lt.cpp)
add_executable(epoll-server-write-et epoll_server_write_event_et.cpp)
add_executable(epoll-server-with-oneshot epoll_server_with_oneshot.cpp)
add_executable(epoll-server-with-oneshot2 epoll_server_with_oneshot_2.cpp)