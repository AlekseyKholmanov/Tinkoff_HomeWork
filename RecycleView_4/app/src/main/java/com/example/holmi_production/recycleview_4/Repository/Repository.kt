package com.example.holmi_production.recycleview_4.Repository

public interface Repository<T>{
    fun add (item:T)
    fun update(item:T)
    fun remove(item:T)
    fun query(specification : Specification)
}