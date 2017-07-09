# Lorenzo il Magnifico - The Game


## Game Initialization
In order to start playing the game, firstly, you have to run the Server class that you can find inside the server package.
Secondly, run the Client interface that you find in the client package to create a new player. 

The game initialization proceeds as follows:
when a new player connects to the server, if there is not a game in the launching phase, then a new game is created, otherwise, the new player joins the starting game. The game starts as soon as 4 players connect. When the second player connects, a timer is scheduled with a timeout of 60 seconds. If the 4 players threshold is not reached by the expiration of the timer, the game starts with the connected players.

When a new player is created, you will be asked which type of networking interface, between RMI and Socket, you would like to use. 
After that, it's time to customize your player: you should provide the player Name and Color. 
Lastly, all the players will have to decide - in reverse order of the order of play - which personalized bonus tile (i.e. the thin tab placed next to the player board) they prefer, among the available ones.

## Game Representation
On the console it is now shown the graphic (CLI) representation of the game board and the player board for each player respectively. 
The Game Board comprises:
- The four towers, one for each type of development card (e.g. Territory, Character, Building, Venture). Into the four cells of each tower the name of the card is shown. Otherwise, if the card has already been taken through a family member, the color of the corresponing player is shown, while if it has been taken as an effect of another card the cell will be empty.
- The Council Palace, with a list of the players (identified with name and color) in there.
- The set of spaces (every space, if occupied, shows the color(s) of the present player(s)):
  * Coin Space: the one that by default gives 5 coins.
  * Servant Space: the one that by default gives 5 servants.
  * Mixed Space: the one that by default gives 2 coins and 3 military points (blocked if there are not 4 players).
  * Privileges Space: the one that gives the player the possibility of choosing two (by default) different council privileges
  * Production Space: the one where players go into, through a production action. It is divided into the two production subspaces (if there are more than 2 players).
  * Harvest Space: the one where players go into, through a harvest action. It is divided into the two harvest subspaces (if there are more than 2 players).
- The three tracks representing the Faith, Military and Victory points of each player.

The Player board comprises:
- The development cards the player has been taking during the course of the game, divided according to their type.
- The player resources (i.e. coins, woods, stones and servants)
- The family members, associated to the respective dice value if they haven't been used yet, marked as "used" otherwise.

## Let's play!
Firstly, the current player will be asked what kind of action he/she would like to undertake. There are mainly four types of actions in this game. You can:
- Take a card from the available ones shown into the towers. You will, then, be asked the name of the card you want to take, the family member you want to exploit for this action and if you want to increment its value by paying servants. If you want to know the cost of a card, before deciding to take it, you can answer "askcost" to the first question.
- Go to one of the available spaces (listed above) on the game board and, just like before, you will be asked which family member you want to use and if you want to increment its value.
- In case you are not able to exploit any kind of action (e.g. you only have the neutral family member availabe and you don't have servants to increment its value) you can skip.
- Play, discard or activate a Leader card (this action is labelled as Special Action and it will be asked at the end of every turn anyway). After that, you will also be able to do an usual action, of course. Just like what happens with the action of taking a card, you can also ask what is the cost for playing a leader, by typing "askLeaderCost".

If you are hungry for more information, you can also have a look at the excommunications you received and the current status of you leaders, if they are either played or active, both, or any of the two.

At the end of every era, the players that will have achieved the minimum amount of faith points necessary to avoid the excommunication, will be asked if they prefer to receive it and remain in the achieved track position, or if they want to sacrifice their faith points and avoid the church excommunication.
Otherwise, the players that did not reach the minimum faith points, will receive the excommunication and maintain their poisition.

## Other information
- Every player has a maximum time for completing an action (5 min). If the timer expires before the player finished the action, it will not been applied. Moreover, the player will be prompted in a suspension state, from which he/she can return to play by typing reconnect. Until he/she does not reconnect the game will go on with the remaining players. Every player will be notified when a player has been suspended and, in case a player is still suspended when the game ends, he/she will be considered in the final points count anyway.
- The suspension state, from which a player can join the game again, differs from the actual disconnection of the client. Infact, if a player closes the console, he/she won't be able to start playin again in the game he/she left. Also in case of disconnection all the remaining players are notified of the event, and they can keep playing (unfortunately, if the abandoning player used RMI, the next player has to wait for the timer to expire, while for Socket, as soon as a player leaves the game, the following player can immediatly start his/her turn). 
- Every interaction between client and server is done through strings exchange.


HAVE FUN!
