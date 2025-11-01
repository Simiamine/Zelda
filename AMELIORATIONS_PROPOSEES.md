# 🎮 Propositions d'Améliorations - Zelda v2.1

## ✅ Améliorations Déjà Implémentées

### 1. Vitesse du Joueur ✓
- **Avant** : 10 pixels/frame (trop rapide)
- **Après** : 6 pixels/frame  
- **Résultat** : Meilleur contrôle, gameplay plus tactique

### 2. Physique de l'Épée ✓
- **Système de cooldown** : 15 frames entre chaque attaque (empêche spam)
- **Animation d'attaque** : 10 frames de durée
- **État d'attaque** : `isAttacking()` pour synchroniser animations

### 3. Destruction Automatique de l'Herbe ✓
- **Avant** : Il fallait appuyer sur A manuellement
- **Après** : L'herbe est détruite automatiquement quand l'épée frappe
- **Mécanisme** : Détection de hitbox pendant l'animation d'attaque
- **Drops conservés** : Rubis (20%), Cœurs (50%)

---

## 🎯 Améliorations Prioritaires Proposées

### 🎨 Visuels & Animations

#### 1. Animation d'Attaque du Joueur (★★★★★)
```
Priorité: HAUTE
Difficulté: Moyenne
```
**Problème** : Le joueur n'a pas d'animation visuelle quand il attaque  
**Solution** :
- Ajouter des sprites d'attaque pour chaque direction (U, D, L, R)
- Afficher le sprite d'attaque pendant `attackAnimationCounter > 0`
- Possible effet de "slash" visuel

**Fichiers à modifier** :
- `Player.java` : méthode `render()`
- Ajouter sprites dans `res/player/` : `AttackU.png`, `AttackD.png`, etc.

#### 2. Effet Visuel de Slash d'Épée (★★★★☆)
```
Priorité: MOYENNE-HAUTE
Difficulté: Moyenne
```
- Afficher un effet de tranche/slash pendant l'attaque
- Particules ou sprite semi-transparent devant le joueur
- Couleur selon la direction

#### 3. Feedback Visuel des Dégâts (★★★★☆)
```
Priorité: MOYENNE-HAUTE
Difficulté: Facile
```
- Monstres clignotent en rouge quand touchés
- Joueur clignote en rouge quand blessé
- Effet de recul (knockback) sur les ennemis

#### 4. Barre de Vie Visible pour les Monstres (★★★☆☆)
```
Priorité: MOYENNE
Difficulté: Facile
```
- Mini barre HP au-dessus des monstres
- Seulement visible quand le monstre est blessé
- Disparaît après 2 secondes

---

### 🎵 Audio & Sonorités

#### 5. Effets Sonores d'Attaque (★★★★★)
```
Priorité: HAUTE
Difficulté: Facile
```
**Sons à ajouter** :
- `sword_slash.wav` : Coup d'épée
- `grass_cut.wav` : Herbe coupée
- `monster_hit.wav` : Monstre touché
- `player_hurt.wav` : Joueur blessé

**Implémentation** :
- Utiliser le `Sound` existant de GamePanel
- Jouer le son au début de l'animation d'attaque

#### 6. Musique d'Ambiance par Carte (★★★☆☆)
```
Priorité: MOYENNE
Difficulté: Moyenne
```
- Musique différente selon la carte
- Musique de combat quand monstres proches
- Fade in/out entre les musiques

---

### ⚔️ Combat & Gameplay

#### 7. Système de Combo (★★★★☆)
```
Priorité: MOYENNE-HAUTE
Difficulté: Moyenne
```
**Mécanisme** :
- 3 attaques consécutives = combo !
- Dernier coup du combo inflige +50% dégâts
- Reset si pas d'attaque pendant 1 seconde
- Compteur de combo affiché

#### 8. Attaque Chargée (★★★★☆)
```
Priorité: MOYENNE-HAUTE
Difficulté: Moyenne
```
- Maintenir A pour charger l'attaque
- Attaque chargée = portée +1, dégâts x2
- Animation différente (épée brille)
- Son différent

#### 9. Dash/Esquive (★★★★★)
```
Priorité: HAUTE
Difficulté: Moyenne
```
**Contrôles** : Double-tap direction OU touche Shift
**Effet** :
- Déplacement rapide sur 2-3 tuiles
- Invincibilité pendant le dash (iframes)
- Cooldown de 2 secondes
- Animation de blur/trail

