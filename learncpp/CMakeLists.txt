#CMake 最低版本号
cmake_minimum_required(VERSION 3.17)
#项目名称
project(learncpp)
#set定义和设置各种变量，先名称后值
#定义之后可以使用${变量名}引用变量
set(CMAKE_CXX_STANDARD 14)

#CMake使用aux_source_directory 指定源码目录
#     使用include_directories  指定include目录
#     使用link_directories     指定lib目录

# 生产动态库或静态库的名称，格式：
# add_library(libname [SHARED|STATIC|MODULE] [EXCLUDE_FROM_ALL] ...src1,src2)
# 举例：
#add_library(
#        common
#        SHARED
#        common.cpp)
# SHARED 动态库.so  STATIC .a静态库 MODULE dyld系统中有效。
# EXCLUDE_FROM_ALL 不默认构建

# 生成二进制文件，描述于其他库的依赖关系 举例：
#target_link_libraries(
#        common
#       ${log-lib})

add_executable(learncpp filefunc.cpp)
