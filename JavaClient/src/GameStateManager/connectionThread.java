package GameStateManager;

import java.net.InetAddress;
import java.net.Socket;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.lang.Thread;

import javax.swing.JOptionPane;


public class connectionThread extends Thread {

	public OutputStream socketOutput;
	public InputStream socketInput;
	public Socket theSocket;
	public String pNum;
	public int x;
	public int p1x;
	public int p1y;
	public int p2x;
	public int p2y;
	private boolean p1_ready, p2_ready;
	public boolean checkwin;
	public boolean breakout;
	
	public connectionThread(){
		p1_ready = false;
		p2_ready = false;
		checkwin = false;
		breakout = false;
		x=0;
		
		try
		{
			theSocket = new Socket(InetAddress.getByName("192.168.56.1"),2000);
			socketOutput = theSocket.getOutputStream();
			socketInput = theSocket.getInputStream();
			System.out.println("connect");
		}
		catch(Exception ex)
		{
			System.out.println(ex.toString());
		}
	}
	
	
	
	
	public void run()
	{
		try
		{
			byte[] readBuffer = new byte[256];
			for(int i=0;i<256;i++){
				readBuffer[i]=0;
			}
			while(true)
			{
				int i = socketInput.read(readBuffer);
				String decoded = new String(readBuffer, "UTF-8");
				String trollolol = decoded.substring(0, i);
				
				if(trollolol.equals("CONNECTED1")){
					pNum="1";
					p1_ready = true;
					p2_ready = true;
					break;
				}
				else if(trollolol.equals("CONNECTED2")){
					pNum="2";
					p1_ready = true;
					p2_ready = true;
					break;
				}
				
				
			}	
			
			while(true){
				try {
					if(breakout){
						break;
					}
					int size = socketInput.read(readBuffer);
					String decoded = new String(readBuffer, "UTF-8");
					String pos = decoded.substring(0, size);
					String[] split1 = pos.split("\n");
					
					if(split1[0].equals("lose")){
		    			checkwin=true;
		    			try
		    			{
		    				theSocket.close();
		    			}
		    			catch(Exception ex)
		    			{
		    				System.out.println(ex.toString());
		    			}
		    			break;
					}
					
					for(int i=0; i<split1.length; i++){
						String[] split2 = split1[i].split(",");
						if(split2.length==7){
							if(Integer.parseInt(split2[0])>0){
								x = Integer.parseInt(split2[0]);
							}
							p1x=Integer.parseInt(split2[2]);
							p1y=Integer.parseInt(split2[3]);
							p2x=Integer.parseInt(split2[5]);
							p2y=Integer.parseInt(split2[6]);
						}
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}			
	}
	
	public boolean ready() {
		return p1_ready && p2_ready;
	}
}

