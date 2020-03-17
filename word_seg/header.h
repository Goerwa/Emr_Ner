#ifndef _HEADER_H
#define _HEADER_H
#include <map>
#include <vector>
#include <string>
#include <sstream>
#include <time.h>
#include <stdio.h>
#include <iostream>
#include "string.h"
#include <stdlib.h>
#include <stdint.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <dirent.h>
#define SHORT_LENTH 250
#define MAX_LENTH 50000
typedef struct{
    float d;
    std::string word;
}wdlen;

typedef FILE* _FILE;

typedef uint16_t uint16; // 无符号的16位整型

typedef std::string string;

typedef std::map<string,int> map_sint;

typedef std::vector<string> vec_str;

typedef std::vector<uint16> intvector;

#endif

