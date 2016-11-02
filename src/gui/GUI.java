package gui;



import javafx.scene.control.ComboBox;
import model.NotImplementedException;

import javax.swing.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import controller.Controller;
import controller.DumbAI;
import controller.RandomAI;
import controller.SmartAI;
import model.Game;
import model.Player;

import controller.*;
import model.*;
import model.Board.State;
import controller.*;
import model.*;
import model.Board.State;

/**
 * Created by kevinyang on 11/1/16.
 */


/* NetId(s): djg17, ret87. Time spent: hh hours, mm minutes. */


        import javafx.scene.control.ComboBox;
        import model.NotImplementedException;

        import javax.swing.*;
        import java.awt.*;
        import javax.swing.*;
        import java.awt.event.*;
        import javax.swing.event.*;

        import java.awt.BorderLayout;
        import java.awt.Color;
        import java.awt.Dimension;
        import java.awt.Graphics;
        import java.awt.GridLayout;
        import java.awt.event.MouseEvent;
        import java.awt.event.MouseListener;

        import controller.Controller;
        import controller.DumbAI;
        import controller.RandomAI;
        import controller.SmartAI;
        import model.Game;
        import model.Player;

        import controller.*;
        import model.*;
        import model.Board.State;
        import controller.*;
        import model.*;
        import model.Board.State;



public class GUI extends JFrame implements GameListener {

    public Game g;
    public  Controller playerX = new GUIController(Player.X);
    //public  Controller playerX = null;
    public  Controller playerO = new GUIController(Player.O);
    //public Controller playerO = null;
    private JComboBox player1Mode = new JComboBox();
    private JComboBox player2Mode = new JComboBox();
    JLabel player1Label= new JLabel("Mode of Player 1");
    JLabel player2Label= new JLabel("Mode of Player 2");
    JButton start = new JButton("Start Game");

    public class drawBoard extends JPanel implements MouseListener{

        private final int row;
        private final int col;
        private int currentPlayer;


        public drawBoard(int i, int j) {
            currentPlayer = 0;

//            if(g.nextTurn() == Player.X){
//                currentPlayer = 1;
//            }else if(g.nextTurn() == Player.O){
//                currentPlayer = 2;
//            }
            row = i;
            col = j;

            setPreferredSize(new Dimension(50,50));
            addMouseListener(this);
        }

        public void toggle() {
//            this.selected = !this.selected;

            int row = this.getY()/50;
            int col = this.getX()/50;

            System.out.println("Clicked:");
            System.out.println(row);
            System.out.println(col);
//            ((GUIController)playerX).setLocation(row,col);

//            System.out.println(g.nextTurn());

            if(g.nextTurn() == Player.X && playerX instanceof GUIController){
                ((GUIController)playerX).setLocation(row,col);
                System.out.println("X");
            }

            if(g.nextTurn()== Player.O && playerO instanceof GUIController){
                ((GUIController)playerO).setLocation(row,col);
                System.out.println("O");
            }


            repaint();
        }


        public void delay() {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }

        public @Override void paint(Graphics x) {
            if ((row + col) % 2 == 0)
                x.setColor(Color.GRAY);
            else
                x.setColor(Color.white);

            x.fillRect(0, 0, getWidth()-1, getHeight()-1);

            x.setColor(Color.BLACK);
            x.drawRect(0, 0, getWidth()-1, getHeight()-1);

            if (g.getBoard().get(row,col) == Player.X) {
                x.setColor(Color.PINK);
                x.fillArc(0, 0, getWidth()-1, getHeight()-1, 0, 360);
            }
            if (g.getBoard().get(row,col) == Player.O) {
                x.setColor(Color.GREEN);
                x.fillArc(0, 0, getWidth()-1, getHeight()-1, 0, 360);
            }



            x.setColor(Color.BLACK);
            x.drawString("(" + row + ", " + col + ")", 20, 20);


            if(g.getBoard().getState() == State.HAS_WINNER){

                if(g.getBoard().getWinner().line.contains(row, col)){
                    x.setColor(Color.YELLOW);
                }else{
                    if ((row + col) % 2 == 0)
                        x.setColor(Color.GRAY);
                    else
                        x.setColor(Color.white);
                }



                x.fillRect(0, 0, getWidth()-1, getHeight()-1);

                x.setColor(Color.BLACK);
                x.drawRect(0, 0, getWidth()-1, getHeight()-1);

                if (g.getBoard().get(row,col) == Player.X) {
                    x.setColor(Color.PINK);
                    x.fillArc(0, 0, getWidth()-1, getHeight()-1, 0, 360);
                }
                if (g.getBoard().get(row,col) == Player.O) {
                    x.setColor(Color.GREEN);
                    x.fillArc(0, 0, getWidth()-1, getHeight()-1, 0, 360);
                }



                x.setColor(Color.BLACK);
                x.drawString("(" + row + ", " + col + ")", 20, 20);
            }
        }


        @Override
        public void mouseClicked(MouseEvent e) {
            toggle();
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
//            toggle();
        }

        @Override
        public void mouseExited(MouseEvent e) {
//            toggle();
        }


    }

    boolean hasClickedStart = false;
    JPanel center = new JPanel();
    
