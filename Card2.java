import java.util.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.math.*;
// check this out

/*  
    actual class for the card where most of the code is used
    to draw the actual card.
*/
public class Card2 {
  /* 
  -- declaring the constants and objects below 
  */
  private int r; // acts as the value of the card (Ace, 2-10, Jack, Queen, King)
  private int x; // standard x-position
  private int y; // standard y-position
  /* below three integers are the pixel sizes */
  private final int SIZE_X = 150;
  private final int SIZE_Y = 240;
  private final int SIZE = 100;
  private Graphics2D g;


  /*  
  Card "object"/class, takes in x and y as starting positions for the card
  to be drawn on. As well as Graphics
  */
  public Card2(int x, int y, Graphics2D g) {
    /* 
    Below is a random object that takes a value from 1 to 13
    and whatever it is it prints onto the card (however 1 will be an Ace.
    11 will be a Jack, 12 will be queen, and king would be 13). Next
    it will randomize the suit that's printed (spades,clubs,hearts,diamonds)
    */
    r = (int) (1 + (Math.random() * 13)); // actual random object defining the value
    String value = findValue(r); 
    String suite = findSuite();
    this.x = x;
    this.y = y;
    this.g = (Graphics2D) g;
    drawCard(suite, value);
  }


  // "main" method to draw the card, takes in the value and suite from strings

  public void drawCard(String suite, String value) {
    g.setColor(Color.BLACK);
    g.drawRect(x, y, 150, 240);
    
    if (suite.equals("\u2666") || suite.equals("\u2665")) { 
      // if the suit is a diamond or heart, the card will be red
      g.setColor(Color.RED);
    }
    // g.setStroke(new BasicStroke(50));
    g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
    // two lines below draw the suit and value top left corner
    g.drawString(value, x + 10, y + 30);
    g.drawString(suite, x + 10, y + 60);
    if (r >= 11) {
      drawRoyal(value);
    } else {
      int row = getRow();
      int spaceLeftx = 130;
      int spaceLefty = 120;
      int spacex = getSpacex(row, spaceLeftx);
      int num = getNum();
      int spacing = 0;
      if (num != 0) {
        spacing = spaceLefty / num;
      }
      int spacey = y + 60 + (spaceLefty / (num + 1)) - 7;
      int iterx = spacex;
      int itery = spacey;
      makeRows(num, suite, iterx, itery, row, spacey, spacing);
    }
    drawBottom(value, suite);
  }

  // draws the bottom right corner considering the upside-down condition
  // takes in the value and suite that will be printed
  public void drawBottom(String value, String suite) {
    AffineTransform at = new AffineTransform();
    g.setFont(new Font("TimesRoman", Font.PLAIN, 60));
    Font font  = new Font("TimesRoman", Font.PLAIN, 30);
    at.rotate(Math.toRadians(180),0,0);
    Font rotatedFont = font.deriveFont(at);
    g.setFont(rotatedFont); 
    g.drawString(value, x+140,y+210);
    g.drawString(suite,x+ 140,y+ 180);
  }

  /* next few methods implement voids to draw the patterns that
  will be seen in the middle of the card */
  public void makeSpecialSuites(String suite, int iterx, int itery, int spacing, int spacey){
    if(r == 1){
      g.drawString(suite, x+ 60,  y+ 120);
      // draws the plain suit in the middle of the card
      // if it's an ace
    }

    if(r==7 || r==8 || r==10) {
      g.drawString(suite, iterx-20 , spacey + spacing / 2);
    }


    if(r==5 || r==8 || r==10){
      itery -= spacing;
      System.out.println(itery);
      g.drawString(suite, iterx-20 , itery - spacing / 2); 
    }

    if(r== 9) {
      itery -= spacing;
      System.out.println(itery);
      g.drawString(suite, iterx-20 ,  itery - 3*spacing / 2);
    }
  }

  // prints the rows of the pattern when the value isn't an ace or any of the face cards
  public void makeRows(int num, String suite, int iterx, int itery, int row, int spacey, int spacing) {
    int flip = num - 1;
    System.out.println(num);
    AffineTransform old= g.getTransform();
    if (num > 1) {
      for (int i = 0; i < num; i++) {
        if(num >= flip) {
          g.rotate(Math.toRadians(180), iterx, itery);
          g.drawString(suite, iterx, itery);
          g.setTransform(old);
        } else {
          g.drawString(suite, iterx, itery);
        }
        itery += spacing;
        
      }
    }

    if (row > 1) {
      iterx = iterx + 40;
      itery = spacey;
      for (int i = 0; i < num; i++) {
        g.drawString(suite, iterx, itery);
        itery += spacing;
      }
    }
    makeSpecialSuites(suite, iterx, itery, spacing, spacey);
  }


  /* 
  Method that returns an integer, gets the starting position
  of a row (suits in the middle) based on the amount of rows
  */
  public int getSpacex(int row, int spaceLeftx) {
    int spacex;
    if (row == 2) {
      spacex = x + 10 + spaceLeftx / (2 * row);
    } else {
      spacex = x + spaceLeftx / 2 - 5;
    }
    return spacex;
  }

  /*Method that figure out number suit marks per row*/
  public int getNum() {
    int num = 0;
    if (r % 2 == 1) {
      num = (r - 1) / 2;
    } else {
      num = r / 2;
      if (r == 10) {
        num = 4;
      }
      if (r == 8) {
        num = 3;
      }
    }
    if (r < 4) {
      num = r;
    }
    return num;
  }

  public int getRow() {
    if (r <= 3) {
      return 1;
    }
    return 2;
  }


  /* 
  Below is a method that draws the "pattern" or face for the royal cards
  Jack, Queen, and King, does not return anything as it is a void method but
  it does take in a String parameter which is 'value' it determines what kind of
  face card it is.
  */
  public void drawRoyal(String value) {
    if (value == "J") {
      g.setFont(new Font("times", Font.BOLD, -1 * SIZE * 3 / 10));
      g.fillOval(x + 60, y + 100, SIZE / 3, SIZE / 3);
    } else if (value == "Q") {
      g.setFont(new Font("times", Font.BOLD, SIZE / 2));
      g.drawString("♛", x + 55, y + 100);
      g.fillOval(60 + x, 100 + y, SIZE / 3, SIZE / 3);
    } else {
      g.setFont(new Font("times", Font.BOLD, SIZE / 2));
      g.drawString("♚", x + 55, y + 100);
      g.fillOval(x + 60, y + 100, SIZE / 3, SIZE / 3);
    }
  }

  /* 
  Below method finds the value and returns a string.
  It is for the face cards. Takes in an integer that was randomized in
  the actual card object
  */
  public static String findValue(int r) {
    String value = Integer.toString(r);
    if (r == 1) {
      value = "A"; // Ace
    } else if (r == 11) {
      value = "J"; // Jack
    } else if (r == 12) {
      value = "Q"; // Queen
    } else if (r == 13) {
      value = "K"; // King
    }
    return value;
  }


  // below method is used to find the suit of the card
  public static String findSuite() {
    int s = (int) (Math.random() * 4); //  1 in 4 chance
    String suite;
    if (s == 0) {
      suite = "\u2666"; // Diamond
    } else if (s == 1) {
      suite = "\u2665"; // Heart
    } else if (s == 2) {
      suite = "\u2660"; // Spade
    } else {
      suite = "\u2663"; // Club
    }
    return suite;
  }
}