# Basic version of Conway's Game of Life in Java
This is a basic Java version of the [Conway's Game of Life](http://en.wikipedia.org/wiki/Conway's_Game_of_Life) that I develop during the [Code Retreat at Xebia France](http://www.duchess-france.org/retour-du-code-retreat-du-29-septembre-2012/) on 29 September 2012.

The "game" is a zero-player game, meaning that its evolution is determined by its initial state, requiring no further input. One interacts with the Game of Life by creating an initial configuration and observing how it evolves.

# Install
`mvn clean install`

# Run 
`mvn exec:java -Dexec.mainClass="net.diegolemos.gameoflife.GameOfLife"`
