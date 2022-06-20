package com.example.androidapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapp.databinding.ItemContainerContactBinding;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Contact> contacts;


    public ContactAdapter(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            return new ContactViewHolder(
                    ItemContainerContactBinding.inflate(
                            LayoutInflater.from(parent.getContext()),
                            parent,
                            false
                    )
            );

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ((ContactViewHolder) holder).setData(contacts.get(position));

    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }


    static class ContactViewHolder extends RecyclerView.ViewHolder {
        private final ItemContainerContactBinding binding;

        ContactViewHolder(ItemContainerContactBinding itemContainerContactBinding) {
            super(itemContainerContactBinding.getRoot());
            binding = itemContainerContactBinding;
        }

//        @Override
//        public void onClick(View v) {
//            Intent intent = new Intent(this, ChatScreen.class);
//            intent.putExtra("contact_id", contacts.get(i).getId());
//            intent.putExtra("connectedUsername", UsernameID);
//            startActivity(intent);
//        }

        void setData(Contact contact){
            String lastMessage, lastDate;
            if (contact.getLast() != null) {
                if (contact.getLast().length() < 15) {
                    lastMessage = contact.getLast();
                }
                else {
                    lastMessage = contact.getLast().substring(0,14) + "...";
                }
                lastDate = contact.getLastdate().substring(11,16);
            }
            else {
                lastMessage = "";
                lastDate = "";
            }
            binding.contactNickname.setText(contact.getName());
            binding.lastMessage.setText(lastMessage);
            binding.lastDate.setText(lastDate);
        }

    }

}
