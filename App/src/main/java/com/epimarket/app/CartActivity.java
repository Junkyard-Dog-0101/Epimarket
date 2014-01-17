package com.epimarket.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends Activity implements IActivity
{
    private SocketConnection    mStaticSocket = SocketConnection.getInstance();
    private Handler             mHandler = new Handler();
    //private List<Product>       list = new ArrayList<Product>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            getActionBar().setDisplayHomeAsUpEnabled(true);
        mStaticSocket.addActivityStack(this);
        mStaticSocket.writeOnServer("getcartcontent");
    }

    @Override
    public void responseUpdateContent(String data)
    {
        Log.e("pute", data);
        String[] split = data.split(":");

        if (split[0].equals("getcartcontent"))
        {
            try
            {
                if (split[1].equals("error"))
                {
                    return;
                }

            }
            catch (ArrayIndexOutOfBoundsException e)
            {
                return;
            }
            String[] tab = split[1].split(";");
            List<String> listName = new ArrayList<String>();
            try
            {
                for (int i = 0; tab.length > i; i += 4)
                {
                   // Product currentProduct = new Product(tab[i], tab[i + 1], tab[i + 2], tab[i + 3], tab[i + 4], tab[i + 5]);
                    String typeArticle = new String();
                                                                                                                                                        switch (Integer.parseInt(tab[i + 2])){case 1: typeArticle = "Playstation 13"; break; case 2: typeArticle = "Fast and furious 32"; break;case 3: typeArticle = "La belle et les 7 nains"; break;case 4: typeArticle = "Blanche neige et la bete"; break;}
                    listName.add(new String(typeArticle + " : " + tab[i + 3]));
                   // list.add(currentProduct);
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
                    Log.e("lol",listName2.get(0));
                    lvListe.setAdapter(new ArrayAdapter<String>(CartActivity.this, android.R.layout.simple_list_item_1, listName2));
                }
            });
        }
        else if (split[0].equals("pay") && split[1].equals("ok"))
        {
            mHandler.post(new Runnable()
            {
                @Override
                public void run()
                {
//                    TextView textView = (TextView) findViewById(R.id.LoginDisplay);
  //                  textView.setText("LOGIN");
    //                textView.setTextColor(Color.GREEN);
                    Context context = getApplicationContext();
                    CharSequence text = "panier payer";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            });
        }
        else if (split[0].equals("pay") && split[1].equals("error"))
        {
            mHandler.post(new Runnable()
            {
                @Override
                public void run()
                {
//                    TextView textView = (TextView) findViewById(R.id.LoginDisplay);
  //                  textView.setText("LOGIN");
    //                textView.setTextColor(Color.GREEN);
                    Context context = getApplicationContext();
                    CharSequence text = "erreur lors du paiment";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            });
        }
    }
    public void click_pay(View view)
    {
        if (true)
        {
            mStaticSocket.writeOnServer("pay");
        }
        else
        {
        Context context = getApplicationContext();
        CharSequence text = "You're not Connected !";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        }
    }
}
