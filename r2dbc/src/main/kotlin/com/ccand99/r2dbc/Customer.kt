package com.ccand99.r2dbc

import org.springframework.data.annotation.Id

class Customer(val firstName: String, val lastName: String) {
    @Id
    var id: Long? = null

    override fun toString() = String.format(
        "Customer[id=%d, firstName='%s', lastName='%s']",
        id, firstName, lastName
    )

}