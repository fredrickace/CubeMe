package com.cube_me.cubeme.Contacts;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.cube_me.cubeme.R;

import java.util.List;

/**
 * Created by Fredrick on 23-Jul-16.
 */

public class ContactsRecyclerAdapter extends RecyclerView.Adapter<ContactsRecyclerAdapter.ContactsViewHolder>{

    private LayoutInflater inflater;
    static List<Contact> data;
    Context context;

    public ContactsRecyclerAdapter(Context context, List<Contact> data) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.data = data;
    }

    @Override
    public ContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.contacts_recycler_row,parent,false);
        ContactsViewHolder contactsViewHolder = new ContactsViewHolder(view);
        return contactsViewHolder;
    }

    @Override
    public void onBindViewHolder(ContactsViewHolder holder, int position) {
        if(position%2 == 0){
            holder.layout.setBackgroundColor(holder.itemView.getResources().getColor(R.color.recyclerViewBG));
        }
        Contact currentContact = data.get(position);
        holder.contactNameTextView.setText(currentContact.companyName);
        holder.contactCompanyNameTextView.setText(currentContact.contactName);
        if(currentContact.email != null) {
            holder.contactEmailTextView.setText(currentContact.email);
        }

        //SETTING THE IMAGEVIEW AS CIRCULAR IMAGE VIEW
        Resources res = context.getResources();
        Bitmap src = BitmapFactory.decodeResource(res, R.drawable.contactimage);
        RoundedBitmapDrawable dr =
                RoundedBitmapDrawableFactory.create(res, src);
        dr.setCornerRadius(Math.max(src.getWidth(), src.getHeight()) / 1f);
        holder.contactDPImageView.setImageDrawable(dr);
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
        ImageView contactDPImageView;
        LinearLayout layout;

        public ContactsViewHolder(View itemView) {
            super(itemView);
            layout = (LinearLayout) itemView.findViewById(R.id.contactRow_layout);
            contactNameTextView = (TextView) itemView.findViewById(R.id.contactName_textView);
            contactCompanyNameTextView = (TextView)itemView.findViewById(R.id.contactCompanyName_textView);
            contactEmailTextView = (TextView) itemView.findViewById(R.id.contactEmail_textView);
            contactDPImageView = (ImageView) itemView.findViewById(R.id.contactDP_imageView);


        }
    }

}
