package com.cube_me.cubeme.Contacts;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.cube_me.cubeme.R;

import java.util.List;

/**
 * Created by Fredrick on 23-Jul-16.
 */

public class ContactsRecyclerAdapter extends RecyclerView.Adapter<ContactsRecyclerAdapter.ContactsViewHolder>{

    private LayoutInflater inflater;
    static List<Contact> data;

    public ContactsRecyclerAdapter(Context context, List<Contact> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public ContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.contacts_row,parent,false);
        ContactsViewHolder contactsViewHolder = new ContactsViewHolder(view);
        return contactsViewHolder;
    }

    @Override
    public void onBindViewHolder(ContactsViewHolder holder, int position) {
        Contact currentContact = data.get(position);
        holder.contactNameTextView.setText(currentContact.getName());
        holder.contactCompanyNameTextView.setText(currentContact.getCompanyName());
        if(currentContact.getEmail() != null) {
            holder.contactEmailTextView.setText(currentContact.getEmail());
        }
//        if(currentContact.contactimage != null){
//            holder.contactDPImageView.setImageIcon(currentContact.contactimage);
//        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ContactsViewHolder extends RecyclerView.ViewHolder{

        TextView contactNameTextView;
        TextView contactCompanyNameTextView;
        TextView contactEmailTextView;
//        ImageView contactDPImageView;

        public ContactsViewHolder(View itemView) {
            super(itemView);
            contactNameTextView = (TextView) itemView.findViewById(R.id.contactName_textView);
            contactCompanyNameTextView = (TextView)itemView.findViewById(R.id.contactCompanyName_textView);
            contactEmailTextView = (TextView) itemView.findViewById(R.id.contactEmail_textView);
//            contactDPImageView = (ImageView) itemView.findViewById(R.id.contactDP_imageView);


        }
    }

    public static void addNewContact(Contact newContact){

        data.add(newContact);
        ContactsFragment.contactsAdapter.notifyDataSetChanged();
    }
}
