package com.epimarket.app;

import android.util.Log;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class SocketConnection implements Runnable
{
    private Socket                      mConnection;
    private PrintWriter                 mOut;
    private BufferedReader              mIn;
    private ArrayList<IActivity>        mActivityList = new ArrayList<IActivity>();;
    private static SocketConnection     mInstance = null;
    private final static String         mIP = "192.168.1.11";
    private final static int            mPort = 4242;

    private SocketConnection()
    {

    }

    public static SocketConnection getInstance()
    {
        if (mInstance == null)
            return (mInstance = new SocketConnection());
        else
            return (mInstance);
    }

    public synchronized void addActivityStack(IActivity newActivity)
    {
        mActivityList.add(newActivity);
    }

    public synchronized void removeActivityStack(IActivity newActivity)
    {
        mActivityList.remove(newActivity);
    }

    private void callActivityUpdate(String readString)
    {
        for (IActivity current : mActivityList)
        {
            current.responseUpdateContent(readString);
        }
    }

    public synchronized void writeOnServer(String content)
    {
        try
        {
            if (mOut == null)
            {
                mOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(mConnection.getOutputStream())), true);
            }
            mOut.println(content);
        }
        catch (IOException e)
        {
            Log.e("IOException", e.toString());
        }
        catch (java.lang.NullPointerException e)
        {
            Log.e("NullPointerException", e.toString());
        }
    }

    @Override
    public void run()
    {
        try
        {
            mConnection = new Socket(mIP, mPort);
        }
        catch (UnknownHostException e)
        {
            Log.e("UnknownHostException in Thread", e.toString());
        }
        catch (IOException e)
        {
            Log.e("IOException in Thread", e.toString());
        }
        writeOnServer("getproducts");
        try
        {
            while (true)
            {
                mIn = new BufferedReader(new InputStreamReader(mConnection.getInputStream()));
                if (mIn  == null)
                    break;
                callActivityUpdate(mIn.readLine());
            }
        }
        catch (IOException e)
        {
            Log.e("IOException in Thread", e.toString());
        }
    }
}
