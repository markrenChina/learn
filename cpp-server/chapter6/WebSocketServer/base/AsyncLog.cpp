/**
 * @desc:   ???????AsyncLog.cpp
 * @author: zhangyl
 * @date:   2019.04.13
 */
#include "AsyncLog.h"
#include <ctime>
#include <time.h>
#include <sys/timeb.h>
#include <stdio.h>
#include <string.h>
#include <sstream>
#include <iostream>
#include <stdarg.h>
#include "../base/Platform.h"

#define MAX_LINE_LENGTH   256
#define DEFAULT_ROLL_SIZE 10 * 1024 * 1024

bool CAsyncLog::m_bTruncateLongLog = false;
FILE* CAsyncLog::m_hLogFile = NULL;
std::string CAsyncLog::m_strFileName = "default";
std::string CAsyncLog::m_strFileNamePID = "";
LOG_LEVEL CAsyncLog::m_nCurrentLevel = LOG_LEVEL_INFO;
int64_t CAsyncLog::m_nFileRollSize = DEFAULT_ROLL_SIZE;
int64_t CAsyncLog::m_nCurrentWrittenSize = 0;
std::list<std::string> CAsyncLog::m_listLinesToWrite;
std::shared_ptr<std::thread> CAsyncLog::m_spWriteThread;
std::mutex CAsyncLog::m_mutexWrite;
std::condition_variable CAsyncLog::m_cvWrite;
bool CAsyncLog::CAsyncLog::m_bExit = false;
bool CAsyncLog::m_bRunning = false;

bool CAsyncLog::Init(const char* pszLogFileName/* = nullptr*/, bool bTruncateLongLine/* = false*/, int64_t nRollSize/* = 10 * 1024 * 1024*/)
{
    m_bTruncateLongLog = bTruncateLongLine;
    m_nFileRollSize = nRollSize;
   
    if (pszLogFileName == nullptr || pszLogFileName[0] == 0)
    {
        m_strFileName.clear();
    }
    else
        m_strFileName = pszLogFileName;

    //???????id??????????????????????????????
    char szPID[8];
#ifdef WIN32
    snprintf(szPID, sizeof(szPID), "%05d", (int)::GetCurrentProcessId());  
#else
    snprintf(szPID, sizeof(szPID), "%05d", (int)::getpid());
#endif
    m_strFileNamePID = szPID;

    //TODO???????????

    m_spWriteThread.reset(new std::thread(WriteThreadProc));

    return true;
}

void CAsyncLog::Uninit()
{
    m_bExit = true;

    m_cvWrite.notify_one();

    if (m_spWriteThread->joinable())
        m_spWriteThread->join();
    
    if(m_hLogFile != nullptr)
	{
        fclose(m_hLogFile);
        m_hLogFile = nullptr;
	}
}

void CAsyncLog::SetLevel(LOG_LEVEL nLevel)
{
    if (nLevel < LOG_LEVEL_TRACE || nLevel > LOG_LEVEL_FATAL)
        return;
    
    m_nCurrentLevel = nLevel;
}

bool CAsyncLog::IsRunning()
{
    return m_bRunning;
}

