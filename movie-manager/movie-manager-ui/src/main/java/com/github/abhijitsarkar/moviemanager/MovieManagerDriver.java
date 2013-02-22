/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.abhijitsarkar.moviemanager;

import com.github.abhijitsarkar.moviemanager.ui.MainFrame;

/**
 *
 * @author abhijit
 */
public class MovieManagerDriver {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        new MainFrame().setVisible(true);
    }
}
