package com.cube_me.cubeme_crm.Contacts;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.cube_me.cubeme_crm.AddressObject;
import com.cube_me.cubeme_crm.Email;
import com.cube_me.cubeme_crm.PhoneNumber;
import com.cube_me.cubeme_crm.R;

import org.apache.poi.sl.usermodel.Line;

import java.util.List;

/**
 * Created by Fredrick on 23-Jul-16.
 */

public class ContactsRecyclerAdapter extends RecyclerView.Adapter<ContactsRecyclerAdapter.ContactsViewHolder>{

    private LayoutInflater inflater;
    List<Contact> data;
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
        holder.contactNameTextView.setText(currentContact.contactName);
        if(!currentContact.contactSecondName.equals("null")){
            holder.contactSecondNameTV.setText(currentContact.contactSecondName);
        }
        if(!currentContact.contactDesignation.equals("null")){
            holder.contactDesignationTV.setText(currentContact.contactDesignation);
        }
        if(!currentContact.companyName.equals("null")){
            holder.contactCompanyNameTextView.setText(currentContact.companyName);
        }



//        //SETTING THE IMAGEVIEW AS CIRCULAR IMAGE VIEW
//        Resources res = context.getResources();
//        Bitmap src = BitmapFactory.decodeResource(res, R.drawable.contactimage);
//        RoundedBitmapDrawable dr =
//                RoundedBitmapDrawableFactory.create(res, src);
//        dr.setCornerRadius(Math.max(src.getWidth(), src.getHeight()) / 1f);
//        holder.contactDPImageView.setImageDrawable(dr);
//        if(currentContact.contactimage != null){
//            holder.contactDPImageView.setImageIcon(currentContact.contactimage);
//        }

        holder.emailLL.removeAllViews();
        //CHECKING IF THE CONTACT HAS EMAILS - IF THE CONTACT HAS EMAIL'S IT ADD EVERY EMAIL DYNAMICALLY
        if(!currentContact.emailList.isEmpty()){
            for (int i = 0; i<currentContact.emailList.size(); i++){
                Email tempEmail = currentContact.emailList.get(i);
                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View emailView = layoutInflater.inflate(R.layout.email_tv_dynamicrow,null);
                TextView emailLabel = (TextView) emailView.findViewById(R.id.emailRowTV_label);
                TextView email = (TextView) emailView.findViewById(R.id.emailRowTV);

                emailLabel.setText(tempEmail.accountEmailLabel.substring(0,1).toUpperCase() + tempEmail.accountEmailLabel.substring(1));
                email.setText(tempEmail.accountEmail);

                holder.emailLL.addView(emailView);
            }
        }


        holder.phoneNumberLL.removeAllViews();
        //CHECKING IF THE CONTACT HAS PHONE NUMBERS - IF THE CONTACT HAS EMAIL'S IT ADD EVERY EMAIL DYNAMICALLY
        if(!currentContact.phoneNumberList.isEmpty()){
            for (int i = 0; i<currentContact.phoneNumberList.size(); i++){
                PhoneNumber tempPhoneNumber = currentContact.phoneNumberList.get(i);
                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View phoneNumberView = layoutInflater.inflate(R.layout.phoneno_tv_dynamicrow,null);
                TextView phoneNumberLabel = (TextView) phoneNumberView.findViewById(R.id.phoneRowTV_label);
                TextView phoneNumber = (TextView) phoneNumberView.findViewById(R.id.phoneRowTV);

                phoneNumberLabel.setText(tempPhoneNumber.accountPhoneLabel.substring(0,1).toUpperCase() + tempPhoneNumber.accountPhoneLabel.substring(1));
                phoneNumber.setText(tempPhoneNumber.accountPhoneNumber);

                holder.phoneNumberLL.addView(phoneNumberView);
            }
        }

        //CHECKING IF THE CONTACT HAS ADDRESS - IF THE CONTACT HAS EMAIL'S IT ADD EVERY EMAIL DYNAMICALLY
        if(!currentContact.contactAddress.equals("null")){
            String[] address = currentContact.contactAddress.split(",");
            String temp = "";
            for (int j = 0; j<address.length; j++){
                temp += address[j] +"\n";
                Log.i("AddressTV", address[j]);
            }
            holder.addressTV.setText(temp);
        }


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ContactsViewHolder extends RecyclerView.ViewHolder{

        TextView contactNameTextView;
        TextView contactCompanyNameTextView;
        TextView contactSecondNameTV;
        TextView addressTV;
        TextView contactDesignationTV;
//        ImageView contactDPImageView;
        LinearLayout phoneNumberLL;
        LinearLayout emailLL;
        LinearLayout layout;

        public ContactsViewHolder(View itemView) {
            super(itemView);
            layout = (LinearLayout) itemView.findViewById(R.id.contactRow_layout);
            phoneNumberLL = (LinearLayout) itemView.findViewById(R.id.contactRecycler_phoneNumberContainer);
            emailLL = (LinearLayout) itemView.findViewById(R.id.contactRecycler_emailContainer);
            contactNameTextView = (TextView) itemView.findViewById(R.id.contactName_textView);
            contactSecondNameTV = (TextView) itemView.findViewById(R.id.contactSecondName_textView);
            contactCompanyNameTextView = (TextView)itemView.findViewById(R.id.contactCompanyName_textView);
            addressTV = (TextView) itemView.findViewById(R.id.contactAddress_textView);
            contactDesignationTV = (TextView) itemView.findViewById(R.id.contactDesignation_textView);
//            contactDPImageView = (ImageView) itemView.findViewById(R.id.contactDP_imageView);


        }
    }

}