bool CAsyncLog::Output(long nLevel, const char* pszFmt, ...)
{
    if (nLevel != LOG_LEVEL_CRITICAL)
    {
        if (nLevel < m_nCurrentLevel)
            return false;
    }
    
    std::string strLine;
    MakeLinePrefix(nLevel, strLine);

    //log????
    std::string strLogMsg;

    //???????????????????????????????
    va_list ap;
    va_start(ap, pszFmt);
    int nLogMsgLength = vsnprintf(NULL, 0, pszFmt, ap);
    va_end(ap);

    //??????????????????\0
    if ((int)strLogMsg.capacity() < nLogMsgLength + 1)
    {
        strLogMsg.resize(nLogMsgLength + 1);
    }
    va_list aq;
    va_start(aq, pszFmt);
    vsnprintf((char*)strLogMsg.data(), strLogMsg.capacity(), pszFmt, aq);
    va_end(aq);

    //string?????????length?????????????length
    std::string strMsgFormal;
    strMsgFormal.append(strLogMsg.c_str(), nLogMsgLength);

    //??????????????????????MAX_LINE_LENGTH?????
    if (m_bTruncateLongLog)
        strMsgFormal = strMsgFormal.substr(0, MAX_LINE_LENGTH);

    strLine += strMsgFormal;

    //????????????????????????¦Â????????§Ù?
    if (!m_strFileName.empty())
    {
        strLine += "\n";
    }
	
    if (nLevel != LOG_LEVEL_FATAL && nLevel != LOG_LEVEL_SYSERROR)
    {
        std::lock_guard<std::mutex> lock_guard(m_mutexWrite);
        m_listLinesToWrite.push_back(strLine);
        m_cvWrite.notify_one();
    }
    else
    {
        //?????FATAL??????????????crash?????????§Õ????????
        std::cout << strLine << std::endl;
#ifdef _WIN32
        OutputDebugStringA(strLine.c_str());
        OutputDebugStringA("\n");
#endif
        
        if (!m_strFileName.empty())
        {
            if (m_hLogFile == nullptr)
            {
                //??????
                char szNow[64];
                time_t now = time(NULL);
                tm time;
#ifdef _WIN32
                localtime_s(&time, &now);
#else
                localtime_r(&now, &time);
#endif
                strftime(szNow, sizeof(szNow), "%Y%m%d%H%M%S", &time);

                std::string strNewFileName(m_strFileName);
                strNewFileName += ".";
                strNewFileName += szNow;
                strNewFileName += ".";
                strNewFileName += m_strFileNamePID;
                strNewFileName += ".log";
                if (!CreateNewFile(strNewFileName.c_str()))
                    return false;
            }// end inner if 

            WriteToFile(strLine);

        }// end outer-if

        if (nLevel == LOG_LEVEL_FATAL)
        {
            //?¨®???????crash??
            Crash();
        } 
    }
    

    return true;
}

bool CAsyncLog::Output(long nLevel, const char* pszFileName, int nLineNo, const char* pszFmt, ...)
{
    if (nLevel != LOG_LEVEL_CRITICAL)
    {
        if (nLevel < m_nCurrentLevel)
            return false;
    }

    std::string strLine;
    MakeLinePrefix(nLevel, strLine);

    //???????
    char szFileName[512] = { 0 };
    snprintf(szFileName, sizeof(szFileName), "[%s:%d]", pszFileName, nLineNo);
    strLine += szFileName;

    //???????
    std::string strLogMsg;

    //???????????????????????????????
    va_list ap;
    va_start(ap, pszFmt);
    int nLogMsgLength = vsnprintf(NULL, 0, pszFmt, ap);
    va_end(ap);

    //??????????????????\0
    if ((int)strLogMsg.capacity() < nLogMsgLength + 1)
    {
        strLogMsg.resize(nLogMsgLength + 1);
    }
    va_list aq;
    va_start(aq, pszFmt);
    vsnprintf((char*)strLogMsg.data(), strLogMsg.capacity(), pszFmt, aq);
    va_end(aq);

    //string?????????length?????????????length
    std::string strMsgFormal;
    strMsgFormal.append(strLogMsg.c_str(), nLogMsgLength);

    //??????????????????????MAX_LINE_LENGTH?????
    if (m_bTruncateLongLog)
        strMsgFormal = strMsgFormal.substr(0, MAX_LINE_LENGTH);

    strLine += strMsgFormal;

    //????????????????????????¦Â????????§Ù?
    if (!m_strFileName.empty())
    { 
        strLine += "\n";
    }

    if (nLevel != LOG_LEVEL_FATAL && nLevel != LOG_LEVEL_SYSERROR)
    {
        std::lock_guard<std::mutex> lock_guard(m_mutexWrite);
        m_listLinesToWrite.push_back(strLine);
        m_cvWrite.notify_one();
    }
    else
    {
        //?????FATAL??????????????crash?????????§Õ????????
        std::cout << strLine << std::endl;
#ifdef _WIN32
        OutputDebugStringA(strLine.c_str());
        OutputDebugStringA("\n");
#endif

        if (!m_strFileName.empty())
        {
            if (m_hLogFile == nullptr)
            {
                //??????
                char szNow[64];
                time_t now = time(NULL);
                tm time;
#ifdef _WIN32
                localtime_s(&time, &now);
#else
                localtime_r(&now, &time);
#endif
                strftime(szNow, sizeof(szNow), "%Y%m%d%H%M%S", &time);

                std::string strNewFileName(m_strFileName);
                strNewFileName += ".";
                strNewFileName += szNow;
                strNewFileName += ".";
                strNewFileName += m_strFileNamePID;
                strNewFileName += ".log";
                if (!CreateNewFile(strNewFileName.c_str()))
                    return false;
            }// end inner if 

            WriteToFile(strLine);     
        }// end outer-if

        if (nLevel == LOG_LEVEL_FATAL)
        {
            //?¨®???????crash??
            Crash();
        }    
    }

    return true;
}

