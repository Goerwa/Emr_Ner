/*
 * hmm的训练和分词
 */
#ifndef _HMM_H_
#define _HMM_H_
#include <math.h>
#include "fun.h"
#define PB 0.3
#define PE 9999
#define PM 9999
#define PS 1.3
typedef float* emission;
typedef float** PROBTRANS;
typedef std::map<uint16_t, emission> PROBE;

namespace seg{
    class Hmm{
    public:
        PROBE emissionprob;
        PROBTRANS transprob;

    public:
        Hmm();
        Hmm(char*  dictpath);

        ~Hmm();

        bool LoadHmm(const char* hmmpath);

        bool TrainHmm(const char* dictfile, const char* hmmfile);

        float HmmSeg(intvector& v, int bg, int ed);

        float HmmSeg(std::string& str);

        bool HmmSeg(std::string& strin, std::string& strout);

        bool HmmSeg(float ** D, intvector& part);
    private:
    };
}
#endif













