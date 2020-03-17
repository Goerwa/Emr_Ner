#ifndef _SEG_H_
#define _SEG_H_
#include "tree.h"
#include "hmm.h"
#include "symbol.h"
#include <math.h>
#include <algorithm>
#include "fun.h"
typedef struct{
    void* t;
    int index;
    std::string str;
    intvector v;
}parameter;

typedef struct{
    void* t;
    int index;
    std::string str;
}segpara;

namespace seg{
    class Wordseg{
    public:
        std::string path;
        Dict my_dict;
        Hmm mhmm;
        Symbol mysymbol;

    public:
        Wordseg();

        ~Wordseg();

        void InitDict(const char* dictpath);

        bool CheckNoeWord(treenode* r,uint16_t x);

        void Check(intvector& line, std::vector<intvector>& part);

        void InitDistence(float ** D, intvector &part);

        void CheakChineseName(float ** D, intvector &part);

        std::string Solve(float ** D, intvector &part,int wordlen);

        std::string JionCon(string& str);

        std::string Segement(const char* str);

        std::string Segement(const char* str,int x);

        std::string QuickSegement(const char* str);

        std::string SegStr(intvector& part);

        std::string SegAll(intvector& part,int x);

        std::string _SegStr(intvector& part);

        std::string Unit(std::map<unsigned int , unsigned int>& position, intvector& part);

        bool SegFile(const char* inpath, const char* outpath);
    };
}
#endif
