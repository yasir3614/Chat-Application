import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.lang.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

class S1{
        JFrame f=new JFrame("Server Side");  
        JTextField tf=new JTextField();
        JTextField output=new JTextField();
        JTextField input=new JTextField();
        JButton b=new JButton("Start Listening");  
        JButton send=new JButton("Send");  
        JLabel l1=new JLabel("Enter Port:");
        JLabel l2=new JLabel("Message From Client:");
        JLabel l3=new JLabel("Write new Message:");
        JLabel message=new JLabel();
        
    S1() throws IOException
    {
            l1.setBounds(50,20, 100,20); 
            l2.setBounds(50,150, 200,20); 
            l3.setBounds(50,300, 200,20);
            message.setBounds(30,100,400,20);
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
        Server1 s=new Server1();        
    }
    public class Server1{
        public Server1() throws IOException{
            
                
    b.addActionListener(new ActionListener(){  
        
      public void actionPerformed(ActionEvent e){  
               int portNumber=Integer.parseInt(tf.getText());
//               	System.out.println("Server waiting for connection on port "+portNumber);
                message.setText("Server waiting for connection on port "+portNumber);
		ServerSocket ss = null;
          try {
              ss = new ServerSocket(portNumber);
          } catch (IOException ex) {
              Logger.getLogger(S1.class.getName()).log(Level.SEVERE, null, ex);
          }
		Socket clientSocket = null;
          try {
              clientSocket = ss.accept();
          } catch (IOException ex) {
              Logger.getLogger(S1.class.getName()).log(Level.SEVERE, null, ex);
          }
                message.setText("Recieved connection from "+clientSocket.getInetAddress()+" on port "+portNumber);
		OutputThread recieve = new OutputThread(clientSocket);
		Thread thread = new Thread(recieve);
		thread.start();
		InputThread send = new InputThread(clientSocket);
		Thread thread2 = new Thread(send);
		thread2.start();
      }
    });
	
        }
    }
    
    
    class OutputThread implements Runnable
{
	Socket socket=null;
	BufferedReader brBufferedReader = null;
	
	public OutputThread(Socket clientSocket)
	{
		this.socket = clientSocket;
	}
	public void run() {
		try{
		brBufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));		
		
		String messageString;
		while(true){
		while((messageString = brBufferedReader.readLine())!= null){//assign message from client to messageString
			if(messageString.equals("EXIT"))
			{
				break;//break to close socket if EXIT
			}
                       output.setText(messageString);
		}
		this.socket.close();
		System.exit(0);
	}
		
	}
	catch(Exception ex){System.out.println(ex.getMessage());}
	}
    }
class InputThread implements Runnable
{
	PrintWriter printW;
	Socket socket = null;
	
	public InputThread(Socket s)
	{
		socket = s;
	}
	public void run() {
            
             send.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){

		try{
                        printW =new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		
			String msgToClientString = null;
			BufferedReader input1 = new BufferedReader(new InputStreamReader(System.in));
			
			msgToClientString=input.getText();
			printW.println(msgToClientString);
			printW.flush();
		
		}
		catch(Exception ex){System.out.println(ex.getMessage());}
                } 
            });
	}
        }

    
}


public class Server {
	public static void main(String[] args) throws IOException {
                S1 s=new S1();
	}}
