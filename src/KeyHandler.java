/**
 * Creates and controls all the mouse activity, including the appearance of the screen and clicking 
 * @author Semi Park, Michael Zhou, David Sun
 */
import java.awt.event.*;
import java.awt.*;

public class KeyHandler implements MouseMotionListener, MouseListener {


	public void mouseClicked(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent e)
	{
		Screen.store.click(e.getButton());
	}


	public void mouseReleased(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}


	public void mouseEntered(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}


	public void mouseExited(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}


	public void mouseDragged(MouseEvent e)
	{
		Screen.currentPoint = new Point ((e.getX())-((Frame.size.width-Screen.myWidth)/2), (e.getY())-((Frame.size.height-(Screen.myHeight))-(Frame.size.width - Screen.myWidth/2)));
	}


	//mouse cursor
	public void mouseMoved(MouseEvent e)
	{
		Screen.currentPoint = new Point ((e.getX()) - ((Frame.size.width - Screen.myWidth)/2), (e.getY()) - ((Frame.size.height - (Screen.myHeight)) - (Frame.size.width - Screen.myWidth)/2));
		
	}

	
	
}
