# Lance une boîte de dialogue pour permettre à l'utilisateur de choisir un dossier
Add-Type -AssemblyName System.Windows.Forms
$folderBrowser = New-Object System.Windows.Forms.FolderBrowserDialog
$folderBrowser.Description = "Sélectionnez le dossier contenant les fichiers Java"
$folderBrowser.ShowNewFolderButton = $false
$result = $folderBrowser.ShowDialog()
# Vérifie si l'utilisateur a cliqué sur le bouton OK
if ($result -eq [System.Windows.Forms.DialogResult]::OK) {
   $directoryPath = $folderBrowser.SelectedPath
   $outputFilePath = "H:\Documents\java\AllJavaClasses.txt"
   # Crée un nouveau fichier texte ou écrase le fichier si il existe déjà
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
} else {
   Write-Host "Opération annulée par l'utilisateur." }