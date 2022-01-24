package com.flethy.androidacademy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flethy.androidacademy.data.models.Actor
import com.google.android.material.imageview.ShapeableImageView

class ActorsAdapter : ListAdapter<Actor, ActorsAdapter.ActorViewHolder>(ActorsCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewHolder = inflater.inflate(R.layout.view_holder_actor, parent, false)
        viewHolder.layoutParams.width = parent.measuredWidth / SPAN_COUNT
        return ActorViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class ActorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val name: AppCompatTextView = itemView.findViewById(R.id.actor_name)
        private val image: ShapeableImageView = itemView.findViewById(R.id.actor_image)

        fun onBind(actor: Actor) {
            name.text = actor.name
            Glide.with(itemView.context)
                .load(actor.image)
                .into(image)
        }
    }

    companion object {
        private const val SPAN_COUNT = 4
    }

}

class ActorsCallback : DiffUtil.ItemCallback<Actor>() {
    override fun areItemsTheSame(oldItem: Actor, newItem: Actor) =
        oldItem.name == newItem.name

    override fun areContentsTheSame(oldItem: Actor, newItem: Actor) =
        oldItem == newItem
}