package com.example.android.myaddressbook;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ContactViewHolder extends RecyclerView.ViewHolder {
    private TextView nameLabel;
    private TextView emailLabel;
    private Contact contact;

    public ContactViewHolder(final AppCompatActivity activity, ViewGroup parent) {
        super(LayoutInflater.from(activity).inflate(R.layout.contact_list_item, parent, false));
        nameLabel = itemView.findViewById(R.id.textview_name);
        emailLabel = itemView.findViewById(R.id.textview_email);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                ContactDetailDialog.show(activity, contact, getAdapterPosition());
            }
        });
    }

    public void bind(Contact contact) {
        this.contact = contact;
        nameLabel.setText(contact.getName());
        emailLabel.setText(contact.getEmail());
    }
}
