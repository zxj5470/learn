//
// Created by zh on 2018/1/18.
//

#ifndef ZXJSON_ZXJSON_H
#define ZXJSON_ZXJSON_H

#include "ZxjsonC.h"



class Zxjson {
public:
    int parseValue(const char *jsonString);

private:
    const char *json;
};


#endif //ZXJSON_ZXJSON_H
