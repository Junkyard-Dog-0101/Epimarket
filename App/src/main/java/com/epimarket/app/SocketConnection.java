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

public class SocketConnection
{
    private Socket connection;
    private PrintWriter out;
    private BufferedReader in;

    public SocketConnection(String IP, int port)
    {
        try
        {
            //InetAddress serverAddr = InetAddress.getByName(SERVER_IP);

            connection = new Socket(IP, port);
        }
        catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public String readOnServer()
    {
        try
        {
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            //Log.e("lol", in.readLine());
            return (in.readLine());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return (null);
    }

    public void writeOnServer(String content)
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

    }
}
