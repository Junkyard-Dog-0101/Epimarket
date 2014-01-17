package com.epimarket.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity implements IActivity
{
    private SocketConnection    mStaticSocket;
    private String              mLogin;
    private String              mPassword;
    private Handler             mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_view);

        // Make sure we're running on Honeycomb or higher to use ActionBar APIs
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Show the Up button in the action bar.
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mStaticSocket = SocketConnection.getInstance();
        mStaticSocket.addActivityStack(this);
//        /socket.writeOnServer("getproducts");
    }

    public void TryLogin(View view)
    {
        mLogin = ((EditText)findViewById(R.id.editTextLogin)).getText().toString();
        mPassword = ((EditText)findViewById(R.id.editTextPassword)).getText().toString();
        mStaticSocket.writeOnServer(new StringBuilder("login;" + mLogin + ";" + mPassword).toString());
    }

    public void click_register(View view)
    {
       mLogin = ((EditText)findViewById(R.id.login2)).getText().toString();
       mPassword = ((EditText)findViewById(R.id.password2)).getText().toString();
       mStaticSocket.writeOnServer(new StringBuilder("register;" + mLogin + ";" + mPassword).toString());
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mStaticSocket.removeActivityStack(this);
    }



    @Override
    public void responseUpdateContent(String data)
    {
        String[] split = data.split(":");
        if (split[0].equals("login") && split[1].equals("ok"))
        {
            finish();
        }
        else if (split[0].equals("register") && split[1].equals("ok"))
        {
            //mLogin = ((EditText)findViewById(R.id.editTextLogin)).getText().toString();
            //mPassword = ((EditText)findViewById(R.id.editTextPassword)).getText().toString();
            mStaticSocket.writeOnServer(new StringBuilder("login;" + mLogin + ";" + mPassword).toString());
           // finish();
        }
        else
        {
            mHandler.post(new Runnable()
            {
                @Override
                public void run()
                {
                    Context context = getApplicationContext();
                    CharSequence text = "erreur";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            });
        }
    }
}