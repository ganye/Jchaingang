import java.io.IOException;
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
			MessageEvent me = (MessageEvent) e;
			String msg = me.getMessage();
			String[] margs = msg.split("[\\s,:]");

			try {
				hallyberry = new HalD();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			if(margs[0].equalsIgnoreCase("TimHortons"))
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