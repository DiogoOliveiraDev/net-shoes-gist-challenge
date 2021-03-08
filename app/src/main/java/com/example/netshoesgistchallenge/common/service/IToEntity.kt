package com.example.netshoesgistchallenge.common.service

interface IToEntity<Map, Entity> {
    fun toEntity(map: Map) : Entity
}
