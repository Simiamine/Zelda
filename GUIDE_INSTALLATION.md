# 🎮 GUIDE D'INSTALLATION ET DE LANCEMENT - Zelda

## ✅ Installation Complète (Déjà Faite !)

Tout est déjà installé sur votre Mac :

- ✅ **Java 17** (OpenJDK)
- ✅ **Maven** (gestionnaire de dépendances)
- ✅ **JavaFX** (sera téléchargé automatiquement par Maven)
- ✅ **Projet refactorisé et prêt**

---

## 🚀 LANCER LE JEU

### Méthode 1 : Script Simple (Recommandé)

```bash
cd ~/Documents/Amine/zelda
./run.sh
```

C'est tout ! Le script :
1. Télécharge les dépendances JavaFX
2. Compile le projet
3. Lance le jeu automatiquement

### Méthode 2 : Commandes Maven Manuelles

```bash
cd ~/Documents/Amine/zelda

# Télécharger les dépendances
mvn dependency:resolve

# Compiler le projet
mvn clean compile

# Lancer le jeu
mvn javafx:run
```

### Méthode 3 : Compiler et créer un JAR

```bash
cd ~/Documents/Amine/zelda

# Compiler
mvn clean package

# Lancer le JAR (après configuration supplémentaire)
java -jar target/zelda-game-2.0.jar
```

---

## 🎯 CONTRÔLES DU JEU

Une fois le jeu lancé :

| Touche | Action |
|--------|--------|
| **↑ ↓ ← →** | Déplacer Link |
| **Espace** | Interagir / Parler aux PNJ |
| **A** | Attaquer |
| **I** | Ouvrir/Fermer l'inventaire |
| **Entrée** | Confirmer (dialogues/commerce) |
| **T** | Activer/Désactiver le mode debug |

---

## 🛠️ DÉPANNAGE

### Problème : "Java command not found"

```bash
export PATH="/opt/homebrew/opt/openjdk@17/bin:$PATH"
export JAVA_HOME="/opt/homebrew/opt/openjdk@17/libexec/openjdk.jdk/Contents/Home"
```

Ajoutez ces lignes à votre `~/.zshrc` pour que ce soit permanent :

```bash
echo 'export PATH="/opt/homebrew/opt/openjdk@17/bin:$PATH"' >> ~/.zshrc
source ~/.zshrc
```

### Problème : "mvn command not found"

```bash
brew install maven
```

### Problème : Le jeu ne démarre pas

1. Vérifiez Java :
```bash
java -version
```
Vous devez voir : `openjdk version "17.x.x"`

2. Vérifiez Maven :
```bash
mvn -version
```

3. Nettoyez et recompilez :
```bash
mvn clean
mvn compile
mvn javafx:run
```

### Problème : Erreur de compilation

Si vous avez modifié le code :

```bash
# Nettoyer complètement
mvn clean

# Recompiler depuis zéro
mvn compile
```

---

## 📁 STRUCTURE DU PROJET

```
zelda/
├── src/                       # Code source Java
│   ├── main/                  # Classes principales
│   │   ├── Main.java         # Point d'entrée
│   │   ├── GamePanel.java    # Panneau de jeu
│   │   ├── GameConstants.java # Constantes
│   │   └── UI.java           # Interface utilisateur
│   ├── entity/                # Entités du jeu
│   │   ├── Player.java       # Joueur
│   │   ├── Monster.java      # Monstres
│   │   └── NPC.java          # PNJ
│   ├── object/                # Objets du jeu
│   └── tile/                  # Système de tuiles
│       └── TileManager.java  # Gestionnaire de cartes
├── res/                       # Ressources
│   ├── player/               # Sprites du joueur
│   ├── monster/              # Sprites des monstres
│   ├── maps/                 # Fichiers de cartes
│   ├── zeldatile/            # Tuiles
│   ├── tiles_config.txt      # Config des tuiles
│   └── music/                # Musiques
├── pom.xml                    # Configuration Maven
├── run.sh                     # Script de lancement
└── GUIDE_INSTALLATION.md      # Ce fichier
```

---

## 🔧 CONFIGURATION AVANCÉE

### Changer la résolution

Éditez `src/main/GameConstants.java` :

```java
public static final int MAX_SCREEN_COL = 16;  // Colonnes
public static final int MAX_SCREEN_ROW = 14;  // Lignes
public static final int SCALE = 3;             // Facteur d'échelle
```

### Modifier les constantes du jeu

Toutes les constantes sont dans `src/main/GameConstants.java` :
- Vitesse du joueur
- Points de vie maximum
- Taille des tuiles
- etc.

### Ajouter de nouvelles cartes

1. Créez un fichier texte dans `res/maps/`
2. Ajoutez le chargement dans `TileManager.java` :
```java
loadMap("res/maps/nouvelle_carte.txt", 2);
```

### Ajouter de nouvelles tuiles

1. Ajoutez l'image dans `res/zeldatile/`
2. Ajoutez une ligne dans `res/tiles_config.txt` :
```
256,image_part_257.png,false
```

---

## 📊 INFORMATIONS SYSTÈME

### Versions installées

- **Java** : OpenJDK 17
- **Maven** : 3.9.11
- **JavaFX** : 17.0.2
- **Projet** : Version 2.0 (Refactorisé)

### Chemins importants

- Java : `/opt/homebrew/opt/openjdk@17/`
- Maven : `/opt/homebrew/bin/mvn`
- Projet : `~/Documents/Amine/zelda/`

---

## 📖 DOCUMENTATION ADDITIONNELLE

- **REFACTORING_REPORT.md** : Détails du refactoring effectué
- **README.md** : Description générale du projet
- **Javadoc** : Dans le code source des classes

---

## 🆘 SUPPORT

Si vous rencontrez des problèmes :

1. Vérifiez que Java 17 est installé : `java -version`
2. Vérifiez que Maven est installé : `mvn -version`
3. Lisez les messages d'erreur dans le terminal
4. Consultez la section Dépannage ci-dessus

---

## 🎉 AMÉLIORATIONS APPORTÉES (v2.0)

- ✅ Code entièrement refactorisé
- ✅ Architecture propre et maintenable
- ✅ Système de constantes centralisé
- ✅ Logging professionnel
- ✅ Gestion d'erreurs appropriée
- ✅ Configuration Maven automatique
- ✅ Script de lancement simple
- ✅ Documentation complète

---

**Bon jeu ! 🎮✨**

