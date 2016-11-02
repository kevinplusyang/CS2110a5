package gui;

import controller.Controller;
import java.util.Scanner;

import controller.Controller;
import model.Game;
import model.Location;
import model.Player;

/**
 * Created by kevinyang on 11/1/16.
 */
public class GUIController extends Controller{

    public GUIController(Player me) {
        super(me);
    }

    int x = -1;
    int y = -1;
    int tempx = -1;
    int tempy = -1;

    public void setLocation(int newX, int newY){
        x = newX;
        y = newY;
    }


    @Override
    protected Location nextMove(Game g) {

        while (true) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

           

                if(x != -1 && y != -1 && g.getBoard().get(x, y) == null){
                    //tempx = x;
                	
                	int row = x;
                	int col = y;
                    //tempy = y;
                	x = -1;
                    y = -1;
                    return new Location(row, col);
                }
//                    System.out.println("MMM:");
                

            
            x = -1;
            y = -1;
        }
    }
}
