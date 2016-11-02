/* NetId(s): djg17, ret87. Time spent: hh hours, mm minutes. */

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




public class Main extends JFrame{

	public static void main(String[] args) {


			Game g = new Game();
			GUI gui = new GUI(g);

			// cause the board to be printed when it changes.


			while (!gui.hasClickedStart) {
				delay();
			}

			g.addListener(gui);
			// cause the players to play when it is their turn
			g.addListener(gui.playerX);
			g.addListener(gui.playerO);

		}



	protected static void delay() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
		}
	}
}











