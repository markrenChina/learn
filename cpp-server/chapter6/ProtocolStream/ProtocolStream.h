/**
 *  ???????§¿????, protocolstream.h
 *  zhangyl 2017.05.27
 */

#ifndef __PROTOCOL_STREAM_H__
#define __PROTOCOL_STREAM_H__

#include <stdlib.h>
#include <sys/types.h>
#include <string>
#include <sstream>
#include <stdint.h>

 //??????§¿???????????????????????????????????§»??
namespace net
{
    enum
    {
        TEXT_PACKLEN_LEN = 4,
        TEXT_PACKAGE_MAXLEN = 0xffff,
        BINARY_PACKLEN_LEN = 2,
        BINARY_PACKAGE_MAXLEN = 0xffff,

        TEXT_PACKLEN_LEN_2 = 6,
        TEXT_PACKAGE_MAXLEN_2 = 0xffffff,

        BINARY_PACKLEN_LEN_2 = 4,               //4????????
        BINARY_PACKAGE_MAXLEN_2 = 0x10000000,   //????????256M,????

        CHECKSUM_LEN = 2,
    };

    //????§µ???
    unsigned short checksum(const unsigned short* buffer, int size);
    //?????4????????????????1~5?????
    void write7BitEncoded(uint32_t value, std::string& buf);
    //?????8??????????????1~10?????
    void write7BitEncoded(uint64_t value, std::string& buf);

    //?????1~5???????????????????4?????????
    void read7BitEncoded(const char* buf, uint32_t len, uint32_t& value);
    //?????1~10????????????4?????????
    void read7BitEncoded(const char* buf, uint32_t len, uint64_t& value);

    class BinaryStreamReader final
    {
    public:
        BinaryStreamReader(const char* ptr, size_t len);
        ~BinaryStreamReader() = default;

        virtual const char* GetData() const;
        virtual size_t GetSize() const;
        bool IsEmpty() const;
        bool ReadString(std::string* str, size_t maxlen, size_t& outlen);
        bool ReadCString(char* str, size_t strlen, size_t& len);
        bool ReadCCString(const char** str, size_t maxlen, size_t& outlen);
        bool ReadInt32(int32_t& i);
        bool ReadInt64(int64_t& i);
        bool ReadShort(short& i);
        bool ReadChar(char& c);
        size_t ReadAll(char* szBuffer, size_t iLen) const;
        bool IsEnd() const;
        const char* GetCurrent() const { return cur; }

    public:
        bool ReadLength(size_t& len);
        bool ReadLengthWithoutOffset(size_t& headlen, size_t& outlen);

    private:
        BinaryStreamReader(const BinaryStreamReader&) = delete;
        BinaryStreamReader& operator=(const BinaryStreamReader&) = delete;

    private:
        const char* const ptr;
        const size_t      len;
        const char* cur;
    };

    class BinaryStreamWriter final
    {
    public:
        BinaryStreamWriter(std::string* data);
        ~BinaryStreamWriter() = default;

        virtual const char* GetData() const;
        virtual size_t GetSize() const;
        bool WriteCString(const char* str, size_t len);
        bool WriteString(const std::string& str);
        bool WriteDouble(double value, bool isNULL = false);
        bool WriteInt64(int64_t value, bool isNULL = false);
        bool WriteInt32(int32_t i, bool isNULL = false);
        bool WriteShort(short i, bool isNULL = false);
        bool WriteChar(char c, bool isNULL = false);
        size_t GetCurrentPos() const { return m_data->length(); }
        void Flush();
        void Clear();

    private:
        BinaryStreamWriter(const BinaryStreamWriter&) = delete;
        BinaryStreamWriter& operator=(const BinaryStreamWriter&) = delete;

    private:
        std::string* m_data;
    };

}// end namespace

#endif //!__PROTOCOL_STREAM_H__