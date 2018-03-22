cmake_minimum_required(VERSION 3.10)
project(DEM_TIN)

set(CMAKE_CXX_STANDARD 11)
set(CMAKE_RUNTIME_OUTPUT_DIRECTORY ${CMAKE_CURRENT_SOURCE_DIR})
add_executable(DEM_TIN main.cpp)