cmake_minimum_required(VERSION 3.16.3)
project(win-fiber)

set(CMAKE_CXX_STANDARD 17)

add_compile_options("$<$<C_COMPILER_ID:MSVC>:/utf-8>")
add_compile_options("$<$<CXX_COMPILER_ID:MSVC>:/utf-8>")
add_definitions(-DUNICODE -D_UNICODE)
add_executable(win-fiber test-fiber.cpp)
