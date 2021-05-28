# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.10.2)

# Declares and names the project.

#set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=gun++11"
#判断编译器类型,如果是gcc编译器,则在编译选项中加入c++11支持
if(CMAKE_COMPILER_IS_GNUCXX)
    set(CMAKE_CXX_FLAGS "-std=c++11 ${CMAKE_CXX_FLAGS}")
    message(STATUS "optional:-std=c++11")
endif(CMAKE_COMPILER_IS_GNUCXX)

project("learnopencv")

#头文件基准目录
include_directories(jniLibs/include)



#添加 opencv so库
#set(distribution_DIR ${CMAKE_SOURCE_DIR})

add_library(
        native-lib
        SHARED
        ${CMAKE_SOURCE_DIR}/native-lib.cpp sg.cpp)
add_library(
        opencv_java4
        SHARED
        IMPORTED )
set_target_properties(
        opencv_java4
        PROPERTIES IMPORTED_LOCATION
        ${CMAKE_SOURCE_DIR}/jniLibs/${ANDROID_ABI}/libopencv_java4.so
)

find_library(
        log-lib

        log )


target_link_libraries(
        native-lib
        opencv_java4
        jnigraphics

        ${log-lib} )