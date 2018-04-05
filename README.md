<p align="center">
 <u><b>VERSION FRANCAISE</b></u>
</p>

# SensorPong

## Présentation du jeu
Sensor Pong est un jeu attractif et accessible à tous. Les partis sont rapide, elles durent seulement **15 secondes**.
Le but du jeu est de faire passer la balle de haut en bas de l\'écran et inversement sans toucher les bords droit et gauche. Pour cela il suffit d'incliner l'écran pour faire bouger la balle.


#### Compte des points
Le système de point est très simple :
- Vous marquez **100 points** en touchant alternativement le bord haut et bas de l'écran
- Vous perdez **1 points** à chaque fois que vous touchez les bords droit et gauche de l'écran. Votre score décroit tant que vous restez contre un de ces bords


#### Résultat
 A la fin d\'une partie, saisissez votre pseudo et consulter les trois premiers du classement (avant votre partie).
 Pour accéder au classement complet, il vous suffit d\'appuyer sur le `i` d\'information.
Pour rejouer cliquez sur le bouton `Replay`
Le bouton `Home` vous renvoit à la page d'accueil avec le classement actualisé

#### Déroulement d'une partie 
 - Le joueur lance la en cliquant sur le bouton `Jouer` sur la page d'accueil. 
 - Il joue la partie de SensorPong
 - Sur la page de résultat : il saisit son pseudo, consulte son score et son classement. Il a aussi accès au classement réduit et au classement complet.

## Fonctionnalité
L'utilisateur à accès à plusieurs fonctionnalités : 
- Sur l'écran d'accueil : il peut consulter le règlement ou jouer une partie
- Sur l'écran de résultat : il peut voir le classement complet, voir son propre classement, rejouer une partie ou retourner à la page d'accueil.

## Aspect techniques
- Nous avons décidé de bloquer l'application en mode portrait. Le but de l'application étant de faire passer la balle de haut en bas, cela aurait été plus facile de marquer des points en mode paysage et ce n'est pas le but que nous recherchions.
- L'application comporte 2 modèles : un pour le score (gestion de la partie de l'utilisateur) et un pour la gravité (gestion de la balle pendant une partie).
- Les deux modèles sont liés à leur ViewModel respectif.
- La base de données (SQLite) est en locale sur le téléphone de l'utilisateur. Elle comporte une seule table score. Cette table contient 4 attributs : un id, un score (celui de la partie jouée), un username (celui du joueur) et la date (celle du jour de la partie).

## Travail de groupe
Le projet c'est effectué en plusieurs phases : 
- Phase de réflexion : trouver l'idée de l'application et choisir le capteur qui sera utilisé
- Travail sur papier : définition des modèles utiles à l'application, de la table (et de ses attributs) présente dans la base de données, maquettes des interfaces
- Travail de développement : travail en open space à l'IUT et à domicile pour le développement de l'application
- Réunion : à chaque début d'après midi une mise au point a été faite pour confirmer le travail effectué et pour définir les tâches à effectuer lors de la prochaine phase de développement
- Outils utilisés : Git pour le partage du code en instantanné et Facebook lors du travail à domicile pour la communication. Android Studio pour le développement. Les smartphones personnels et des émulateurs pour les tests de déploiement.

## Evolutions envisagées
- Augmentation de la zone de perte de point au fil du temps.
- Avoir plusieurs niveau de difficulté : par rapport au temps de la partie, à la vitesse de la balle, etc...
- Pouvoir consulter le classement selon un laps de temps précis : la classement par jour, semaine, mois ou année.
- Pouvoir changer la musique de fond dans les options.
- Pouvoir réinitialiser la base de données pour effacer tous les scores.

## Crédit
La team Hibou est composé de :
- Gestas Vincent 
- Grulois Clément
- Lemarchand--Frémont Olivier

Et vous souhaite une bonne partie de Sensor Pong !

<p align="center">
 <u><b>ENGLISH VERSION</b></u>
</p>

# SensorPong

## Presentation of the game
Sensor Pong is an attractive game accessible to all. The parties are fast, they last only **15 seconds**.
The goal of the game is to move the ball up and down the screen and vice versa without touching the right and left edges. To do this, simply tilt the screen to move the ball.


#### Count of points
The point system is very simple:
- You mark **100 points** by alternately touching the top and bottom edge of the screen
- You lose **1** points each time you touch the right and left edges of the screen. Your score decreases as long as you stay against one of these edges


#### Result
 At the end of a game, enter your nickname and consult the first three of the ranking (before your game).
 To access the complete ranking, simply press the information button.
To replay click on the `Replay` button
The `Home` button takes you back to the homepage with the updated ranking

#### Conduct of a game
 - The player starts by clicking on the `Play` button on the home page.
 - He plays the part of SensorPong
 - On the result page: he enters his nickname, consults his score and his ranking. He also has access to the reduced classification and the complete ranking.

## Functionality
The user has access to several features:
- On the home screen: he can consult the rules or play a game
- On the result screen: he can see the complete ranking, see his own ranking, replay a game or return to the homepage.

## Technical aspects
- We decided to block the application in portrait mode. The purpose of the application being to move the ball up and down, it would have been easier to score points in landscape mode and this is not the goal we were looking for.
- The application has 2 models: one for the score (management of the user's part) and one for gravity (management of the ball during a game).
- Both models are linked to their respective ViewModel.
- The database (SQLite) is local on the user's phone. It has only one score table. This table contains 4 attributes: an id, a score (the one of the game played), a username (the player's) and the date (the day of the game).

## Group work
The project is carried out in several phases:
- Reflection phase: find the idea of the application and choose the sensor that will be used
- Work on paper: definition of the models useful to the application, of the table (and its attributes) present in the database, models of the interfaces
- Development work: open space work at the IUT and at home for the development of the application
- Meeting: at the beginning of the afternoon, an update was made to confirm the work done and to define the tasks to be performed during the next phase of development
- Tools used: Git for instant code sharing and Facebook when working at home for communication. Android Studio for development. Personal smartphones and emulators for deployment testing.

## Proposed developments
- Increase the area of point loss over time.
- Have several levels of difficulty: compared to the time of the game, the speed of the ball, etc ...
- To be able to consult the classification according to a precise lapse of time: the classification by day, week, month or year.
- Can change the background music in the options.
- Can reset the database to erase all scores.

## Credit
The Owl team is composed of:
- Gestas Vincent
- Grulois Clément
- Lemarchand - Fremont Olivier

And wish you a good part of Sensor Pong!

