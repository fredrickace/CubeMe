package com.cube_me.cubeme;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by FredrickCyril on 10/16/16.
 */

public class AttachmentListRecyclerAdapter extends RecyclerView.Adapter<AttachmentListRecyclerAdapter.AttachmentViewHolder> {

    List<String> data;
    LayoutInflater inflater;
    Context context;
    AttachmentRecyclerCommunicator communicator;

    public AttachmentListRecyclerAdapter(List<String> data, Context context,AttachmentRecyclerCommunicator communicator) {
        this.data = data;
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.communicator = communicator;
    }

    @Override
    public AttachmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.attachement_display_recycler_row, parent, false);
        AttachmentViewHolder holder = new AttachmentViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(AttachmentViewHolder holder, final int position) {
        String current = data.get(position);
        holder.nameTV.setText(current);
        holder.deleteImgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                communicator.deleteAttachedPosition(position);
            }
        });
    }



    @Override
    public int getItemCount() {
        return data.size();
    }

    public class AttachmentViewHolder extends RecyclerView.ViewHolder {

        ImageButton deleteImgButton;
        TextView nameTV;

        public AttachmentViewHolder(View itemView) {
            super(itemView);

            deleteImgButton = (ImageButton) itemView.findViewById(R.id.attachementRow_deleteButton);
            nameTV = (TextView) itemView.findViewById(R.id.attachementRow_attachmentNameTV);
        }
    }

//    public void setCallBack(AttachmentRecyclerCommunicator communicator) {
//        this.communicator = communicator;
//    }

    public interface AttachmentRecyclerCommunicator {
        public void deleteAttachedPosition(int position);
    }
}
