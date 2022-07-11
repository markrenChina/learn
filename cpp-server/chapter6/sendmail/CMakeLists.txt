cmake_minimum_required(VERSION 2.6)

project (SEND_MAIL_DEMO)

if("${CMAKE_CXX_COMPILER_ID}" STREQUAL "MSVC")
    add_compile_options("$<$<C_COMPILER_ID:MSVC>:/utf-8>")
    add_compile_options("$<$<CXX_COMPILER_ID:MSVC>:/utf-8>")
    #add_definitions(-DUNICODE -D_UNICODE)
else()
    #set(CMAKE_CXX_FLAGS "$ENV{CXXFLAGS} -rdynamic -g -Wno-deprecated -Werror -Wno-unused-function")
    set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++0x -g -Wall -O0 -Wno-unused-variable -pthread")
    #????pthread
    #target_link_libraries(tcp-demo pthread)
endif()


set(srcs 
Base64Util.cpp
MailMonitor.cpp
Platform.cpp
SmtpSocket.cpp
main.cpp
)

add_executable(alertmailsystem ${srcs})
TARGET_LINK_LIBRARIES(alertmailsystem)