#### 10. Blocage avec Bouclier (★★★★☆)
```
Priorité: MOYENNE-HAUTE
Difficulté: Moyenne
```
- Nouvel objet : `OBJ_Shield.java`
- Maintenir touche S pour bloquer
- Réduit dégâts de 75%
- Ne peut pas se déplacer en bloquant

---

### 🎒 Objets & Items

#### 11. Arc et Flèches (★★★★☆)
```
Priorité: MOYENNE-HAUTE
Difficulté: Haute
```
- `OBJ_Bow.java` : Arc
- `OBJ_Arrow.java` : Projectiles
- Attaque à distance
- Munitions limitées (collectables)

#### 12. Bottes de Vitesse (★★★☆☆)
```
Priorité: MOYENNE
Difficulté: Facile
```
- `OBJ_Boots.java`
- Augmente vitesse de 6 → 9
- Permanent une fois équipé

#### 13. Lanterne (★★★☆☆)
```
Priorité: MOYENNE
Difficulté: Moyenne
```
- `OBJ_Lantern.java`
- Éclaire les zones sombres
- Révèle passages secrets
- Nécessite de l'huile (consommable)

#### 14. Hookshot/Grappin (★★★★☆)
```
Priorité: MOYENNE-HAUTE
Difficulté: Très Haute
```
- Tire un crochet
- Attrape objets à distance
- Traverse gouffres

#### 15. Amélioration des Drops (★★★☆☆)
```
Priorité: MOYENNE
Difficulté: Facile
```
**Drop actuel de l'herbe** :
- 20% : Rubis
- 50% : Cœur
- 30% : Rien

**Proposition** :
- 15% : Rubis
- 25% : Petit cœur (+1)
- 10% : Grand cœur (+3)
- 5% : Rubis bleu (+5)
- 2% : Fée (revie si mort)
- 43% : Rien

---

### 🧩 Puzzles & Mécaniques

#### 16. Interrupteurs et Portes (★★★★☆)
```
Priorité: MOYENNE-HAUTE
Difficulté: Moyenne
```
- `OBJ_Switch.java` : Interrupteur au sol
- `OBJ_Button.java` : Bouton mural
- Ouvre/ferme des portes spécifiques
- Puzzles multi-switchs

#### 17. Blocs Poussables (★★★★☆)
```
Priorité: MOYENNE-HAUTE
Difficulté: Moyenne
```
- `OBJ_PushableBlock.java`
- Le joueur peut les pousser
- Utilisés pour puzzles
- Peuvent activer des interrupteurs

#### 18. Téléporteurs (★★★☆☆)
```
Priorité: MOYENNE
Difficulté: Facile
```
- `OBJ_Teleporter.java`
- Téléporte entre 2 points
- Effet visuel tourbillon
- Son de téléportation

---

### 🏆 Progression & RPG

#### 19. Système d'Expérience (★★★★☆)
```
Priorité: MOYENNE-HAUTE
Difficulté: Haute
```
- XP gagnée en tuant monstres
- Niveaux de joueur (1-20)
- Chaque niveau : +1 force, +1 cœur max
- Barre XP dans l'UI

#### 20. Quêtes Secondaires (★★★★☆)
```
Priorité: MOYENNE-HAUTE
Difficulté: Haute
```
- Journal de quêtes
- NPCs donnent des missions
- Récompenses : items, rubis, XP
- 5-10 quêtes variées

#### 21. Système de Sauvegarder (★★★★★)
```
Priorité: TRÈS HAUTE
Difficulté: Moyenne
```
**Actuellement** : Pas de sauvegarde, tout perdu au Game Over  
**Proposition** :
- Sauvegarder en JSON
- Points de sauvegarde (statues)
- Sauvegarde auto toutes les 5 min
- Fichier : `saves/save_slot_1.json`

---

### 🗺️ Cartes & Monde

#### 22. Mini-Map (★★★★☆)
```
Priorité: MOYENNE-HAUTE
Difficulté: Moyenne
```
- Coin supérieur droit
- Affiche position joueur
- Révèle zones explorées
- Icônes : Coffres, NPCs, Boss

#### 23. Nouvelles Cartes Thématiques (★★★☆☆)
```
Priorité: MOYENNE
Difficulté: Haute
```
- Forêt mystérieuse
- Grotte souterraine (sombre)
- Donjon avec énigmes
- Désert aride
- Village avec NPCs

#### 24. Zones Secrètes (★★★☆☆)
```
Priorité: MOYENNE
Difficulté: Moyenne
```
- Murs destructibles (bombes)
- Passages cachés
- Salles avec trésors rares

