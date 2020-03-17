/*
 * 分词
 */
#include "seg.h"
namespace seg{
    Wordseg::Wordseg() {
//        std::string hmm_path = path + "/hmm";
//        mhmm = new Hmm(hmm_path.c_str());
    }

    Wordseg::~Wordseg() {}

    void Wordseg::InitDict(const char* dictpath) {
        path = dictpath;
        std::string segdictpath = path + "dict";
        std::cout<<segdictpath<<std::endl;
        my_dict.LoadDict(segdictpath.c_str());
        mysymbol.Init(path.c_str());
//        commom::DEBUG_INFO(commom::ConvertToStr(mysymbol.m_connet.size()));
    }

    bool Wordseg::CheckNoeWord(treenode* r,uint16_t x) {
        if (r->childlist.find(x) == r->childlist.end()) {
            return true;
        }
        trienodemap::iterator it = r->childlist.find(x);
        r = it->second;
        if (r->wordtag && (my_dict.nodelist[r->index].freq>10000)) {
            return true;
        }
        return false;
    }

    void Wordseg::InitDistence(float** D, intvector &part) {
        int wordlen = part.size();
        long long twd = 400000000000LL;
        for (int i=0 ,h=0; i<wordlen ; ++i, ++h) {
            treenode* p = my_dict.root;
            int j = i;
            while (p->childlist.find(part[j]) != p->childlist.end()) {
                trienodemap::iterator it = p->childlist.find(part[j]);
                p = it->second;
                if (p->wordtag) {
                    float temp = my_dict.nodelist[p->index].freq;
                    temp /= twd;
                    D[h][j] = 0 - log(temp)/sqrt(float(my_dict.nodelist[p->index].wlen));
                }
                if (j+1 < wordlen)
                    j++;
            }
        }
    }

    void Wordseg::CheakChineseName(float ** D, intvector &part) {
        int i = 0;
        for (intvector::iterator it = part.begin(); it!= part.end(); it++,i++){
            if(mysymbol.CheckName(*it)) {
                unsigned x = 0;
                float snf = 0;
                float a = 0.4;
                if(i + 1 <  part.size()) {
                    snf= mysymbol.namefre[com::VecToStr( part, i+1, i+2)];
                    D[i][i+1] = std::min(D[i][i+1],float(350/(pow(snf,a))));
                }
                if (i + 2 <  part.size()) {
                    float lnf= mysymbol.namefre[com::VecToStr( part, i+2, i+3)];
                    if (lnf <= 0)
                        continue;
                    treenode *r = my_dict.root;
                    if (!CheckNoeWord(r,part.at(i+2))) {
                        if (0!=snf) {
                            D[i][i+2]= std::min(D[i][i+2], float((100/pow(snf,a))*110/pow(lnf,a))) ;
                        }
                    }
                }
            }
        }
    }

    std::string Wordseg::Unit(std::map<unsigned int , unsigned int>& position, intvector& part) {
        int lastunicode = 0;
        bool single = false;
        std::string str = "";
        for(std::map<unsigned int , unsigned int> :: iterator it = position.begin();
        it != position.end(); it++) {
            str =(str == ""? str : str+" ") + com::VecToStr( part, it->first, it->second);
        }
        return str;
    }
    // 计算最优路径
    std::string Wordseg::Solve(float** D,intvector& part, int wordlen) {
        std::map<unsigned int , unsigned int> position;
        float score=0;
        std::map<float,std::string> dmap;
        float * worddistance = new float[wordlen];
        std::string*strdistance = new std::string[wordlen];
        float distance = 0;
        std::string pathstr = "";
        for (int i=0; i<wordlen; ++i) {
            distance = D[0][i];
            pathstr =  com::ConvertToStr(i);
            for(int k = 0; k<i; ++k){
                if (distance > (worddistance[k] + D[k+1][i])){
                    distance = worddistance[k] + D[k+1][i];
                    pathstr = strdistance[k] + com::ConvertToStr(i);
                }
            }
            worddistance[i] = distance;
            strdistance[i] = pathstr + "/";
        }
        std::string wa = strdistance[wordlen-1];
        int temp_n=0;
        while(std::string::npos != wa.find("/")){
            std::string temp_sttr= std::string(wa,0,wa.find("/"));
            wa=std::string(wa,wa.find("/")+1);
            position.insert(std::pair<unsigned int , unsigned int>(temp_n,(atoi(temp_sttr.c_str())+1)));
            temp_n=atoi(temp_sttr.c_str())+1;
        }
        delete []worddistance;
        delete []strdistance;
        return Unit(position,part);
    }
    // 以trie树根节点中未出现的字符进行切割
    void Wordseg::Check(intvector& line, std::vector<intvector>& part) {
        treenode* p = my_dict.root;
        part.clear();
        intvector temp;
        for (intvector::iterator it = line.begin(); it != line.end(); ) {
            if (p->childlist.find(*it) == p->childlist.end()) {
                it = line.erase(it);
                if (temp.size() != 0) {
                    part.push_back(temp);
                    temp.clear();
                }
            } else {
                temp.push_back(*it);
                it++;
            }
        }
        if (temp.size() != 0) {
            part.push_back(temp);
            temp.clear();
        }
    }