    public GUI(Game newG) {

        g = newG;
        hasClickedStart = false;

        JFrame game = new JFrame();

        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        center.setLayout(new GridLayout(9, 9));
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                center.add(new drawBoard(i, j));

        game.add(center, BorderLayout.WEST);


        JPanel eastPanel = new JPanel();

        JPanel player1 = new JPanel();
        JPanel player2 = new JPanel();

        player1.setLayout(new BoxLayout(player1, BoxLayout.X_AXIS));
        player2.setLayout(new BoxLayout(player2, BoxLayout.X_AXIS));

        player1.add(player1Label);
        player1.add(player1Mode);


        player1Mode.addItem("Human");
        player1Mode.addItem("DumbAI");
        player1Mode.addItem("RandomAI");
        player1Mode.addItem("SmartAI");
        player1Mode.addActionListener(new ComboXListener());

        player2.add(player2Label);
        player2.add(player2Mode);


        player2Mode.addItem("Human");
        player2Mode.addItem("DumbAI");
        player2Mode.addItem("RandomAI");
        player2Mode.addItem("SmartAI");
        player2Mode.addActionListener(new ComboOListener());

        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));

        eastPanel.add(player1);
        eastPanel.add(player2);
        eastPanel.add(start);

        game.add(eastPanel, BorderLayout.EAST);

        startButtonListener startListener = new startButtonListener();
        start.addActionListener(startListener);

        game.pack();
        game.setVisible(true);


    }


    private class ComboXListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(player1Mode.getSelectedItem() == "Human"){
                playerX = new GUIController(Player.X);
                System.out.println("Created HH");
            }
            if(player1Mode.getSelectedItem() == "DumbAI"){
                playerX = new DumbAI(Player.X);
                System.out.println("Created HD");
            }
            if(player1Mode.getSelectedItem() == "RandomAI"){
                playerX = new RandomAI(Player.X);
                System.out.println("Created HR");
            }
            if(player1Mode.getSelectedItem() == "SmartAI"){
                playerX = new SmartAI(Player.X);
                System.out.println("Created HS");
            }
        }
    }

    private class ComboOListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(player2Mode.getSelectedItem() == "Human"){
                playerO = new GUIController(Player.O);
                System.out.println("Created HH");
            }
            if(player2Mode.getSelectedItem() == "DumbAI"){
                playerO = new DumbAI(Player.O);
                System.out.println("Created HD");
            }
            if(player2Mode.getSelectedItem() == "RandomAI"){
                playerO = new RandomAI(Player.O);
                System.out.println("Created HR");
            }
            if(player2Mode.getSelectedItem() == "SmartAI"){
                playerO = new SmartAI(Player.O);
                System.out.println("Created HS");
            }
        }
    }

    
    private class startButtonListener implements ActionListener {
        // Process a click of a button
        public void actionPerformed(ActionEvent e) {
            System.out.println(player1Mode.getSelectedItem());
            System.out.println(player2Mode.getSelectedItem());

            hasClickedStart = true;

/*

            if(player1Mode.getSelectedItem() == "Human"){
				playerX = new GUIController(Player.X);
                System.out.println("Created HH");
            }
            if(player1Mode.getSelectedItem() == "DumbAI"){
                playerX = new DumbAI(Player.X);
                System.out.println("Created HD");
            }
            if(player1Mode.getSelectedItem() == "RandomAI"){
                playerX = new RandomAI(Player.X);
                System.out.println("Created HR");
            }
            if(player1Mode.getSelectedItem() == "SmartAI"){
                playerX = new SmartAI(Player.X);
                System.out.println("Created HS");
            }

            if(player2Mode.getSelectedItem() == "Human"){
				playerO = new GUIController(Player.O);
                System.out.println("Created OH");
            }
            if(player2Mode.getSelectedItem() == "DumbAI"){
                playerO = new DumbAI(Player.O);
                System.out.println("Created OD");
            }
            if(player2Mode.getSelectedItem() == "RandomAI"){
                playerO = new RandomAI(Player.O);
                System.out.println("Created OR");
            }
            if(player2Mode.getSelectedItem() == "SmartAI"){
                playerO = new SmartAI(Player.O);
                System.out.println("Created OS");
            }
*/

        }
    }

    
    public void delay() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
    }

    

    @Override
    public void gameChanged(Game g) {
    	Object[] comps = center.getComponents();
    	System.out.println("baobao "+comps.length);
        System.out.println("Changed");
        System.out.println(comps[0].getClass());
        for (int i = 0; i < 9; i++) {
        	for (int j = 0; j < 9; j++) {
        		((drawBoard) comps[i*9+j]).repaint();
        	}
        }
        
        delay();
        
        for (int i = 0; i < 9; i++) {
        	for (int j = 0; j < 9; j++) {
        		((drawBoard) comps[i*9+j]).repaint();
        	}
        }


        switch(g.getBoard().getState()) {
            case HAS_WINNER:
                System.out.println(g.getBoard().getWinner().winner + " wins!");
                System.out.print(g.getBoard().getWinner().line);
                break;
            case DRAW:
                System.out.println("Game ended in a draw!");
                break;
            case NOT_OVER:
                System.out.println("It is " + g.nextTurn() + "'s turn");
                break;
        }


    }


}
