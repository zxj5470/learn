//
// Created by zh on 2018/1/18.
//

#ifndef ZXJSON_ZXJSONC_H
#define ZXJSON_ZXJSONC_H

#ifndef ISDIGIT_DEFINE
#define ISDIGIT_DEFINE
#define ISDIGIT(ch)         ((ch) >= '0' && (ch) <= '9')
#define ISDIGIT1TO9(ch)     ((ch) >= '1' && (ch) <= '9')
#endif

enum ZxType {
    ZXTYPE_NULL,
    ZXTYPE_TRUE,
    ZXTYPE_FALSE,
    ZXTYPE_NUMBER,
    ZXTYPE_STRING,
    ZXTYPE_ARRAY,
    ZXTYPE_OBJECT
};

enum ZxSymbols {
    ZX_Colon = ':',//Ã°ºÅ
    ZX_Quote = '"',//ÒýºÅ
    ZX_LSquareBracket = '[',    //·½À¨ºÅ£¬»¨À¨ºÅ
    ZX_RSquareBracket = ']',
    ZX_LBracket = '{',
    ZX_RBracket = '}',
    ZX_Slash = '\\',
};

enum ZxStatus{
    ZX_PARSE_OK,
    ZX_PARSE_EXPECT_VALUE,
    ZX_PARSE_INVALID_VALUE,
    ZX_PARSE_ROOT_NOT_SINGULAR,
};
#endif //ZXJSON_ZXJSONC_H