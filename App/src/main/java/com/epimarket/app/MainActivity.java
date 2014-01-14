package com.epimarket.app;

import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity
{
    private Handler tutu = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
        }

        new Thread()
        {
            @Override
            public void run()
            {
                Log.e("fesfse", "dans le thread");
                SocketConnection toto = new SocketConnection("10.17.72.228", 4242);
                toto.writeOnServer("getproducts");
                String[] tab = toto.readOnServer().split(";");
                List<Product> list = new ArrayList<Product>();
                List<String> listName = new ArrayList<String>();
                Log.e("tutu", "avant catch");
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
                Log.e("lol", "après catch");
                final ListView lvListe = (ListView)findViewById(R.id.listView);
                tutu.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        lvListe.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, listName2));
                    }
                });
            }
        }.start();

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
