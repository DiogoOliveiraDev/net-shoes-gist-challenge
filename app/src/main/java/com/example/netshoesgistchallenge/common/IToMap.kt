package com.example.netshoesgistchallenge.common

interface IToMap<Entity, Map> {
    fun toMap(entity: Entity) : Map
}
