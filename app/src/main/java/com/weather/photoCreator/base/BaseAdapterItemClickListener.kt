package com.weather.photoCreator.base

interface BaseAdapterItemClickListener<T> {
    fun onItemClicked(itemModel: T , position:Int)
}