// Programming pals
// period 2

/* 
This is the playing-card project, which is a building block to the poker game.
Similar to the CafeWall, 

*/
// Vincent again....

// importing tools that will be used to make certain elements run
// (drawingPanel/Graphics2D especially)
import java.util.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.math.*;

class Main {
  public static void main(String[] args) {
    
    /* 
    -- getting and naming drawingpanel and the graphics used 
    -- for the cards to be drawn on
    -- test
    */
    DrawingPanel d= new DrawingPanel(1000, 1000);
    Graphics2D g = (Graphics2D)d.getGraphics();

    // v initializes the cards from a different class
    Card2 card1 = new Card2(0, 0, g);
    Card2 card2 =  new Card2(300, 0, g);
    Card2 card3 =  new Card2(0, 300, g);
    Card2 card4 = new Card2(300, 300, g);
  }
  
}