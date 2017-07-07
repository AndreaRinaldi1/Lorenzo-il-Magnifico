# Lorenzo il Magnifico - The Game


## Game Start
In order to start playing the game, firsly, you have to run the Server class that you can find inside the server package.
Secondly, run the Client interface that you find in the client package to create a new player. 

The game initialization proceeds as follows:
when a new player connects to the server, if there is not a game in the launching phase, then a new game is created, otherwise, the new player joins the starting game. The game starts as soon as 4 players connect. When the second player connects, a timer is scheduled with a timeout of 60 seconds. If the 4 players threshold is not reached by the expiration of the timer, the game starts with the connected players.

When a new player is created, you will be asked which type of networking interface, between RMI and Socket, you would like to use. 
After that, it's time to customize your player: you should provide the player Name and Color. 
Finally, you have to decide which personalized bonus tile (i.e. the thin tab placed next to the player board) you prefer.

### Clone and push the template to your repo
Using the git command line client for your OS, type the following commands:
```bash
 # clone the repo on your current folder, naming the remote as 'template'
 git clone https://github.com/deib-polimi/prova-finale-template --origin template
 # move to the cloned repo
 cd prova-finale-template/
 # add your repository as 'origin' (default) remote
 git remote add origin https://github.com/YOUR_USERNAME/YOUR_REPO_NAME
 # push the template project to your github repository, setting
 git push --set-upstream origin master
 # alternatively, if you already have some content in your repo (e.g., a README)
 # and YOU WA  NT TO OVERWRITE IT, force the push
 git push --force --set-upstream origin master

```
then, you can safely remove the 'template' remote by typing `git remote rm template`.

### Customize your project files and Import them in Eclipse
- Open the `pom.xml` file in a text editor and substitute the two occurrences of **pcXX** with your assigned **team_code**.
- Import it in Eclipse as Maven Project:
  * from Eclipse, select `File > Import... > Existing Maven Project`
  * click `Browse...` and select the directory where you cloned the project
  * make sure the project is listed and selected under `Projects`
  * select `Finish`
  * you should now see the project **team_code** listed in the Package Explorer view of Eclipse
- from the Package Explorer view, rename packages under `src/main/java` and `src/test/java` substituting **pcXX** with your assigned **team_code**
- customize the `README.md`
- in order to check that everything worked fine, try to build with Maven:
  + from Eclipse (Package Explorer view):
    * right-click on the project
    * select `Run as > Maven build...`
    * type `clean package` into the `Goal` field
    * click `Run`
  + from command line:
    * move to your project directory (you should be in the same folder as `pom.xml` file)
    * type `mvn clean package`
  + wait for the build to complete and make sure you have a build success

### Commit and push your changes:
  ```
  git commit -am "customize project"
  git push origin master
  ```
