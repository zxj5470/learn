cmake_minimum_required(VERSION 3.10)
project(pystr)

set(CMAKE_CXX_STANDARD 11)

add_executable(pystr
        pystr_test.cpp
        pystr.hpp
        pystring.cpp
        pystring.h)
