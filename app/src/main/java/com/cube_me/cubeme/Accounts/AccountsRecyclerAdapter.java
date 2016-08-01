package com.cube_me.cubeme.Accounts;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cube_me.cubeme.R;

import java.util.List;

/**
 * Created by Fredrick on 14-Jul-16.
 */

public class AccountsRecyclerAdapter extends RecyclerView.Adapter<AccountsRecyclerAdapter.AccountsViewHolder> {

    private LayoutInflater inflater;
    static List<Accounts> data;

    public AccountsRecyclerAdapter(Context context, List<Accounts> data) {

        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public AccountsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.accounts_row,parent,false);
        AccountsViewHolder accountsViewHolder = new AccountsViewHolder(view);

        return accountsViewHolder;
    }

    @Override
    public void onBindViewHolder(AccountsViewHolder holder, final int position) {
        Accounts current = data.get(position);
        holder.accountsNameTextView.setText(current.getAccountName());
        holder.accountsContactNameTextView.setText(current.getAccountContactName());
        holder.imageView.setImageResource(R.drawable.ic_delete_black_24dp);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    Toast.makeText(v.getContext(), "Deleted", Toast.LENGTH_SHORT).show();

                data.remove(position);
//                notifyDataSetChanged();
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position,getItemCount());
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class AccountsViewHolder extends RecyclerView.ViewHolder {

        TextView accountsNameTextView;
        TextView accountsContactNameTextView;
        ImageView imageView;

        public AccountsViewHolder(View itemView) {
            super(itemView);
            accountsNameTextView = (TextView) itemView.findViewById(R.id.textView_accountName);
            accountsNameTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), accountsNameTextView.getText(), Toast.LENGTH_SHORT).show();
                }
            });
            accountsContactNameTextView = (TextView) itemView.findViewById(R.id.textView_contactName);
            imageView = (ImageView) itemView.findViewById(R.id.deleteImageView);


        }
    }
    public static void addNewAccounts(Accounts newObject){
        data.add(newObject);
        AccountsFragment.accountsAdapter.notifyDataSetChanged();

    }
}
