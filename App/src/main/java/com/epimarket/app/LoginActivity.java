package com.epimarket.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity implements IActivity
{

    private SocketConnection mStaticSocket;
    private String mLogin;

    @SuppressLint("NewApi")
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
        String Password = ((EditText)findViewById(R.id.editTextPassword)).getText().toString();
        mStaticSocket.writeOnServer(new StringBuilder("login;" + mLogin + ";" + Password).toString());
    }

    @Override
    public void answerUpdateContent(String data)
    {
        Log.e("grdgr", data);
        String[] split = data.split(":");
        if (split[0].equals("login") && split[1].equals("ok"))
        {
           // TextView text = (TextView) findViewById(R.id.LoginDisplay);
            //String app_name = setString(R.string.Login);
        //    text.setText("Login");
            finish();
        }
      //  Log.e("test2", data);
    }

/*    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
}