/*
 * 文本处理函数
 */

#ifndef _FUNC_H
#define _FUNC_H

#include "header.h"

namespace com{
    void LOG_INFO ( string str);
    void DEBUG_INFO( string str);

    string GetLine(char* buffer );
    string Unit(const string& str);
    void  Split(const char* d, const string& str, vec_str& vec);
    void  Split(const char* d, const string& str, map_sint& vec);
    void  _Split(const char* d,const string& str, vec_str& vec);
    void  SplitOnce(const char* d,const string& str, vec_str& vec);
    bool  StrToVec(const string& str, intvector& v);
    uint16 StrToInt(const string& str);
    string VecToStr(intvector& v, int i,int j );
    string MapToJason(std::map<string,string>& dcit);
    inline uint16 Combine(char high, char low){
        return (((uint16(high)&0x00ff)<<8)|(uint16(low)&0x00ff));
    }
    char*	ReadLine(char* buffer, int size, FILE *fi);
    int		WiteLine(const char *str, FILE *fo);
    bool	GetDirfile(vec_str& filelist, const char* path);

    string GetTime();
    string GetDate();

    template <class T> string ConvertToStr(T value) {
        std::stringstream ss;
        ss << value;
        return ss.str();
    }
    static bool SortByDistance(const wdlen& x, const wdlen& y){
        return x.d > y.d;
    }
    static bool SortBySecondGreater(const std::pair<string, float>& x,
                                    const std::pair<string, float>& y ){
        return x.second > y.second;
    }
    static bool SortBySecondLess(const std::pair<string, float>& x,
                                 const std::pair<string, float>& y ){
        return x.second < y.second;
    }
    template <class T> int MaxArray(T* prob, int n){
        if((n < 1 )||(prob == NULL))return -1;
        T temp = prob[0];	int index =0;
        for(int i =0; i< n; i++){
            if(temp < prob[i] ){
                temp = prob[i];
                index = i;
            }
        }
        return index;
    }
    template <class T> int MinArray(T* prob, int n){
        if((n < 1 )||(prob == NULL))return -1;
        T temp = prob[0];	int index =0;
        for(int i =0; i< n; i++){
            if(temp > prob[i] ){
                temp = prob[i];
                index = i;
            }
        }
        return index;
    }


}
#endif

