import java.io.IOException;
import java.util.Date;
import java.util.Random;

import jerklib.ConnectionManager;
import jerklib.Profile;
import jerklib.Session;
import jerklib.events.IRCEvent;
import jerklib.events.IRCEvent.Type;
import jerklib.events.JoinCompleteEvent;
import jerklib.events.MessageEvent;
import jerklib.listeners.IRCEventListener;

public class BMain implements IRCEventListener
{
	private ConnectionManager manager;
    private boolean inTimeout = false;
    private Date timeoutTime = null;
    static HalD hallyberry;
    Random rng = new Random();
	public BMain()
	{

		manager = new ConnectionManager(new Profile("TimHortons"));
		Session session = manager.requestConnection("irc.installgentoo.com");
		session.addIRCEventListener(this);
 
	}

	public void receiveEvent(IRCEvent e)
	{
		if (e.getType() == Type.CONNECT_COMPLETE)
		{
			e.getSession().join("#installgentoo");
		}
		else if (e.getType() == Type.PRIVATE_MESSAGE)
		{
            String channel = ((MessageEvent) e).getChannel().getName();
            if(!channel.equalsIgnoreCase("#installgentoo.com"))
                return;

            if(inTimeout && timeoutTime != null) {
                Date now = new Date();
                long difference = now.getTime() - timeoutTime.getTime();
                int minutes = (int) ((difference / (1000 * 60)) % 60);
                if (minutes >= 15) {
                    inTimeout = false;
                    timeoutTime = null;
                }
                return;
            }

			MessageEvent me = (MessageEvent) e;
			String msg = me.getMessage();
			String[] margs = msg.split("[\\s,:]");

			try {
				hallyberry = new HalD();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			if(margs[0].equalsIgnoreCase("TimHortons"))
                if(margs[1].equalsIgnoreCase("hush")) {
                    String nick = ((MessageEvent) e).getNick();
                    if((nick.equalsIgnoreCase("ganye") || nick.equalsIgnoreCase("floby"))) {
                        timeoutTime = new Date();
                        inTimeout = true;
                        return;
                    }
                }
				e.getSession().getChannel("#installgentoo").say(hallyberry.plsRespond(margs[2]));
			if(rng.nextInt(10) == 1)
				e.getSession().getChannel("#installgentoo").say(hallyberry.plsRespond(msg));
		}
		else if (e.getType() == Type.JOIN_COMPLETE)
		{
			JoinCompleteEvent jce = (JoinCompleteEvent) e;
			jce.getChannel().say("Sorry I'm back now.");
		}
		else
		{
			System.out.println(e.getType() + " " + e.getRawEventData());
		}
	}
 
	public static void main(String[] args) throws IOException
	{
		
		new BMain();
	}
}