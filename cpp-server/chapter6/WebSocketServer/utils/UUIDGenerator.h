/** 
 * ???¦·???UUID?????????Windows???????????GUID, UUIDGenerator.h
 * zhangyl 20190710
 */

#ifndef __UUID_GENERATOR_H__
#define __UUID_GENERATOR_H__

#include <string>

class UUIDGenerator final
{
private:
    UUIDGenerator() = delete;
    ~UUIDGenerator() = delete;

    UUIDGenerator(const UUIDGenerator& rhs) = delete;
    UUIDGenerator& operator =(const UUIDGenerator& rhs) = delete;

public:
    static std::string generate();

};

#endif //!__UUID_GENERATOR_H__

