/*
 * 文本处理函数
 */

#include "fun.h"
namespace com{
    void LOG_INFO(string str){
        std::cout<<"LOG_INFO :"<<str<<std::endl;
    }
    void DEBUG_INFO(string str){
        std::cout<<"DEBUG_INFO :"<<str<<std::endl;
    }
    // 逐行读取 按空格分隔
    string GetLine( char* buffer ){
        if (buffer[strlen(buffer)-2] == '\n')
            buffer[strlen(buffer)-2] = '\0';
        if (buffer[strlen(buffer)-2] == '\r')
            buffer[strlen(buffer)-2] = '\0';
        if (buffer[strlen(buffer)-1] == '\n')
            buffer[strlen(buffer)-1] = '\0';
        string bufferstr = buffer;
        return bufferstr;
    }

    string Unit(const string& str){
        string restr = "";
        vec_str words;
        Split(" ", str, words);
        for(int j =0; j< words.size(); j++){
            if(words.at(j) == " ")
                continue;
            restr += words.at(j);
        }
        return restr;
    }
    // 分隔字符串，存入vector
    void Split(const char* t,const string& str, vec_str& vec){
        string pattern = t;
        vec.clear();
        size_t last = 0, index=str.find(pattern,last);
        while (index!=string::npos){
            vec.push_back(str.substr(last,index-last));
            last = index+pattern.size();
            index = str.find(pattern,last);
        }
        if (index-last>0)
            vec.push_back(str.substr(last,index-last));
    }
    // 分隔字符串 并进行计数
    void Split(const char* t,const string& str, map_sint& vec){
        string pattern = t;
        vec.clear();
        size_t last = 0,index=str.find(pattern,last);
        while (index!=string::npos){
            vec[str.substr(last,index-last)]++;
            last=index+pattern.size();
            index=str.find(pattern,last);
        }
        if (index-last>0)
            vec[str.substr(last,index-last)]++;
    }
    // 分隔字符串，按频数存入vector
    void _Split(const char* t,const string& str, vec_str& vec){
        string pattern = t;
        vec.clear();
        map_sint temp;
        size_t last = 0, index=str.find(pattern,last);
        while (index!=string::npos){
            temp[str.substr(last,index-last)]++;
            last=index+pattern.size();
            index=str.find(pattern,last);
        }
        if (index-last>0)
            temp[str.substr(last,index-last)]++;
        for(map_sint::iterator it = temp.begin();it!= temp.end(); it++)
            vec.push_back(it->first);
    }
    // 将字符串一分为二
    void SplitOnce(const char* t,const string& str, vec_str& vec){
        string pattern = t;
        vec.clear();
        int index = str.find(pattern);
        string temp ="";
        temp.append(str, 0, index);
        vec.push_back(temp);
        temp = "";
        temp.append(str, index+pattern.size(),str.size()-index );
        vec.push_back(temp);
    }

