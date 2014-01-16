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
    private Socket                      connection;
    private PrintWriter                 out;
    private BufferedReader              in;
    private static SocketConnection     mInstance;
    private ArrayList<IActivity>        mActivityList;
    private IActivity                   mActivity;
    private String                      mIP;
    private int                         mPort;

    public SocketConnection(String newIP, int newPort, IActivity act)
    {
        mIP = newIP;
        mPort = newPort;
        mActivity = act;
    }

    public static SocketConnection getInstance()
    {
        return (mInstance);
    }

    public synchronized void addActivityStack(IActivity newActivity)
    {
        mActivityList.add(newActivity);
    }

    private void callActivityUpdate(String readString)
    {
        for (IActivity current : mActivityList)
        {
            current.answerUpdateContent(readString);
        }
    }

    public synchronized void writeOnServer(String content)
    {
        try
        {
            if (out == null)
            {
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(connection.getOutputStream())), true);
            }
            out.println(content);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (java.lang.NullPointerException e)
        {

        }
    }

    @Override
    public void run()
    {
        try
        {
            connection = new Socket(mIP, mPort);
        }
        catch (UnknownHostException e)
        {
            Log.e("UnknownHostException", e.toString());
        }
        catch (IOException e)
        {
            Log.e("IOException in Thread", e.toString());
        }
        mActivityList = new ArrayList<IActivity>();
        addActivityStack(mActivity);
        mInstance = this;
        writeOnServer("getproducts");
        try
        {
            while (true)
            {
                in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                if (in  == null)
                    break;
                callActivityUpdate(in.readLine());
            }
        }
        catch (IOException e)
        {
            Log.e("IOException in Thread", e.toString());
        }
    }
}
