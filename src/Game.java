import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import javax.swing.*;

public class Game implements Runnable {
    public void run() {
        //Get screen width and height
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        
        //Game frame
        final JFrame frame = new JFrame("Tetris");
        frame.setLocation((int) (width * 0.33), (int) (height * 0.1));

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Playing...");
        status_panel.add(status);

        // Main playing area
        //final GameCourt court = new GameCourt(status);
        final Board board = new Board(status); 
        frame.add(board, BorderLayout.CENTER);
        board.setBackground(Board.SQUARE_COLOR);

        // Control Panel
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);
        
        // Reset button
        final JButton reset = new JButton("New Game");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.reset();
            }
        });
        control_panel.add(reset);
        
        // Pause button
        final JButton pause = new JButton("Pause");
        pause.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.togglePause();
            }
        });
        control_panel.add(pause);
        
        // Score History button
        final JButton userHistory = new JButton("Get Score History");
        userHistory.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Map<String, LinkedList<Integer>> scoreMap = Board.getScoreMap();
                    Set<String> keys = scoreMap.keySet(); 
                    String[] users = Arrays.copyOf(keys.toArray(), keys.size(), String[].class); 
                    
                    if (users.length == 0) {
                        //If there are no users in the scoremap, show an error message
                        JOptionPane.showMessageDialog(board, "ERROR: No scores to display. ", 
                                "NO SCORES ERROR", JOptionPane.ERROR_MESSAGE);
                    } else {
                        //Otherwise, create a dialog with all past users as dropdown options
                        String name = (String) JOptionPane.showInputDialog(board,
                                "Select the name of the user whose score history you want to see:", 
                                "Select User", JOptionPane.QUESTION_MESSAGE, null, users, users[0]);
                        
                        //If the actually choose a name and do not click cancel, 
                        if (!(name == null)) {
                            //Use the scoremap to create a String list of the users scores
                            LinkedList<Integer> scoreList = scoreMap.get(name); 
                            String displayString = name + "'s scores:"; 
                            for (int i = 0; i < scoreList.size(); i++) {
                                String score = Integer.toString(scoreList.get(i)); 
                                displayString += "\n" + score; 
                            }
                            
                            //Display the user's scores in a dialog box
                            JOptionPane.showMessageDialog(board, displayString, 
                                    name + "'s Score History", JOptionPane.PLAIN_MESSAGE);
                        }
                    }
                } catch (IOException e1) {
                    //Display an error messsage
                    JOptionPane.showMessageDialog(board, "ERROR: There was a file error. Could not "
                            + "display scores.", "FILE IO ERROR", 
                            JOptionPane.ERROR_MESSAGE);
                } catch (NumberFormatException e2) {
                    //Display an error messsage
                    JOptionPane.showMessageDialog(board, "ERROR: One or more score entries in the "
                            + "file: \n '" + Board.FILE + "' are formatted improperly. \n "
                            + "Could not display scores.", 
                            "FORMATTING ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        control_panel.add(userHistory);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start game
        board.reset();
        
        frame.getContentPane().setBackground(Color.black);
        frame.pack();
        frame.setVisible(true);

    }

    /**
     * Main method run to start and run the game. Initializes the GUI elements specified in Game and
     * runs it. IMPORTANT: Do NOT delete! You MUST include this in your final submission.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}
