#!/bin/bash

# Script simplifié pour lancer le jeu Zelda avec Maven
# Usage: ./run.sh

set -e

# Couleurs
GREEN='\033[0;32m'
BLUE='\033[0;34m'
YELLOW='\033[1;33m'
NC='\033[0m'

echo ""
echo -e "${BLUE}🎮 ========================================${NC}"
echo -e "${BLUE}   ZELDA: A LINK TO THE PAST - v2.0${NC}"
echo -e "${BLUE}========================================${NC}"
echo ""

# Aller dans le répertoire du projet
cd "$(dirname "$0")"

echo -e "${YELLOW}📦 Téléchargement des dépendances...${NC}"
mvn dependency:resolve -q

echo ""
echo -e "${YELLOW}🔨 Compilation du projet...${NC}"
mvn clean compile -q

echo ""
echo -e "${GREEN}🚀 Lancement du jeu...${NC}"
echo -e "${GREEN}========================================${NC}"
echo ""

# Lancer le jeu avec JavaFX
mvn javafx:run

echo ""
echo -e "${GREEN}👋 Merci d'avoir joué !${NC}"