---

### 👾 Ennemis & Boss

#### 25. Nouveaux Types d'Ennemis (★★★★☆)
```
Priorité: MOYENNE-HAUTE
Difficulté: Moyenne
```
**Ennemis proposés** :
- **Archer** : Tire des flèches à distance
- **Mage** : Lance des sorts (proj. magiques)
- **Tank** : Beaucoup de HP, lent, fort
- **Volant** : Survole obstacles
- **Fantôme** : Traverse murs

#### 26. Boss de Fin de Donjon (★★★★★)
```
Priorité: TRÈS HAUTE
Difficulté: Très Haute
```
- 1 Boss par donjon (3-4 boss total)
- Patterns d'attaque complexes
- Phases multiples
- Récompenses : items légendaires
- Musique de boss épique

---

### 🎨 Interface & UX

#### 27. Menu Pause Amélioré (★★★☆☆)
```
Priorité: MOYENNE
Difficulté: Facile
```
- Carte du monde
- Journal de quêtes
- Équipement
- Statistiques
- Options (son, difficulté)

#### 28. Indicateur de Cooldown (★★★☆☆)
```
Priorité: MOYENNE
Difficulté: Facile
```
- Cercle autour du bouton A
- Se remplit pendant le cooldown
- Feedback visuel clair

#### 29. Tutoriel Interactif (★★★☆☆)
```
Priorité: MOYENNE
Difficulté: Moyenne
```
- Première partie = tutoriel
- Pop-ups explicatifs
- NPC guide au début
- Explique contrôles progressivement

---

### ⚙️ Technique & Qualité

#### 30. Particules System (★★★★☆)
```
Priorité: MOYENNE-HAUTE
Difficulté: Haute
```
- Classe `ParticleSystem.java`
- Effets : poussière, étincelles, feu
- Pool d'objets réutilisables
- Optimisé (max 100 particules)

#### 31. Dialogue System Avancé (★★★☆☆)
```
Priorité: MOYENNE
Difficulté: Moyenne
```
- Portraits de NPCs
- Choix multiples (branches)
- Texte qui défile (typewriter)
- Sauter avec Espace

#### 32. Configuration Externe (★★☆☆☆)
```
Priorité: BASSE-MOYENNE
Difficulté: Facile
```
- `config.properties` :
  - Vitesse joueur
  - Difficulté (Easy/Normal/Hard)
  - Volume audio
  - Résolution

---

## 📊 Roadmap Suggérée

### Phase 1 : Polish Core (2-3 jours)
✅ Vitesse joueur  
✅ Cooldown attaque  
✅ Herbe auto-détruite  
🔲 Animation attaque  
🔲 Effets sonores  
🔲 Feedback visuel dégâts  

### Phase 2 : Combat Avancé (3-4 jours)
🔲 Dash/esquive  
🔲 Système combo  
🔲 Attaque chargée  
🔲 Nouveaux ennemis (2-3)  

### Phase 3 : Monde & Exploration (4-5 jours)
🔲 Mini-map  
🔲 2-3 nouvelles cartes  
🔲 Puzzles (interrupteurs, blocs)  
🔲 Zones secrètes  

### Phase 4 : Progression RPG (3-4 jours)
🔲 Système XP/niveaux  
🔲 Quêtes secondaires (3-5)  
🔲 Save system  
🔲 Stats avancées  

### Phase 5 : Boss & Endgame (5-7 jours)
🔲 2-3 Boss épiques  
🔲 Dungeons thématiques  
🔲 Items légendaires  
🔲 Ending du jeu  

---

## 🎯 Top 5 - À Implémenter EN PRIORITÉ

1. **Animation d'Attaque** (essentiel pour le feel)
2. **Effets Sonores** (immersion x10)
3. **Dash/Esquive** (gameplay plus dynamique)
4. **Save System** (évite frustration)
5. **Mini-Map** (meilleure navigation)

---

## 💡 Idées Bonus (Fun!)

- **Mode Multijoueur Local** : 2 joueurs coopératifs
- **Mode Nuit/Jour** : Change gameplay et ennemis
- **Masques avec Pouvoirs** : Comme Majora's Mask
- **Animaux Compagnons** : Pet qui aide au combat
- **Pêche Mini-Game** : Détente entre combats
- **Course de Chevaux** : Mini-jeu de course
- **Crafting Léger** : Combiner items pour en créer de nouveaux

---

Créé le: 2 novembre 2025  
Version: 2.1 (Post-Refactoring)

