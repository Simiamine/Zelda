# 📦 Inventaire Complet des Objets - Zelda

## 🗡️ Armes & Outils
| Objet | Fichier | Description | Fonctionnalités |
|-------|---------|-------------|-----------------|
| **Épée** | `OBJ_Sword.java` | Arme de base | Augmente force (1→3) et portée (1→2) |
| **Bombe** | `OBJ_Bomb.java` | Explosif | Détruit obstacles, inflige dégâts de zone |
| **Exterminateur** | `OBJ_Exterminator.java` | Arme ultime | Tue tous les monstres de la carte |

## 🔑 Objets Clés
| Objet | Fichier | Description | Fonctionnalités |
|-------|---------|-------------|-----------------|
| **Clé** | `OBJ_Key.java` | Clé dorée | Ouvre les portes verrouillées |
| **Triforce** | `OBJ_Triforce.java` | Objectif final | Condition de victoire |

## 💰 Collectibles
| Objet | Fichier | Description | Fonctionnalités |
|-------|---------|-------------|-----------------|
| **Rubis** | `OBJ_Ruby.java` | Monnaie | +1 rubis (max 999) |
| **Cœur** | `OBJ_Heart.java` | Santé | +1 PV (max 6) |

## 🎁 Conteneurs & Obstacles
| Objet | Fichier | Description | Fonctionnalités |
|-------|---------|-------------|-----------------|
| **Coffre** | `OBJ_Chest.java` | Conteneur | Stocke des objets, s'ouvre avec Espace |
| **Porte** | `OBJ_Door.java` | Passage | Bloque le passage, ouvre avec clé |
| **Pierre** | `OBJ_Stone.java` | Obstacle | Bloque le passage |
| **Herbe** | `OBJ_Grass.java` | Végétation | Drop rubis (20%) ou cœur (50%), coupable à l'épée |

## 🍶 Consommables
| Objet | Fichier | Description | Fonctionnalités |
|-------|---------|-------------|-----------------|
| **Potion** | `OBJ_Potion.java` | Soin | Restaure santé |

---

## 📊 Statistiques

- **Total objets** : 12 types
- **Armes/Outils** : 3
- **Objets clés** : 2
- **Collectibles** : 2
- **Obstacles** : 3
- **Consommables** : 1
- **Conteneurs** : 1

## 🎯 Objets avec Interactions Spéciales

### Herbe (Grass)
- **Condition**: Nécessite l'épée + appuyer sur Attaque
- **Drops**: 
  - 20% : +1 Rubis
  - 50% : +1 Cœur
  - 30% : Rien
- **Problème actuel**: Nécessite d'appuyer sur A alors que l'épée devrait la couper automatiquement

### Coffre (Chest)
- **Ouverture**: Espace
- **Contenu**: Peut contenir n'importe quel objet
- **État**: Reste ouvert après

### Bombe (Bomb)
- **Activation**: Utilisation depuis l'inventaire
- **Effet**: Explosion 3x3 cases
- **Dégâts**: Endommage monstres et joueur

---

## 🔄 Système d'Objets

**Classe de base**: `SuperObject.java`
- `name`: Nom de l'objet
- `description`: Description affichée
- `image`: Sprite de l'objet
- `collision`: Active/Désactive collision
- `worldX`, `worldY`: Position dans le monde
- `mapIndex`: Carte où l'objet se trouve
- `solidArea`: Hitbox de collision

**Méthodes principales**:
- `render()`: Affiche l'objet
- `interact()`: Interaction avec le joueur
- `use()`: Utilisation depuis l'inventaire

