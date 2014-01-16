package com.epimarket.app;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity implements IActivity
{
    private Handler tutu = new Handler();
  //  Runnable r = new SocketConnection("10.17.72.248", 4242, this);
    Runnable r = new SocketConnection("192.168.1.11", 4242, this);
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
        }
        new Thread(r).start();

    }




    public void sendMessage(View view)
    {
        Intent intent = new Intent(this, LoginActivity.class);
       // EditText editText = (EditText) findViewById(R.id.edit_message);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings)
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void answerUpdateContent(String data)
    {
        String[] split = data.split(":");
        Log.e("0",split[0]);
        if (split[0].equals("getproducts"))
        {
            String[] tab = split[1].split(";");
            List<Product> list = new ArrayList<Product>();
            List<String> listName = new ArrayList<String>();
            try
            {
                for (int i = 0; tab.length > i; i += 6)
                {
                    Product currentProduct = new Product(tab[i], tab[i + 1], tab[i + 2], tab[i + 3], tab[i + 4], tab[i + 5]);
                    listName.add(new String(tab[i + 1]));
                    list.add(currentProduct);
                }
            }
            catch (NullPointerException e)
            {
                e.printStackTrace();
            }
            catch (ArrayIndexOutOfBoundsException e)
            {

            }
            final List<String> listName2 = listName;
            final ListView lvListe = (ListView)findViewById(R.id.listView);
            tutu.post(new Runnable() {
                @Override
                public void run() {
                    lvListe.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, listName2));
                }
            });
        }

        if (split[0].equals("login") && split[1].equals("ok"))
        {
            tutu.post(new Runnable()
            {
                @Override
                public void run()
                {
                    TextView text = (TextView) findViewById(R.id.LoginDisplay);
                    //String app_name = setString(R.string.Login);
                    text.setText("Login");
                   // lvListe.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, listName2));
                }
            });
           // finish(LoginActivity);
//R.string.Login
           
        }
   //   st1", data);

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

}
