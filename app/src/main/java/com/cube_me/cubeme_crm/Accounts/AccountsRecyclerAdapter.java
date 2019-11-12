package com.cube_me.cubeme_crm.Accounts;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cube_me.cubeme_crm.R;

import java.util.List;

/**
 * Created by Fredrick on 14-Jul-16.
 */

public class AccountsRecyclerAdapter extends RecyclerView.Adapter<AccountsRecyclerAdapter.AccountsViewHolder>{

    private LayoutInflater inflater;
    static List<Accounts> data;
    Context context;

    public AccountsRecyclerAdapter(Context context, List<Accounts> data) {

        inflater = LayoutInflater.from(context);
        this.context = context;
        this.data = data;
    }

    @Override
    public AccountsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.accounts_recycler_row,parent,false);
        AccountsViewHolder accountsViewHolder = new AccountsViewHolder(view);

        return accountsViewHolder;
    }

    @Override
    public void onBindViewHolder(AccountsViewHolder holder, final int position) {
        if(position%2 == 0){
            holder.layout.setBackgroundColor(holder.itemView.getResources().getColor(R.color.recyclerViewBG));
        }
        Accounts current = data.get(position);
        holder.accountsNameTextView.setText(current.accountName);
        if(!current.accountContactName.equals("null")) {
            holder.accountsContactNameTextView.setText(current.accountContactName);
        }


    }

    @Override
    public int getItemCount() {
        return data.size();
    }



    public class AccountsViewHolder extends RecyclerView.ViewHolder {

        TextView accountsNameTextView;
        TextView accountsContactNameTextView;
        ImageView imageView;
        LinearLayout layout;

        public AccountsViewHolder(View itemView) {
            super(itemView);
            layout = (LinearLayout) itemView.findViewById(R.id.accountViewRow_layout);
            accountsNameTextView = (TextView) itemView.findViewById(R.id.textView_accountName);
            accountsContactNameTextView = (TextView) itemView.findViewById(R.id.textView_contactName);


        }
    }

}
