/*
 * 人名识别
 */
#ifndef _SYMBOL_H_
#define _SYMBOL_H_
#include "fun.h"
#include <math.h>
#include <algorithm>

namespace seg{
    class Symbol{
    public:
        intvector m_name; // 姓
        intvector m_connet; // 其他字符
        std::map<std::string,unsigned int> namefre; // 名的第一个字
        std::map<std::string,unsigned int> secondnamefir; // 名的第二个字
    public:

        Symbol();

        ~Symbol();

        void Init(const char* path);

        bool CheckName(uint16_t  x);

        bool CheckConnet(uint16_t  x);

        bool LoadName(const char *  namepath );

        bool LoadConnet(const char *  connetpath);

        bool LoadNameFre (const char *  namedir);

    };
}
#endif
