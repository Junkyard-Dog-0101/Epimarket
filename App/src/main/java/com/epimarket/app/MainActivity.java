package com.epimarket.app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity implements IActivity
{
    private Handler             mHandler = new Handler();
    private SocketConnection    mStaticSocket = SocketConnection.getInstance();
    private List<Product>       list = new ArrayList<Product>();
    private Boolean             mLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
        }
        mStaticSocket.addActivityStack(this);
        new Thread(mStaticSocket).start();

    }

    public void click_getcart(View view)
    {
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }

    public void sendMessage(View view)
    {
        if (mLogin == false)
        {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        else
        {
            mStaticSocket.writeOnServer("logout");
        }
    }

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
*/
    /*@Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.action_settings)
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/



    @Override
    public void responseUpdateContent(String data)
    {
        String[] split = data.split(":");

        if (split[0].equals("getproducts"))
        {
            String[] tab = split[1].split(";");
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
                Log.e("NullPointerException", e.toString());
            }
            catch (ArrayIndexOutOfBoundsException e)
            {
                Log.e("ArrayIndexOutOfBoundsException", e.toString());
            }
            final List<String> listName2 = listName;
            final ListView lvListe = (ListView)findViewById(R.id.listView);
            mHandler.post(new Runnable()
            {
                @Override
                public void run()
                {
                    lvListe.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, listName2));
                    lvListe.setTextFilterEnabled(true);
                    lvListe.setOnItemClickListener(new AdapterView.OnItemClickListener()
                    {
                        public void onItemClick(AdapterView<?> arg0, View v, int position, long id)
                        {
                            Intent intent = new Intent(MainActivity.this, ProductActivity.class);
                            //EditText editText = (EditText) findViewById(R.id.edit_message);
                            //String message = editText.getText().toString();
                           // intent.putExtra(EXTRA_MESSAGE, message);
                            intent.putExtra("productObject", list.get(position));
                            startActivity(intent);
                           /* AlertDialog.Builder adb = new AlertDialog.Builder(
                                    this);
                            adb.setTitle("ListView OnClick");
                            adb.setMessage("Selected Item is = "
                                    + lvListe.getItemAtPosition(position));
                            adb.setPositiveButton("Ok", null);
                            adb.show();*/
                        }
                    });
                }
            });
        }
        else if (split[0].equals("login") && split[1].equals("ok"))
        {
            mLogin = true;
            mHandler.post(new Runnable()
            {
                @Override
                public void run()
                {
                    Button button = (Button) findViewById(R.id.button);
                    button.setText("Logout");
                    TextView textView = (TextView) findViewById(R.id.LoginDisplay);
                    textView.setText("LOGIN");
                    textView.setTextColor(Color.GREEN);
                    Context context = getApplicationContext();
                    CharSequence text = "You're connected !";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            });
        }
        else if (split[0].equals("logout"))
        {
            mLogin = false;
            mHandler.post(new Runnable()
            {
                @Override
                public void run()
                {
                    Button button = (Button) findViewById(R.id.button);
                    button.setText("Login");
                    TextView textView = (TextView) findViewById(R.id.LoginDisplay);
                    textView.setText("NOT LOG");
                    textView.setTextColor(Color.RED);
                    Context context = getApplicationContext();
                    CharSequence text = "diconnected !";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            });
        }


    }


    public static class PlaceholderFragment extends Fragment
    {

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
