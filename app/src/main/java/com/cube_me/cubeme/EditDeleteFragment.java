package com.cube_me.cubeme;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;

import com.cube_me.cubeme.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditDeleteFragment extends DialogFragment{


    ImageButton editImageButton;
    ImageButton deleteImageButton;
    ImageButton viewImageButton;
    EditDeleteCommunicator communicator;
    Context context;
    public EditDeleteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        communicator = (EditDeleteCommunicator) context;
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.edit_delete, container, false);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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

    public interface EditDeleteCommunicator{
        public void deleteFromDialog();
        public void editFromDialog();
        public void viewFromDialog();

    }

}
