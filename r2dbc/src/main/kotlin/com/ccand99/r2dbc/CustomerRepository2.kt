package com.ccand99.r2dbc

import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface CustomerRepository2: CoroutineCrudRepository<Customer,Long> {
}