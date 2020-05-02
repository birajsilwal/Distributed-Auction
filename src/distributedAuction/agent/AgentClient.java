package distributedAuction.agent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class AgentClient extends Thread {

    private String ip;
    private int port;
    private PrintWriter out;
    private BufferedReader in;

    public AgentClient(String ip, int port){
        this.ip = ip;
        this.port = port;
    }

    private void startClient(){
        try{
            Socket socket = new Socket(ip, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch (IOException e){
            e.getStackTrace();
        }
    }

    @Override
    public void run(){
        startClient();
        super.run();
    }

    public PrintWriter getOutput(){
        return out;
    }

    public BufferedReader getInput(){
        return in;
    }
}
