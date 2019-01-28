package com.example.eventbusdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventbusdemo.AdvanceEventModel.Events;
import com.example.eventbusdemo.AdvanceEventModel.GlobalBus;
import com.example.eventbusdemo.Fragments.UserFragment;

import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView toolbar_Title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbarAndViews();

        addFragment();

    }

    @Override
    protected void onStart() {
        super.onStart();
        //Register the Event to Subscribe
        GlobalBus.getBus().register(this);
    }

    public void initToolbarAndViews(){
        toolbar = findViewById(R.id.toolbar);
        toolbar_Title = (TextView) toolbar.findViewById(R.id.titleMain);
    }

    private void addFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainer, new UserFragment())
                .commit();
    }

    public void sendMessageToFragment(View view){
        EditText etMessage = (EditText) findViewById(R.id.activityData);
        Events.ActivityFragmentMessage activityFragmentMessage =
                new Events.ActivityFragmentMessage(String.valueOf(etMessage.getText().toString()));

        GlobalBus.getBus().post(activityFragmentMessage);
    }

    @Subscribe
    public void getMessage(Events.FragmentActivityMessage fragmentActivityMessage) {
        TextView messageView = (TextView) findViewById(R.id.message);
        messageView.setText(getString(R.string.message_received) + " " + fragmentActivityMessage.getMessage());

        Toast.makeText(getApplicationContext(),
                getString(R.string.message_main_activity) + " " + fragmentActivityMessage.getMessage(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        GlobalBus.getBus().unregister(this);
    }

}
