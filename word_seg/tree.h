/*
 * 前缀树
 */
#ifndef _TEEE_H_
#define _TEEE_H_
#include "fun.h"
#include <math.h>
#include <algorithm>
typedef struct{
    std::string word;   // 词
    size_t freq;    // 词频
    size_t wlen;    // 长度
} node;

struct treenode	{
    std::map <uint16_t,  treenode* > childlist;
    bool  leaftag;
    bool  wordtag;
    size_t index;
    treenode(){
        leaftag = true;
        wordtag=false;
        index = 0;
    }
};
typedef std::map<uint16_t,  treenode* > trienodemap;
namespace seg{
    class Dict{
    public:
        treenode* root;
        std::vector<node> nodelist;
    public:
        Dict();

        ~Dict();

        bool LoadDict(const char *  filepath);

        bool Insert(node& nodeInfo);

        bool DeleteNode(treenode* node);

        node* find(std::string str);

        node* find(intvector& intvec,intvector::const_iterator begin ,	intvector::const_iterator end );
    private:
        void FreeTree();
    };

}
#endif
