# Langton-Ant-Multithreaded
Java Implementation of Langton's Ant with multithreading

First try at adding multithreading to a program and I am pretty happy with the results!
Ants act strange when running fullspeed but I believe when "Thread.Sleep(1)" is called the ants act normally to their respective rules
Program allows for lots and lots of ants to move on the screen at once! It is quite fun to watch as the do sometimes fight each other for territory! There are still some things that i'd like to fix but as of now I am quite pleased with the progress of this small project! Also very cool art can be created with this!

*Update* June 29th
- Fixed UI freezing bug during excecution of simulation
- Added support to stop and clear the screen (seperate buttons now!)
- Added support to have a custom background color
- Program now runs in windowed fullscreen when launched

# Result images!

Example 1
![Screenshot_74](https://user-images.githubusercontent.com/62959991/176577016-188e8302-2ee9-4ebb-8565-32a59a8ab5a3.png)
Example 2
![Screenshot_70](https://user-images.githubusercontent.com/62959991/175842201-c05b7473-c340-47d6-85d6-e66f5f3bb572.png)
Example 3
![Screenshot_69](https://user-images.githubusercontent.com/62959991/175842200-fdd2218b-5d33-4d5d-97c2-3339984be9b0.png)
Example 4
![Screenshot_71](https://user-images.githubusercontent.com/62959991/175842203-f8bab05c-6b75-4ddb-bd1c-6d96421b1391.png)

# More about Langton's Ant!
Langton's ant is a two-dimensional universal Turing machine with a very simple set of rules but complex emergent behavior. It was invented by Chris Langton in 1986 and runs on a square lattice of black and white cells. The universality of Langton's ant was proven in 2000. The idea has been generalized in several different ways, such as turmites which add more colors and more states.
Squares on a plane are colored variously either black or white. We arbitrarily identify one square as the "ant". The ant can travel in any of the four cardinal directions at each step it takes. The "ant" moves according to the rules below:

# Rules
At a white square, turn 90° clockwise, flip the color of the square, move forward one unit
At a black square, turn 90° counter-clockwise, flip the color of the square, move forward one unit
Langton's ant can also be described as a cellular automaton, where the grid is colored black or white and the "ant" square has one of eight different colors assigned to encode the combination of black/white state and the current direction of motion of the ant.

# Modes of behavior
These simple rules lead to complex behavior. Three distinct modes of behavior are apparent, when starting on a completely white grid.
Simplicity. During the first few hundred moves it creates very simple patterns which are often symmetric.
Chaos. After a few hundred moves, a large, irregular pattern of black and white squares appears. The ant traces a pseudo-random path until around 10,000 steps.
Emergent order. Finally the ant starts building a recurrent "highway" pattern of 104 steps that repeats indefinitely.
All finite initial configurations tested eventually converge to the same repetitive pattern, suggesting that the "highway" is an attractor of Langton's ant, but no one has been able to prove that this is true for all such initial configurations. It is only known that the ant's trajectory is always unbounded regardless of the initial configuration – this is known as the Cohen-Kong theorem.

Animation of first 200 steps of Langton's ant.

![LangtonsAntAnimated](https://user-images.githubusercontent.com/62959991/176578549-87c6aeba-712f-4ac0-9b73-ee1bc0a1658b.gif)


Source: https://en.wikipedia.org/wiki/Langton%27s_ant



