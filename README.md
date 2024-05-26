The Legend of Zelda: A Link to the Past - Jeu Java

Description
-----------
Ce projet est un jeu développé en Java inspiré par "The Legend of Zelda: A Link to the Past". Il utilise JavaFX pour l'interface graphique. Le jeu inclut diverses entités telles que des joueurs, des PNJ et des monstres, chacun avec des comportements et des interactions uniques. Les joueurs peuvent explorer le monde du jeu, interagir avec les PNJ, collecter des objets et combattre des monstres.

Fonctionnalités
---------------
- Joueur : Contrôlez un personnage joueur, déplacez-vous dans le monde du jeu et interagissez avec d'autres entités.
- PNJ : Personnages non jouables qui peuvent engager un dialogue et échanger des objets avec le joueur.
- Monstres : Entités hostiles qui peuvent attaquer le joueur.
- Système d'inventaire : Les joueurs peuvent collecter, utiliser et gérer des objets dans leur inventaire.
- Téléportation : Déplacez-vous entre différentes cartes dans le monde du jeu.
- Commerce : Achetez des objets aux PNJ en utilisant la monnaie du jeu.
- Conditions de victoire : Des conditions spécifiques qui, lorsqu'elles sont remplies, affichent un message de victoire et des options pour redémarrer ou quitter le jeu.

Installation
------------
1. Clonez le dépôt :
   git clone https://github.com/simiamine/zelda
   cd zelda-java-game

2. Assurez-vous d'avoir Java et JavaFX installés :
   - Java Development Kit (JDK) 8 ou supérieur
   - JavaFX SDK

3. Compilez le projet :
   javac -cp path/to/javafx-sdk/lib/*:. $(find . -name "*.java")

4. Exécutez le projet :
   java -cp path/to/javafx-sdk/lib/*:. main.Main

Utilisation
-----------
Contrôles
---------
- Flèches directionnelles : Déplacer le personnage joueur.
- Espace : Interagir avec les PNJ et les objets.
- I : Ouvrir et fermer l'inventaire.
- A : Attaquer.
- Entrée : Confirmer les actions dans les fenêtres de dialogue ou de commerce.
- T : Afficher ou masquer le texte de débogage.

Gameplay
--------
- Déplacement : Utilisez les flèches directionnelles pour déplacer votre personnage dans le monde du jeu.
- Interaction : Appuyez sur espace pour interagir avec les PNJ, ramasser des objets ou ouvrir des portes.
- Gestion de l'inventaire : Appuyez sur 'I' pour ouvrir l'inventaire, où vous pouvez utiliser des objets en les sélectionnant et en appuyant sur espace.
- Combat : Appuyez sur 'A' pour attaquer les monstres proches.
- Commerce : Interagissez avec les PNJ marchands pour acheter des objets en échange de rubis.
- Conditions de victoire : Remplissez les conditions spécifiques du jeu pour afficher un message de victoire avec des options pour redémarrer ou quitter.

