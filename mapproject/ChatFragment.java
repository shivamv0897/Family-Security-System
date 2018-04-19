package com.example.shivam.mapproject;


import android.content.Intent;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.text.format.DateFormat;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import static android.app.Activity.RESULT_OK;



/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {

    View v;
    RelativeLayout activity_main;
    FloatingActionButton fab;
    EditText input;
    ListView listView;

    private static int SIGN_IN_REQUEST_CODE=1;
    private FirebaseListAdapter<ChatMessanger> adaptor;


    public ChatFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==SIGN_IN_REQUEST_CODE)
        {
            if(resultCode==RESULT_OK)
            {
                Toast.makeText(v.getContext(),"Sucessfully signed in...WELCOME",Toast.LENGTH_LONG).show();
                displayChatMessage();
            }
            else
            {
                Toast.makeText(v.getContext(),"Couldn't be signed in....Please try again later",Toast.LENGTH_LONG).show();
                getActivity().finish();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_chat, container, false);
        activity_main=(RelativeLayout)v.findViewById(R.id.relative_chat);
        fab=(FloatingActionButton)v.findViewById(R.id.floatingActionButton);
        input=(EditText)v.findViewById(R.id.input);
        listView=(ListView)v.findViewById(R.id.list_messages);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().push().setValue(new ChatMessanger(input.getText().toString(),FirebaseAuth.getInstance().getCurrentUser().getDisplayName()));
                input.setText("");
            }
        });
        Toast.makeText(v.getContext(),"label 1",Toast.LENGTH_SHORT).show();

        if(FirebaseAuth.getInstance().getCurrentUser()==null)
        {             Toast.makeText(v.getContext(),"label 2",Toast.LENGTH_SHORT).show();

            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(),SIGN_IN_REQUEST_CODE);
        }
         else
        {
            Toast.makeText(v.getContext(),"Welcome "+FirebaseAuth.getInstance().getCurrentUser().getEmail(),Toast.LENGTH_SHORT).show();
        }

        displayChatMessage();
        return v;
    }

    private void displayChatMessage() {
        adaptor=new FirebaseListAdapter<ChatMessanger>(getActivity(),ChatMessanger.class,R.layout.list_message_item,FirebaseDatabase.getInstance().getReference()) {

            @Override
            protected void populateView(View v, ChatMessanger model, int position) {
                TextView messageText,messageUser,messageTime;
                messageText=(TextView)v.findViewById(R.id.message_text);
                messageUser=(TextView)v.findViewById(R.id.message_user);
                messageTime=(TextView)v.findViewById(R.id.message_time);

                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",model.getTime()));
            }
        };
        listView.setAdapter(adaptor);
    }

}