    uint16 StrToInt(const string& str){
        char leftstr, rightstr;
        if(str.empty())
            return 0;
        int i =0;
        if(!(str[i] & 0x80)){
            return str[i];
        }else if((unsigned char)str[i] <= 0xdf && i+1<str.size()){
            leftstr = (str[i] >> 2) & 0x07;
            rightstr = (str[i+1] & 0x3f) | ((str[i] & 0x03) << 6);
            return Combine(leftstr, rightstr);
        }else if((unsigned char)str[i] <= 0xef && i + 2 < str.size()){
            leftstr = (str[i] << 4) | ((str[i+1] >> 2) & 0x0f );
            rightstr = ((str[i+1]<<6) & 0xc0) | (str[i+2] & 0x3f);
            return Combine(leftstr, rightstr);
        }else{
            return 0;
        }

    }
    bool StrToVec(const string& str, intvector& v){
        char leftstr, rightstr;
        if(str.empty())return false;
        v.clear();
        for( unsigned int i = 0; i <str.size(); ){
            if(!(str[i] & 0x80)) {
                v.push_back(str[i++]);
            }else if ((unsigned char)str[i] <= 0xdf && i + 1 < str.size()) {
                leftstr = (str[i] >> 2) & 0x07;
                rightstr = (str[i+1] & 0x3f) | ((str[i] & 0x03) << 6 );
                v.push_back(Combine(leftstr, rightstr));	i += 2;
            }else if ((unsigned char)str[i] <= 0xef && i + 2 < str.size()) {
                leftstr = (str[i] << 4) | ((str[i+1] >> 2) & 0x0f );
                rightstr = ((str[i+1]<<6) & 0xc0) | (str[i+2] & 0x3f);
                v.push_back(Combine(leftstr, rightstr));	i += 3;
            }else if((unsigned char)str[i] <= 0xf7 ){
                i += 4;
            }else if((unsigned char)str[i] <= 0xfb ){
                i += 5;
            }else if((unsigned char)str[i] <= 0xfd ){
                i += 6;
            }else{
                return false;
            }
        }
        return true;
    }
    string VecToStr(intvector& v, int i,int j ){
        string str = "";
        if(i >= j) return str;
        while( i != j )	{
            if( v[i] <= 0x7f )	str += char( v[i]);
            else if( v[i] <= 0x7ff ){
                str += char((( v[i]>>6) & 0x1f) | 0xc0);
                str += char(( v[i] & 0x3f) | 0x80);
            }else{
                str += char((( v[i] >> 12) & 0x0f )| 0xe0);
                str += char((( v[i]>>6) & 0x3f )| 0x80 );
                str += char(( v[i] & 0x3f) | 0x80);
            }
            i++;
        }
        return str;
    }

    string MapToJason(std::map<string,string>& dcit){
        string str = "";
        str += "{";
        for(std::map<string,string>::iterator it = dcit.begin(); it != dcit.end(); it++){
            it++;
            if(it == dcit.end()){
                it--;
                str += "'" + it->first + "'";
                str += ":";
                str += "'" + it->second + "'";
            }else{
                it--;
                str += "'" + it->first + "'";
                str += ":";
                str += "'" + it->second + "',";
            }
        }
        str += "}";
        return str;
    }

    string GetTime(){
        string timestr = "";
        time_t timep;
        struct tm *p;
        time(&timep);
        p = localtime(&timep); //取得当地时间
        timestr += ConvertToStr(1900 + p->tm_year);
        if(1 + p->tm_mon >9){
            timestr += ConvertToStr(1 + p->tm_mon);
        }else{
            timestr += ("0" + ConvertToStr(1 + p->tm_mon));
        }
        if(p->tm_mday > 9){
            timestr += ConvertToStr(p->tm_mday);
        }else{
            timestr += ("0" +ConvertToStr(p->tm_mday));
        }
        timestr += ConvertToStr(p->tm_hour);
        timestr += ConvertToStr(p->tm_min);
        timestr += ConvertToStr(p->tm_sec);
        return timestr;
    }
    string GetDate(){
        string timestr = "";
        time_t timep;
        struct tm *p;
        time(&timep);
        p = localtime(&timep); //取得当地时间
        timestr += ConvertToStr(1900 + p->tm_year);
        if(1 + p->tm_mon >9){
            timestr += ConvertToStr(1 + p->tm_mon);
        }else{
            timestr += ("0" + ConvertToStr(1 + p->tm_mon));
        }
        if(p->tm_mday > 9){
            timestr += ConvertToStr(p->tm_mday);
        }else{
            timestr += ("0" +ConvertToStr(p->tm_mday));
        }
        return timestr;
    }

    //文件处理
    char* ReadLine( char* buffer, int size, FILE *fi ){
        memset( buffer,0,size );
        return fgets(buffer, size, fi);
    }
    int WiteLine( const char *str, FILE *fo ){
        return fputs(str,fo);
    }
    bool GetDirfile(vec_str& filelist, const char* path){
        DIR *dp;
        struct dirent *dirp;
        if((dp=opendir(path))==NULL){
            com::DEBUG_INFO("open dir error");
        }
        while ((dirp=readdir(dp))!=NULL){
            //com::DEBUG_INFO(dirp->d_name);
            filelist.push_back(dirp->d_name);
        }
        closedir(dp);
        delete(dirp);
        return true;
    }

};
