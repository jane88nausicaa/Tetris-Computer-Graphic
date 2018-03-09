# Game of Tetris (Computer Graphic)

## Overview
This project is to create an application of the Game of Tetris, as shown in the following figure. 

<img width="646" alt="tetris" src="https://user-images.githubusercontent.com/37192026/37186201-1469fb4e-22f9-11e8-8ab0-55bd147504b5.png">

The game involves playing with different shapes. 
<img width="720" alt="img" src="https://user-images.githubusercontent.com/37192026/37186245-4be69cf8-22f9-11e8-95ca-3753ed0380e5.png">

## Functionality
- If the mouse cursor moves inside the main area, `PAUSE` (in a large font) will be displayed; and if the cursor moves out of the area, `PAUSE` will disappear. 

- Change of the window dimension will only possibly change the size, but not the relative position and aspect ratio of any component.

- If the button `QUIT` is pressed, the program terminates and quits (this should not be the quit from the window’s standard pull-down menu). 

- Randomly select one of the 10 shapes as shown above to be displayed in the center at the top of `Main area` and randomly select a different shape to be displayed in `Next shape`. The starting orientation of the shape can be fixed. 

- The shape at the top of `Main area` moves down (falls) at a constant speed. Once the shape’s lowest edge touches the bottom of `Main area` or the top edge of another shape, it stops moving and stays there. 

- When the cursor is outside of `Main area`, each click on the left mouse button moves the falling shape to the left by one square, and similarly, the right button moves the shape to the right by one square. A forward scroll of the mouse wheel will rotate the shape clockwise and a backward scroll will rotate the shape counter-clockwise. If the mouse cursor moves inside `Main area`, the falling stops, i.e. a pause. 

- Add the following constants which could be adjusted as needed in the indicated ranges: 
  - M – scoring factor (range: 1-10).
  - N – number of rows required for each Level of difficulty (range: 20-50).
  - S – speed factor (range: 0.1-1.0).
  
- Relationship among `M`, `N`, and `S`: 
  - When any horizontal row of squares `R` has no hole, i.e. all the squares in `R` are parts of some shapes with colors, `R` is removed and all the rows above `R` move one square down, `Lines = Lines + 1`; `Score = Score + Level x M`. 
  - If the number of removed rows in the current `Level `reaches `N`, `Level = Level + 1`, the falling speed `FS = FS x (1 + Level  x S)`.
  
- When a new shape has no space to fall, i.e. existing shapes in `Main area` pile up to near the top, the game terminates.

- If the cursor is inside the falling shape `F` (in `PAUSE` mode), `F` will be changed to one of the shapes different from `F` and that currently inside `Next shape`, `Score = Score - Level  x M`. We should use `Point-Inside-Polygon` test algorithm to detect the cursor.

- Create a user-friendly interface so that various parameters could be adjusted, using `GUI` widgets,  to suit different user groups:
  - Constants `M`, `N`, and `S` are individually adjustable.
  - The width and height of `Main area` can be adjusted (beyond 10 x 20 squares).
  - The size of the square is adjustable (e.g. enlarged for elderly players).
