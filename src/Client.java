import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
class Cl{
        JFrame f=new JFrame("Client Side");  
        JTextField tf=new JTextField();
        JTextField output=new JTextField();
        JTextField input=new JTextField();
        JButton b=new JButton("Connect");  
        JButton send=new JButton("Send");  
        JLabel l1=new JLabel("Enter IP and Port:");
        JLabel l2=new JLabel("Message From Server:");
        JLabel l3=new JLabel("Write new Message:");
         JLabel message=new JLabel();
        
        Cl(){
            message.setBounds(30,100,400,20);
            l1.setBounds(50,20, 100,20); 
            l2.setBounds(50,150, 200,20); 
            l3.setBounds(50,300, 200,20); 
            tf.setBounds(50,50, 200,30);  
            b.setBounds(250,50,95,30);  
            output.setBounds(50,200,400,90);
            input.setBounds(50,350,200,30); 
            send.setBounds(250,350,95,30);
            f.add(tf);
            f.add(b);
            f.add(output);
            f.add(input);
            f.add(send); 
            f.add(l1);
            f.add(l2);
            f.add(l3);
            f.add(message);
            f.setSize(500,500);  
            f.setLayout(null);  
            f.setVisible(true);
            
            Client1 cl=new Client1();
        
        }
    public class Client1 {
	public Client1()
	{
    
    b.addActionListener(new ActionListener(){  
      public void actionPerformed(ActionEvent e){  
               String Ip=tf.getText();
               String ip[];
               String port;
               
               
            String[] ip_port = Ip.split(":");
            final int portNumber=Integer.parseInt(ip_port[1]);
               

                try {
			Socket sock = new Socket(ip_port[0],portNumber);
			InputThread sendThread = new InputThread(sock);
			Thread thread = new Thread(sendThread);
                        thread.start();
			OutputThread recieveThread = new OutputThread(sock);
			Thread thread2 =new Thread(recieveThread);
                        thread2.start();
		} catch (Exception en) 
                {
                    System.out.println(en.getMessage());
                    message.setText(en.getMessage());
                } 

            }  
          });  
 	}
}
class OutputThread implements Runnable
{
	Socket socket=null;
	BufferedReader rcv=null;
	
	public OutputThread(Socket sock) {
		this.socket = sock;
	}
	public void run() {
            	try{
		rcv = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		String msgRecieved = null;
		while((msgRecieved = rcv.readLine())!= null)
		{
                 output.setText(msgRecieved);
     		}
		}catch(Exception e){System.out.println(e.getMessage());}
       }
    }
class InputThread implements Runnable
{
	Socket socket=null;
	PrintWriter output=null;
	BufferedReader BreakInput=null;
	
	public InputThread(Socket sock)
	{
		this.socket = sock;
	}
	public void run(){
            send.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                try{
		if(socket.isConnected())
		{
                        message.setText("Client connected to "+socket.getInetAddress() + " on port "+socket.getPort());
                        output = new PrintWriter(socket.getOutputStream(), true);	
			BreakInput = new BufferedReader(new InputStreamReader(System.in));
			String msgtoServerString;
                        msgtoServerString = input.getText();
                        
			output.println(msgtoServerString);
			output.flush();
		
			if(msgtoServerString.equals("EXIT"))
        		socket.close();}}catch(Exception en){System.out.println(en.getMessage());}

                }
            });
	}
    }

}
public class Client{
public static void main(String[] args){
    Cl c=new Cl();
}
}
