=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: _______
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  While I didn't get direct feedback on my proposal, I did remove the Inheritance part design feature because it ended up being unnecessarily complicated (I still had 4 extra design features). I also added extra Collections and made my File I/O system slightly more elaborate. 


  1. 2D Arrays 
  I use 2D arrays in my Grid class, to store the current state of the well (the squares that are "stuck" to the grid). Each Grid has a 2D Color array called well, where each entry well[x][y] is some Color c if that square is colored and null if the square is empty. I have 3 Grid objects in my Board class, one to represent the main playable grid, one to display the next shape, and one to display the saved shape. 

  This choice appropriate because the shape of a rectangular 2D array closely resembles the shape of a grid. My Piece class has a function that returns an array of the piece's 4 coordinate pairs. Setting up my grid as a 2D array makes it very easy to check for a collision between the piece and the grid's well because for each of the piece's 4 coordinate pairs (x,y), I just have to check if entry [x][y] in the 2D well array contains a color or not to see if they collide. 

  2D arrays are also appropriate because it makes it easy to display the graphics of the well. Since each entry already stores its necessary color, in my drawWell function, I just need to iterate through each position to know what color to display. 

  These 2D arrays are properly encapsulated within the Grid class and can only be altered through the setSquareColor() function. The getWell() function within the grid class returns a copy of the 2D array without any references to the original, so any alterations to the array returned will not alter the grid's 2D array state. 
  
                 
  2. Collections
  I use Java Collections in two main places in my Tetris game. First, I use a LinkedList of Pieces to store the order that the pieces will spawn on the board. This is appropriate because it makes it easy to manage the nextPiece display and the savedPiece display. The nextPiece display will always display the head of the LinkedList, and whenever that piece ends up on the board, the nextPiece display will automatically display the next shape. Additionally, when I stick the current piece to the board and get rid of it, I call the remove() function to get the head of the list of next pieces. The savedPiece functionality, which allows a user to press Shift to save the current piece, also works well with the linkedlist because I can either swap the current piece with the saved piece or save the current piece and pull the next from the head of the linkedlist relatively easily. 

  Next, I use Java Collections for displaying scores. My game allows users to click a "Score History" button to see all of a given user's past scores. After each game, a user is prompted to input their username, which defaults to ANONYMOUS_USER if they do not enter anything, and this username and score is written to the Tetris_Scores.txt file. I have a getScoreMap() function in my board class that processes the data in the txt file to create a Map from a String username to a LinkedList<Integer> of scores. Using a LinkedList<Integer> to store a given user's scores is convenient because it doesn't have a specific size and it allows me to add scores with ease. Additionally, it preserves the order the scores are written into the file, so that the user can see the scores in  chronological order. 

  Using a TreeMap that maps from the String username to the list of scores is also appropriate because it allows me to access a user's scores with just a username. In the dialog box asking the player to choose which user's scores they want to view, the TreeMap implementation is also very convenient because I can get the keySet of String keys in the TreeMap to obtain the set of usernames that the player can pick from (these users are displayed in the dropdown menu). 
  

  3. File I/O 
  I use File I/O to store a list of usernames and scores that the game can access. This is appropriate because it stores a running list of all usernames and scores as opposed to resetting every time the game is run. After a game over, the player is prompted to enter their username into a dialog box. Only alphanumeric characters are accepted, and if the user enters an invalid name, they will get an error message and will be asked to input again. If they leave it blank and click okay/cancel, it will record their name as ANONYMOUS_USERNAME. After inputting the name, the game writes their name and score to the specified score filename, using a space as a delimiter separating the two. 

  There is also a "Get Score History" button at the top of the screen, next to the New Game and Pause buttons, that allows the user to select one of the usernames in the score file and view that person's score history, in chronological order. When the player clicks that button, the code reads the score file to create a Map of usernames to lists of scores. It also uses that map to generate a list of usernames to select from, which displays in the dropdown menu. When a player selects a username, a dialog box is opened that contains a list of that users scores, from oldest to newest. If there are any issues with the score file (i.e. it contains no readable scores or the scores are formatted incorrectly), it will provide a corresponding dialog error box. 
  

  4. Testable
  I have based my Tetris implementation on the MVC model. I made a helper Point class that I use throughout my code to represent coordinates and positions. I also made a Piece class and a Grid class that store all of the data necessary to represent grids and pieces. The Piece constructor takes in a type (from the Piece.Types enum) and an initial position. Based on this type, the orientation/color/rotation fields are altered accordingly. The Piece class has many different functions associated with shifting or rotating a piece, and these change the fields accordingly. All fields are encapsulated and can only be altered using these methods, and the getGridCoords() method returns copies of the grid's coordinates, such that altering the returned coordinates will not change the actual shape's coordinates. The invariants associated with what position or orientation a shape of a certian type can take on are preserved by these methods. 

  The Grid class is slightly less complex, but its main functions have to do with coloring certain squares in the well, sticking a piece to the well, or returning the position/dimensions of the grid. All of these are properly encapsulated, and all of the get functions return copies of private fields rather than references to the original, such that altering the returned objects will not change the grid's private fields. 

  The Board class, which extends JPanel, represents the model of my game, with a draw function that draws the necessary components. It contains functions associated with piece rotation, piece shifting, detecting the direction of well collisions and wall collisions, sticking pieces to the board, and detecting whether the game is over or paused. The tick() function is the heartbeat of the game as every time it is called, the current piece is shifted down. The way I have set up the Board class, I can basically model an entire game using the tick() method in conjunction with other methods. My JUnit tests test functions in all three classes, and checks to make sure that all of the functions in the Board class accurately change the game state. 
  

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.
  
