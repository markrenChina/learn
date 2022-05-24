//
// Created by mark on 2022/5/24.
//

#ifndef CPP_SERVER_SHAREDMUTEX_H
#define CPP_SERVER_SHAREDMUTEX_H


#include <shared_mutex>
#ifdef WIN32
#include <Windows.h>
#else
#include <pthread.h>
#endif
/**
 * 模拟c++17 std::shared_mutex
 * windows unix 平台通用
 */
class SharedMutex final {
public:
    SharedMutex();

    virtual ~SharedMutex();

    void acquireReadLock();
    void acquireWriteLock();
    void unlockReadLock();
    void unlockWriteLock();

private:
    SharedMutex(const SharedMutex& rhs) = delete;
    SharedMutex& operator=(const SharedMutex& rhs) = delete;
private:
#ifdef WIN32
    SRWLOCK             m_SRWLock;
#else
    pthread_rwlock_t    m_SRWLock{};
#endif
};
//模拟std::shared_lock
class SharedLockGuard final {
public:
    explicit SharedLockGuard(SharedMutex& sharedMutex);
    ~SharedLockGuard();
private:
public:
    SharedLockGuard(const SharedLockGuard&) =delete;
    SharedLockGuard& operator=(const SharedLockGuard& rhs) = delete;
private:
    SharedMutex& m_sharedMutex;
};
//模拟std::unique_lock
class UniqueLockGuard final {
public:
    explicit UniqueLockGuard(SharedMutex&);
    ~UniqueLockGuard();
private:
    UniqueLockGuard(const UniqueLockGuard&) =delete;
    UniqueLockGuard& operator=(const UniqueLockGuard&) = delete;
private:
    SharedMutex& m_sharedMutex;
};

#endif //CPP_SERVER_SHAREDMUTEX_H
