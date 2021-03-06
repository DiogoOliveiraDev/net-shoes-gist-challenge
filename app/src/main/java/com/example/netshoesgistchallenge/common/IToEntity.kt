package com.example.netshoesgistchallenge.common

interface IToEntity<Map, Entity> {
    fun toEntity(map: Map) : Entity
}
