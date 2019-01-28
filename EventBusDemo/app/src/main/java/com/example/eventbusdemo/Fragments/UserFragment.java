package com.example.eventbusdemo.Fragments;


import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventbusdemo.AdvanceEventModel.Events;
import com.example.eventbusdemo.AdvanceEventModel.GlobalBus;
import com.example.eventbusdemo.R;

import org.greenrobot.eventbus.Subscribe;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {


    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Register the Event to Subscribe
        GlobalBus.getBus().register(this);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setClickListener(view);
    }

    public void setClickListener(final View view){
        Button btnsubmit = (Button) view.findViewById(R.id.submit);
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edtMessage = (EditText) view.findViewById(R.id.editText);

                //We are broadcasting the message here to listen to the subscriber.
                Events.FragmentActivityMessage fragmentActivityMessage =
                        new Events.FragmentActivityMessage(edtMessage.getText().toString());

                GlobalBus.getBus().post(fragmentActivityMessage);
            }
        });
    }

    @Subscribe
    public void getMessage(Events.ActivityFragmentMessage activityFragmentMessage){
        //Do Some Stuff
        TextView messageView = (TextView) getView().findViewById(R.id.message);
        messageView.setText(getString(R.string.message_received) + " " + activityFragmentMessage.getMessage());

        Toast.makeText(getActivity(),
                getString(R.string.message_fragment) +
                        " " + activityFragmentMessage.getMessage(),
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //Unregister the Registered Event.
        GlobalBus.getBus().unregister(this);
    }
}
