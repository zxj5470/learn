cmake_minimum_required(VERSION 3.9)
project(DEM_GIS_PyCallCpp)

set(CMAKE_CXX_STANDARD 11)
set(LIBRARY_OUTPUT_PATH ${PROJECT_SOURCE_DIR})

set(SOURCE_FILES gisdem.cpp gisdem.h)

add_library(gisdem SHARED ${SOURCE_FILES})

add_executable(pureCpp main.cpp gisdem.h gisdem.cpp)
