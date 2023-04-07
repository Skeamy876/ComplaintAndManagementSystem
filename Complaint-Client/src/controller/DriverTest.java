package controller;

import views.LoginView;

import javax.swing.*;

public class DriverTest {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    // Set System L&F
                    UIManager.setLookAndFeel(
                            UIManager.getSystemLookAndFeelClassName());
                }
                catch (UnsupportedLookAndFeelException e) {
                    // handle exception
                    System.err.println("Unsupported Look and Feel" + e.getMessage());
                }
                catch (ClassNotFoundException e) {
                    // handle exception
                    System.err.println("Class Not Found"+ e.getMessage());
                }
                catch (InstantiationException e) {
                    // handle exception
                    System.err.println("Instantiation Exception"+ e.getMessage());
                }
                catch (IllegalAccessException e) {
                    // handle exception
                    System.err.println("Illegal Access Exception"+e.getMessage());
                }
                new LoginView();
            }
        });
    }



}
