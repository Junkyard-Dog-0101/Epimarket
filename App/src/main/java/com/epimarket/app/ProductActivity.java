package com.epimarket.app;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ProductActivity extends Activity implements IActivity
{
    private SocketConnection    mStaticSocket;
    private Product             mProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mProduct = (Product) getIntent().getSerializableExtra("productObject");
        ((TextView) findViewById(R.id.designation)).setText(mProduct.designation);
        ((TextView) findViewById(R.id.description)).setText("Description : " + mProduct.description);
        ((TextView) findViewById(R.id.price)).setText("Prix : " + mProduct.price + " €");
        ((TextView) findViewById(R.id.category)).setText("Categorie : " + mProduct.category);
        mStaticSocket = SocketConnection.getInstance();
        mStaticSocket.addActivityStack(this);
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
        if (split[0].equals("addtocart") && split[1].equals("ok"))
        {

        }
    }

    public void click_cart(View view)
    {
        mStaticSocket.writeOnServer(new StringBuilder("addtocart;" + mProduct.id + ";1").toString());
        Toast toast = Toast.makeText(getApplicationContext(), "produit ajouter au panier", Toast.LENGTH_SHORT);
        toast.show();
    }

}