bool CAsyncLog::OutputBinary(unsigned char* buffer, size_t size)
{
    //std::string strBinary;
    std::ostringstream os;

    static const size_t PRINTSIZE = 512;
    char szbuf[PRINTSIZE * 3 + 8];

    size_t lsize = 0;
    size_t lprintbufsize = 0;
    int index = 0;
    os << "address[" << (long)buffer << "] size[" << size << "] \n";
    while (true)
    {
        memset(szbuf, 0, sizeof(szbuf));
        if (size > lsize)
        {
            lprintbufsize = (size - lsize);
            lprintbufsize = lprintbufsize > PRINTSIZE ? PRINTSIZE : lprintbufsize;
            FormLog(index, szbuf, sizeof(szbuf), buffer + lsize, lprintbufsize);
            size_t len = strlen(szbuf);

            //if (stream().buffer().avail() < static_cast<int>(len))
            //{
            //    stream() << szbuf + (len - stream().buffer().avail());
            //    break;
            //}
            //else
            //{
            os << szbuf;
            //}
            lsize += lprintbufsize;
        }
        else
        {
            break;
        }
    }

    std::lock_guard<std::mutex> lock_guard(m_mutexWrite);
    m_listLinesToWrite.push_back(os.str());
    m_cvWrite.notify_one();

    return true;
}

const char* CAsyncLog::ullto4Str(int n)
{
    static char buf[64 + 1];
    memset(buf, 0, sizeof(buf));
    sprintf(buf, "%06u", n);
    return buf;
}

char g_szchar[17] = "0123456789abcdef";

char* CAsyncLog::FormLog(int& index, char* szbuf, size_t size_buf, unsigned char* buffer, size_t size)
{
    size_t len = 0;
    size_t lsize = 0;
    int headlen = 0;
    char szhead[64 + 1] = { 0 };
    //memset(szhead, 0, sizeof(szhead));
    while (size > lsize && len + 10 < size_buf)
    {
        if (lsize % 32 == 0)
        {
            if (0 != headlen)
            {
                szbuf[len++] = '\n';
            }

            memset(szhead, 0, sizeof(szhead));
            strncpy(szhead, ullto4Str(index++), sizeof(szhead) - 1);
            headlen = strlen(szhead);
            szhead[headlen++] = ' ';

            strcat(szbuf, szhead);
            len += headlen;

        }
        if (lsize % 16 == 0 && 0 != headlen)
            szbuf[len++] = ' ';
        szbuf[len++] = g_szchar[(buffer[lsize] >> 4) & 0xf];
        szbuf[len++] = g_szchar[(buffer[lsize]) & 0xf];
        lsize++;
    }
    szbuf[len++] = '\n';
    szbuf[len++] = '\0';
    return szbuf;
}

void CAsyncLog::MakeLinePrefix(long nLevel, std::string& strPrefix)
{
    //????
    strPrefix = "[INFO]";
    if (nLevel == LOG_LEVEL_TRACE)
        strPrefix = "[TRACE]";
    else if (nLevel == LOG_LEVEL_DEBUG)
        strPrefix = "[DEBUG]";
    else if (nLevel == LOG_LEVEL_WARNING)
        strPrefix = "[WARN]";
    else if (nLevel == LOG_LEVEL_ERROR)
        strPrefix = "[ERROR]";
    else if (nLevel == LOG_LEVEL_SYSERROR)
        strPrefix = "[SYSE]";
    else if (nLevel == LOG_LEVEL_FATAL)
        strPrefix = "[FATAL]";
    else if (nLevel == LOG_LEVEL_CRITICAL)
        strPrefix = "[CRITICAL]";

    //???
    char szTime[64] = { 0 };
    GetTime(szTime, sizeof(szTime));

    strPrefix += "[";
    strPrefix += szTime;
    strPrefix += "]";

    //?????????
    char szThreadID[32] = { 0 };
    //????§Õ????Linux???????????
    //std::ostringstream osThreadID;
    //osThreadID << std::this_thread::get_id();
#ifdef WIN32
    DWORD threadId = ::GetCurrentThreadId();
#else
    int threadId = syscall(SYS_gettid);
#endif
    snprintf(szThreadID, sizeof(szThreadID), "[%d]", (int32_t)threadId);
    strPrefix += szThreadID;   
}

