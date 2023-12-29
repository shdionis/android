package ru.montgolfier.comments.app.view.comments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.montgolfier.comments.app.R
import ru.montgolfier.comments.app.data.Comment

class CommentsAdapter : RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder>() {
    private var data: MutableList<CommentItem> = mutableListOf(CommentItem.LoadingItem)

    fun updateData(newData: List<Comment>) {
        val newDataItems: MutableList<CommentItem> = newData.map { CommentItem.DataItem(it) }.toMutableList()
        val result = DiffUtil.calculateDiff(
            CommentsDiffUtilCallback(
                data,
                newDataItems
            )
        )
        result.dispatchUpdatesTo(this)
        data = newDataItems
    }

    fun loadingData() {
        if (data.last() is CommentItem.LoadingItem) {
            return
        }
        data.add(CommentItem.LoadingItem)
        notifyItemChanged(data.lastIndex)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder =
        when (viewType) {
            DATA_ITEM_TYPE -> {
                CommentsViewHolder.DataItemViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.comment_item, parent, false
                    )
                )
            }

            else -> {
                CommentsViewHolder.LoadingDataViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.comment_loading_item, parent, false
                    )
                )
            }
        }


    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        if (holder is CommentsViewHolder.DataItemViewHolder) {
            holder.bind(data[position])
        }
    }

    override fun getItemViewType(position: Int): Int =
        when (data[position]) {
            is CommentItem.DataItem -> DATA_ITEM_TYPE
            CommentItem.LoadingItem -> LOADING_ITEM_TYPE
        }

    class CommentsDiffUtilCallback(val oldData: List<CommentItem>, val newData: List<CommentItem>) :
        DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldData.size

        override fun getNewListSize(): Int = newData.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            if (oldData[oldItemPosition] is CommentItem.LoadingItem) return true
            return oldData[oldItemPosition] == newData[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldData[oldItemPosition]
            val newItem = newData[newItemPosition]
            if (oldItem is CommentItem.DataItem && newItem is CommentItem.DataItem) {
                return oldItem.data.id == newItem.data.id
            }
            return true
        }
    }

    sealed class CommentItem {
        object LoadingItem : CommentItem()
        data class DataItem(val data: Comment) : CommentItem()
    }

    open class CommentsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        class LoadingDataViewHolder(view: View) : CommentsViewHolder(view)
        class DataItemViewHolder(private val view: View) : CommentsViewHolder(view) {
            val name: TextView = view.findViewById(R.id.comment_name)
            val rating: TextView = view.findViewById(R.id.comment_rating)
            val text: TextView = view.findViewById(R.id.comment_text)
            fun bind(item: CommentItem) {
                if (item !is CommentItem.DataItem) {
                    return
                }
                name.text = item.data.author
                rating.text = item.data.rating.toString()
                text.text = item.data.message
            }
        }

    }

    companion object {
        const val LOADING_ITEM_TYPE = 0
        const val DATA_ITEM_TYPE = 1
    }
}
