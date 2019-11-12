package com.cube_me.cubeme_crm;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;

/**
 * Created by FredrickCyril on 9/7/16.
 */
public class EditDeleteCallBackFragment extends DialogFragment {


    ImageButton editImageButton;
    ImageButton deleteImageButton;
    ImageButton viewImageButton;
    Context context;
    EditDeleteFragment.EditDeleteCommunicator communicator;

    public EditDeleteCallBackFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //INFLATE THE LAYOUT FOR THIS FRAGMENT
        View view =  inflater.inflate(R.layout.edit_delete, container, false);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        editImageButton = (ImageButton) getView().findViewById(R.id.editDelete_edit);
        editImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                communicator.editFromDialog();
                dismiss();
            }
        });

        deleteImageButton = (ImageButton) getView().findViewById(R.id.editDelete_delete);
        deleteImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                communicator.deleteFromDialog();
                dismiss();
            }
        });

        viewImageButton = (ImageButton) getView().findViewById(R.id.editDelete_view);
        viewImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                communicator.viewFromDialog();
                dismiss();
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void setCallBack(EditDeleteFragment.EditDeleteCommunicator communicator){
        this.communicator = communicator;
    }
}
