cmake_minimum_required(VERSION 3.9)
project(Zxjson)

set(CMAKE_CXX_STANDARD 11)

add_executable(Zxjson main.cpp Zxjson.cpp Zxjson.h ZxjsonC.h ZxjsonC.c)

add_executable(Zxjson_test ZxjsonC.h ZxjsonC.c Zxjson_test.c)