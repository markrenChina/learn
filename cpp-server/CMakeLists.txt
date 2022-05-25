cmake_minimum_required(VERSION 3.16.3)
project(cpp-server)

set(CMAKE_CXX_STANDARD 14)

#add_executable(chapter1)
add_subdirectory(chapter3/SharedMutex)
add_subdirectory(chapter3/condition)
add_subdirectory(chapter3/mutex)
add_subdirectory(chapter3/c17sharedMutex)
add_subdirectory(chapter3/condition_variable)
add_subdirectory(chapter3/makesurethreadgroup)
add_subdirectory(chapter3/nptl)