The smallest class is the Point class, which is a helper class I made for myself to represent coordinates and dimensions more easily. It has basic functions that allow you to get the first or second integer of the point, and has other functions related to shifting the point. 

The Grid class represents a grid, and its main function is to store a 2D Color array called the well, which represents whether or not a square on the grid is filled with a color or is empty. It also has methods associated with sticking a given piece onto the grid, clearing the well, and setting a square to a different color. The main reason I needed a grid class is because I had 3 main grid-like things I needed to display on my board: the main playable grid that uses most of the grid functions, and the 2 grids displaying the nextPiece and savedPiece. 

The Piece class represents a Tetris piece. It takes in a type, specific by the Piece.Types enum, and a position.  The type the Piece constructor is given dictates the 4 possible orientations (which I entered into my code by hand after watching how shapes rotate in actual Tetris). The actual shapes in Tetris don't seem to rotate with any specific algorithm, so I had the rotateCW() and rotateCCW() functions basically iterate through an array of 4 possible orientations. The Piece class has other functions that shift the piece left, right, down, or up, and a last function that uses the current position and orientation to return a set of grid coordinates. 

The Board function is the main model of the game. It stores a list of next pieces, a grid corresponding to the main playable grid, the current piece, the saved piece (if any), a list of next pieces, and grids that display the current and next pieces. It has functions that check for well and wall collisions, move the piece, respond to keyPressed events, and check the state of the game (whether it is over, paused, or reset). The tick() function is what drives the game forward, and each time tick is called, the current piece on the board either moves down or sticks to the board (if there is a downward well/wall collision). 

The Game class extends Runnable and handles the main JFrame and most of the corresponding dialog boxes. It controls the general layout of the Board and other buttons and displays the game status JLabel at the very bottom of the green (which is altered by the Board class as necessary). 


- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?

  One major stumbling block was figuring out how exactly to represent pieces. The piece rotations in tetris are so complicated and counter-intuitive at times that it took me such a long time to figure out how to represent them. I finally settled on manually inputting the 4 different orientations a piece can have relative to a "point of origin" that Tetris pieces seem to rotate around (but even this differs from shape to shape). This part took me so long, and I was staring at Tetris/drawing diagrams of shapes for so long before I finally created an implementation I was OK with. 

  Another stumbling block was creating the grid class. I made the grid class after I had already created a working version of my game without it, and it took me longer to create the class, model grids in my code using the class, and debug the errors than it did to write my first working version of the game.  



- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

  I think there is a pretty good separation of functionality between the grids, pieces, and the board. It makes sense to me. I'm not sure if it is overkill to have an entire grid class, but it was helpful in storing a position as well as a 2D array.  

  Private state encapsulation is pretty much everywhere I need it, as I've described in my Testable description. 

  The main thing I would refactor is making the Grid and Piece classes extend JComponent or JPanel and adding them to the Board JPanel rather than selecting x and y positions for them to display. I tried this while I was writing my code, but it led to so many unexpected bugs and display errors that I didn't understand the cause of. I was able to get it partially working, but not fully, so this is the main thing I would alter to make my code design more natural and the layout more automatic. 



========================
=: External Resources :=
========================

- Cite any external resources (libraries, images, tutorials, etc.) that you may
  have used while implementing your game.

  I looked at the JavaDocs for Graphics and figured out how to change things like font size and background color. 
  I also read through the Wikipedia entry on Tetris and played the original Tetris online to figure out the patterns of shape rotation and game controls. 
