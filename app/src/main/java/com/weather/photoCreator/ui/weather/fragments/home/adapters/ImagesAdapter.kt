package com.weather.photoCreator.ui.weather.fragments.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.weather.photoCreator.base.BaseAdapterItemClickListener
import com.weather.photoCreator.databinding.ImageItemBinding
import java.io.File

typealias MODEL_TYPE = File
typealias LIST_TYPE = List<File>
typealias CLICK_LISTENER = BaseAdapterItemClickListener<MODEL_TYPE>
typealias BINDING_TYPE = ImageItemBinding

class ImagesAdapter (myList: LIST_TYPE = listOf()) :
    RecyclerView.Adapter<ImagesAdapter.ViewHolder>() {


    var trxList: LIST_TYPE = ArrayList()
    var itemClickListener: CLICK_LISTENER ?= null
    var showDelete = false

    init {
        this.trxList = myList
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding: BINDING_TYPE =
            BINDING_TYPE.inflate(layoutInflater, parent, false)
        return ViewHolder(itemBinding)
    }

    fun submitMyList(myList: LIST_TYPE , eventListener: CLICK_LISTENER) {
        this.trxList = myList
        this.itemClickListener = eventListener
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return trxList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.myItemTX = trxList[position]
        holder.bind(holder.myItemTX)
    }



    inner class ViewHolder(var itemBinding: BINDING_TYPE) :
        RecyclerView.ViewHolder(itemBinding.root)  , View.OnClickListener{
        lateinit var myItemTX: MODEL_TYPE
        init {
            itemBinding.itemImg.setOnClickListener(this)
        }

        fun bind(myItem: MODEL_TYPE) {
            itemBinding.itemImg.setImageURI(myItem.toUri())
        }
        override fun onClick(v: View?) {
            when(v){
                itemBinding.itemImg-> {
                    itemClickListener?.onItemClicked(myItemTX , adapterPosition)
                }
            }
        }
    }


}