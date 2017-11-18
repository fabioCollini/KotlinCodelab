package com.example.android.myaddressbook

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView

class ContactViewHolder(
        activity: AppCompatActivity,
        parent: ViewGroup
) : RecyclerView.ViewHolder(LayoutInflater.from(activity).inflate(R.layout.contact_list_item, parent, false)) {

    private val nameLabel: TextView = itemView.findViewById(R.id.textview_name)
    private var contact: Contact? = null

    init {
        itemView.setOnClickListener {
            ContactDetailDialog.show(activity, contact, adapterPosition)
        }
    }

    fun bind(contact: Contact) {
        this.contact = contact
        val text = "${contact.name}\n${contact.email}"
        nameLabel.text = text
    }
}
