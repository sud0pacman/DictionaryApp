package com.example.dictionary.presentation.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.database.Cursor
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionary.R
import com.example.dictionary.databinding.ItemWordBinding
import com.example.dictionary.domain.AppRepositoryImpl
import com.example.dictionary.data.source.entity.Dictionary
import com.example.dictionary.presentation.ui.voice_search.VoiceSearchContract
import com.example.dictionary.utils.capitalizeFirstLetter
import com.example.dictionary.utils.myLog
import java.util.concurrent.Executors

class EngUzWordAdapter : RecyclerView.Adapter<EngUzWordAdapter.MyWordViewHolder>() {

    lateinit var clickVolume: (String) -> Unit
    lateinit var clickWordItem: (String, String, String) -> Unit
    lateinit var updateWord: (Dictionary) -> Unit

    private var cursor: Cursor? = null
    private var query: String? = null

    private var lastAnimatedItemPosition = -1


    inner class MyWordViewHolder(private val binding: ItemWordBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dictionary: Dictionary) {
            binding.word1.text = capitalizeFirstLetter(dictionary.english)
            binding.word2.text = dictionary.uzbek

            binding.root.setOnClickListener {
                clickWordItem.invoke(dictionary.english, dictionary.uzbek, dictionary.transcript)
            }

            binding.icBookmark.setOnClickListener {
                if (dictionary.is_favourite == 1) {
                    dictionary.is_favourite = 0
                    updateWord.invoke(dictionary.copy(is_favourite = 0))

                    binding.icBookmark.setImageResource(R.drawable.ic_bookmark_false)
                }
                else {
                    dictionary.is_favourite = 1
                    updateWord.invoke(dictionary.copy(is_favourite = 1))
                    binding.icBookmark.setImageResource(R.drawable.ic_bookmark_true)
                }
            }

            if (dictionary.is_favourite == 1) binding.icBookmark.setImageResource(R.drawable.ic_bookmark_true)
        }

        init {
            binding.icVolume.setOnClickListener {
                clickVolume.invoke(binding.word1.text.toString())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyWordViewHolder {
        return MyWordViewHolder(
            ItemWordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int = if (cursor == null) 0 else cursor!!.count

    @SuppressLint("Range")
    override fun onBindViewHolder(holder: MyWordViewHolder, @SuppressLint("RecyclerView") position: Int) {

        if (lastAnimatedItemPosition < position) {
            animateItem(holder.itemView)
            lastAnimatedItemPosition = position
        }

        cursor?.let {
            it.moveToPosition(position)

            val id = it.getInt(it.getColumnIndex("id"))
            val english = it.getString(it.getColumnIndex("english"))
            val type = it.getString(it.getColumnIndex("type"))
            val transcript = it.getString(it.getColumnIndex("transcript"))
            val uzbek = it.getString(it.getColumnIndex("uzbek"))
            val countable = it.getString(it.getColumnIndex("countable"))
            val is_favourite = it.getInt(it.getColumnIndex("is_favourite"))

            val dictionaryModel = Dictionary(id, english, type, transcript, uzbek, countable, is_favourite)
            holder.bind(dictionaryModel)
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setCursor(cursor: Cursor, query: String? = null) {
        this.cursor?.close()
        this.cursor = cursor
        this.query = query
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