void CAsyncLog::GetTime(char* pszTime, int nTimeStrLength)
{   
    struct timeb tp;
    ftime(&tp);
    
    time_t now = tp.time;
    tm time;
#ifdef _WIN32
    localtime_s(&time, &now);
#else
    localtime_r(&now, &time);
#endif
    
    snprintf(pszTime, nTimeStrLength, "%04d-%02d-%02d %02d:%02d:%02d:%03d", time.tm_year + 1900, time.tm_mon + 1, time.tm_mday, time.tm_hour, time.tm_min, time.tm_sec, tp.millitm);
    //strftime(pszTime, nTimeStrLength, "[%Y-%m-%d %H:%M:%S:]", &time);
}

bool CAsyncLog::CreateNewFile(const char* pszLogFileName)
{
    if (m_hLogFile != nullptr)
    {
        fclose(m_hLogFile);
    }

    //?????????
    m_hLogFile = fopen(pszLogFileName, "w+");
    return m_hLogFile != nullptr;
}

bool CAsyncLog::WriteToFile(const std::string& data)
{
    //???????????????§Õ?????????????????????§Õ
    std::string strLocal(data);
    int ret = 0;
    while (true)
    {
        ret = fwrite(strLocal.c_str(), 1, strLocal.length(), m_hLogFile);
        if (ret <= 0)
            return false;
        else if (ret <= (int)strLocal.length())
        {
            strLocal.erase(0, ret);
        }

        if (strLocal.empty())
            break;
    }
    

    //::OutputDebugStringA(strDebugInfo.c_str());

    fflush(m_hLogFile);

    return true;
}

void CAsyncLog::Crash()
{
    char* p = nullptr;
    *p = 0;
}

void CAsyncLog::WriteThreadProc()
{
    m_bRunning = true;

    while (true)
    {        
        if (!m_strFileName.empty())
        {
            if (m_hLogFile == nullptr || m_nCurrentWrittenSize >= m_nFileRollSize)
            {
                //????m_nCurrentWrittenSize??§³
                m_nCurrentWrittenSize = 0;

                //????¦Ë????????§³????rollsize??????????
                char szNow[64];
                time_t now = time(NULL);
                tm time;
#ifdef _WIN32
                localtime_s(&time, &now);
#else
                localtime_r(&now, &time);
#endif
                strftime(szNow, sizeof(szNow), "%Y%m%d%H%M%S", &time);

                std::string strNewFileName(m_strFileName);
                strNewFileName += ".";
                strNewFileName += szNow;
                strNewFileName += ".";
                strNewFileName += m_strFileNamePID;
                strNewFileName += ".log";
                if (!CreateNewFile(strNewFileName.c_str()))
                    return;
            }// end inner if        
        }// end outer-if


        std::string strLine;
        {
            std::unique_lock<std::mutex> guard(m_mutexWrite);
            while (m_listLinesToWrite.empty())
            {
                if (m_bExit)
                    return;

                m_cvWrite.wait(guard);
            }

            strLine = m_listLinesToWrite.front();
            m_listLinesToWrite.pop_front();
        }

        
        std::cout << strLine << std::endl;

#ifdef _WIN32
        OutputDebugStringA(strLine.c_str());
        OutputDebugStringA("\n");
#endif

        if (!m_strFileName.empty())
        {
            if (!WriteToFile(strLine))
                return;

            m_nCurrentWrittenSize += strLine.length();
        }     
    }// end outer-while-loop

    m_bRunning = false;
}