    std::string Wordseg::SegStr(intvector& part){
        const unsigned int wordlen = part.size();
        float** D = new float *[wordlen];
        for (int i=0; i<wordlen; i++) {
            D[i] = new float[wordlen];
            for (int x = i; x < wordlen;  x++) {
                D[i][x] = 100000*(0.9+x-i);
            }
        }
        InitDistence(D, part);
        CheakChineseName(D, part);
        mhmm.HmmSeg(D,part);
        std::string restr = Solve(D,part,wordlen);
        for (int v = 0; v<wordlen; ++v) {
            delete[]D[v];
        }
        delete[] D;
        return restr;
    }

    std::string Wordseg::SegAll(intvector& part, int x){
        string str = "";
        string restr = "";
        for (int i = 0; i< part.size(); i++) {
            for (int j = i+1; j <part.size(); j++) {
                str = com::VecToStr(part,i,j);
                if (my_dict.find(str) != NULL) {
                    if (j-i > x) {
                        if (restr != "") {
                            restr += (" " + str);
                        } else {
                            restr = (str);
                        }
                    }
                }
            }
        }
        return restr;
    }

    std::string Wordseg::_SegStr(intvector& part) {
        const unsigned int wordlen = part.size();
        float** D = new float *[wordlen];
        for (int i=0; i<wordlen; i++){
            D[i] = new float[wordlen];
            for(int x = i; x < wordlen;  x++){
                D[i][x] = 100000*(0.9 + x-i);
            }
        }
        InitDistence(D, part);
        std::string restr = Solve(D,part,wordlen);
        for (int v = 0; v<wordlen; ++v) {
            delete[]D[v];
        }
        delete[] D;
        return restr;
    }

    std::string Wordseg::JionCon(string& str){
        string restr = "";
        std::vector<string> v;
        std::string conectstr = "";
        com::Split(" ", str, v);
        for(int i =0; i< v.size(); i++){
            if ((v.at(i).size() == 1) && (mysymbol.CheckConnet(com::StrToInt(v.at(i))))) {
                conectstr += v.at(i);
            } else {
                if (restr == "") {
                    if (conectstr == "") {
                        restr = v.at(i);
                    } else {
                        restr = conectstr + " " + v.at(i);
                        conectstr = "";
                    }
                } else {
                    if (conectstr == "") {
                        restr += (" " + v.at(i));
                    } else {
                        restr += (" " + conectstr + " " + v.at(i));
                        conectstr = "";
                    }
                }
            }

        }
        if (conectstr != "") {
            if (restr == "") {
                restr += conectstr;
            } else {
                restr += (" " + conectstr);
            }
        }
        return restr;
    }

    std::string Wordseg::Segement(const char* str) {
        std::string strret = "";
        intvector line;
        std::vector<intvector> part;
        if (!com::StrToVec(str,  line)) {
            return "";
        }
        Check(line,  part);
        if (part.size() == 0) {
            return "";
        }
        std::vector<std::string> arg;
        for(int j =0; j< part.size(); j++){
            if(part.at(j).size() == 1){
                arg.push_back(com::VecToStr(part.at(j), 0, 1));
            }else{
                arg.push_back(SegStr(part.at(j)));
            }
        }
        for(int index = 0; index < arg.size(); index++){
            if(strret == ""){
                strret = JionCon(arg.at(index));
            }else{
                strret += (" " +JionCon(arg.at(index)));
            }
        }
        return strret;
    }
    std::string Wordseg::Segement(const char* str,int x) {
        string strret = "";
        intvector line;
        std::vector<intvector> part;
        if (!com::StrToVec(str,  line)) {
            return "";
        }
        Check(line,  part);
        if (part.size() == 0) {
            return "";
        }
        std::map<std::string,int> arg;
        strret = Segement(str);
        //commom::Split(" ", strret,arg);
        for (int j =0; j< part.size(); j++) {
            if (part.at(j).size() == 1) {
                strret += (" " + com::VecToStr(part.at(j), 0, 1));
            } else {
                strret += (" " + SegAll(part.at(j),x));
            }
        }
        com::Split(" ", strret,arg);
        strret = "";
        for (std::map<std::string,int>::iterator it = arg.begin(); it != arg.end(); it++) {
            if (strret == "") {
                strret = it->first;
            } else {
                strret += (" " + it->first);
            }
        }
        return strret;
    }
    std::string Wordseg::QuickSegement(const char* str){
        std::string strret = "";
        intvector line;
        std::vector<intvector> part;
        if (!com::StrToVec(str,  line)) {
            return "";
        }
        Check(line,  part);
        if (part.size() == 0) {
            return "";
        }
        std::vector<std::string> arg;
        for (int j =0; j< part.size(); j++) {
            if (part.at(j).size() == 1) {
                arg.push_back(com::VecToStr(part.at(j), 0, 1));
            } else {
                arg.push_back(_SegStr(part.at(j)));
            }
        }
        for(int index = 0; index < arg.size(); index++){
            if(strret == ""){
                strret = JionCon(arg.at(index));
            }else{
                strret += (" " +JionCon(arg.at(index)));
            }
        }
        return strret;
    }
}

