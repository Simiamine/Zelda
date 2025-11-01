#!/bin/bash

# Script d'installation et de lancement automatique du jeu Zelda
# Ne nécessite pas de sudo !

set -e  # Arrêter en cas d'erreur

echo "🎮 Configuration du projet Zelda..."
echo ""

# Couleurs
GREEN='\033[0;32m'
BLUE='\033[0;34m'
RED='\033[0;31m'
NC='\033[0m' # No Color

# Répertoires
PROJECT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
LIB_DIR="$PROJECT_DIR/lib"
BUILD_DIR="$PROJECT_DIR/build"
JAVAFX_VERSION="17.0.2"

# Configuration Java
export PATH="/opt/homebrew/opt/openjdk@17/bin:$PATH"
export JAVA_HOME="/opt/homebrew/opt/openjdk@17/libexec/openjdk.jdk/Contents/Home"

echo -e "${BLUE}📋 Étape 1/5: Vérification de Java...${NC}"
if ! command -v java &> /dev/null; then
    echo -e "${RED}❌ Java n'est pas installé ou pas dans le PATH${NC}"
    echo "Exécutez: brew install openjdk@17"
    exit 1
fi

JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2)
echo -e "${GREEN}✅ Java $JAVA_VERSION détecté${NC}"
echo ""

echo -e "${BLUE}📦 Étape 2/5: Téléchargement de JavaFX...${NC}"
mkdir -p "$LIB_DIR"
cd "$LIB_DIR"

# URLs JavaFX pour macOS ARM64
JAVAFX_BASE_URL="https://download2.gluonhq.com/openjfx/17.0.2/openjfx-17.0.2_osx-aarch64_bin-sdk.zip"

if [ ! -f "javafx-sdk-$JAVAFX_VERSION/lib/javafx.controls.jar" ]; then
    echo "Téléchargement de JavaFX $JAVAFX_VERSION..."
    curl -L -o javafx.zip "$JAVAFX_BASE_URL"
    echo "Extraction..."
    unzip -q javafx.zip
    rm javafx.zip
    echo -e "${GREEN}✅ JavaFX téléchargé${NC}"
else
    echo -e "${GREEN}✅ JavaFX déjà présent${NC}"
fi
echo ""

echo -e "${BLUE}🔨 Étape 3/5: Compilation du projet...${NC}"
cd "$PROJECT_DIR"
mkdir -p "$BUILD_DIR"

# Trouver tous les fichiers Java
echo "Recherche des fichiers sources..."
SOURCES=$(find src -name "*.java")
SOURCE_COUNT=$(echo "$SOURCES" | wc -l | xargs)
echo "Trouvé $SOURCE_COUNT fichiers .java"

# Construire le classpath JavaFX
JAVAFX_LIBS="$LIB_DIR/javafx-sdk-$JAVAFX_VERSION/lib"
JAVAFX_CLASSPATH="$JAVAFX_LIBS/javafx.base.jar:$JAVAFX_LIBS/javafx.controls.jar:$JAVAFX_LIBS/javafx.graphics.jar:$JAVAFX_LIBS/javafx.media.jar"

echo "Compilation en cours..."
javac -d "$BUILD_DIR" \
      -cp "$JAVAFX_CLASSPATH" \
      -encoding UTF-8 \
      $SOURCES

if [ $? -eq 0 ]; then
    echo -e "${GREEN}✅ Compilation réussie !${NC}"
else
    echo -e "${RED}❌ Erreur de compilation${NC}"
    exit 1
fi
echo ""

echo -e "${BLUE}📁 Étape 4/5: Copie des ressources...${NC}"
# Copier les ressources dans le build
if [ -d "$PROJECT_DIR/res" ]; then
    cp -r "$PROJECT_DIR/res" "$BUILD_DIR/" 2>/dev/null || true
    echo -e "${GREEN}✅ Ressources copiées${NC}"
fi
echo ""

echo -e "${BLUE}🚀 Étape 5/5: Lancement du jeu...${NC}"
echo ""
echo -e "${GREEN}========================================${NC}"
echo -e "${GREEN}    LANCEMENT DU JEU ZELDA 🎮${NC}"
echo -e "${GREEN}========================================${NC}"
echo ""

cd "$BUILD_DIR"

# Options JVM pour JavaFX
JAVAFX_MODULES="javafx.controls,javafx.graphics,javafx.media"

java --module-path "$JAVAFX_LIBS" \
     --add-modules "$JAVAFX_MODULES" \
     -cp ".:$JAVAFX_CLASSPATH" \
     main.Main

echo ""
echo -e "${GREEN}👋 Merci d'avoir joué !${NC}"

