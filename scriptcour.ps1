# Définit directement le chemin du répertoire
$directoryPath = "H:\Users\CYTech Student\eclipse-workspace\Project\src"
$outputFilePath = "H:\Documents\java\AllJavaClasses.txt"

# Crée un nouveau fichier texte ou écrase le fichier s'il existe déjà
New-Item -Path $outputFilePath -ItemType File -Force

# Parcourt tous les fichiers .java dans le répertoire sélectionné
Get-ChildItem -Path $directoryPath -Filter *.java -Recurse | ForEach-Object {
    # Ajoute un séparateur et le nom du fichier dans le fichier de sortie
    Add-Content -Path $outputFilePath -Value "---------------"
    Add-Content -Path $outputFilePath -Value "$($_.Name):"
    # Lit le contenu du fichier et l'ajoute au fichier de sortie
    Get-Content -Path $_.FullName | ForEach-Object {
        Add-Content -Path $outputFilePath -Value $_
    }
}

# Affiche un message lorsque le script est terminé
Write-Host "Tous les fichiers ont été écrits dans $outputFilePath"
