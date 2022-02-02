package com.hfaria.portfolio.codewars.persistence

interface Mapper<O, D> {
    fun map(obj: O): D
}