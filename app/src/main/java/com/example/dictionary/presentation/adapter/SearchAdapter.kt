package com.example.dictionary.presentation.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionary.databinding.ItemWordBinding
import com.example.dictionary.utils.capitalizeFirstLetter
import com.example.dictionary.utils.createSpannable

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.MySearchViewHolder>() {
    private var cursor: Cursor? = null
    private var query: String? = null
    private var curLang: Boolean = true

    private var lastAnimatedItemPosition = -1

    lateinit var clickVolume: (String) -> Unit
    lateinit var clickWordItem: (String, String, String) -> Unit

    inner class MySearchViewHolder(private val binding: ItemWordBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(english: String, uzbek: String, transcript: String) {
            if (curLang) {
                if (query != null) binding.word1.text = english.createSpannable(query!!)
                else binding.word1.text = capitalizeFirstLetter(english)

                binding.word2.text = uzbek

                binding.root.setOnClickListener {
                    clickWordItem.invoke(english, uzbek, transcript)
                }
            }
            else {
                if (query != null) binding.word1.text = uzbek.createSpannable(query!!)
                else binding.word1.text = capitalizeFirstLetter(uzbek)

                binding.word2.text = english

                binding.root.setOnClickListener {
                    clickWordItem.invoke(uzbek, english, transcript)
                }
            }
        }

        init {
            binding.icVolume.setOnClickListener {
                clickVolume.invoke(binding.word1.text.toString())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MySearchViewHolder =
        MySearchViewHolder(ItemWordBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = cursor?.count ?: 0

    @SuppressLint("Range")
    override fun onBindViewHolder(holder: MySearchViewHolder, @SuppressLint("RecyclerView") position: Int) {

        if (lastAnimatedItemPosition < position) {
            animateItem(holder.itemView)
            lastAnimatedItemPosition = position
        }

        cursor?.let {
            it.moveToPosition(position)

            val english = it.getString(it.getColumnIndex("english"))
            val uzbek = it.getString(it.getColumnIndex("uzbek"))
            val transcript = it.getString(it.getColumnIndex("transcript"))

            holder.bind(english, uzbek, transcript)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setCursor(cursor: Cursor, query: String? = null) {
        this.cursor?.close()
        this.cursor = cursor
        this.query = query

        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun changeTransfer() {
        curLang = !curLang

        notifyDataSetChanged()
    }


    private fun animateItem(view: View) {
        view.translationY = (view.context as Activity).window.decorView.height.toFloat()
        view.animate()
            .translationY(0f)
            .setInterpolator(DecelerateInterpolator(2f))
            .setDuration(900)
            .start()
    }
}