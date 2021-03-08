package com.example.netshoesgistchallenge.common.service

interface IToMap<Entity, Map> {
    fun toMap(entity: Entity) : Map
}
