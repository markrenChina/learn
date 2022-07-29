cmake_minimum_required(VERSION 3.10.2)

project("flutterNative")

add_library(
        flutterNative
        SHARED
        )
find_library(
        log-lib
        log)
target_link_libraries(
        flutterNative
        ${log-lib})