import tkinter as tk
from tkinter import filedialog
import os

def format_tile_setup(input_text):
    lines = input_text.strip().split('\n')
    formatted_lines = []
    
    for i in range(0, len(lines), 2):
        image = lines[i]
        collision = lines[i + 1].lower()
        formatted_line = f'setup({i // 2}, "{image}", {collision});'
        formatted_lines.append(formatted_line)
    
    return '\n'.join(formatted_lines)

def main():
    # Création de la fenêtre Tkinter
    root = tk.Tk()
    root.withdraw()  # Cache la fenêtre principale

    # Ouverture de la boîte de dialogue de sélection de fichier
    file_path = filedialog.askopenfilename(title="Sélectionner le fichier texte", filetypes=[("Text Files", "*.txt")])
    
    if not file_path:
        print("Aucun fichier sélectionné.")
        return

    # Lecture du contenu du fichier sélectionné
    with open(file_path, 'r') as file:
        input_text = file.read()

    # Formatage du texte
    formatted_text = format_tile_setup(input_text)
    
    # Création du nouveau nom de fichier
    base_name = os.path.basename(file_path)
    dir_name = os.path.dirname(file_path)
    new_file_name = os.path.splitext(base_name)[0] + "NewFormat.txt"
    new_file_path = os.path.join(dir_name, new_file_name)

    # Écriture du texte formaté dans le nouveau fichier
    with open(new_file_path, 'w') as new_file:
        new_file.write(formatted_text)
    
    print(f"Le texte formaté a été enregistré dans le fichier : {new_file_path}")

if __name__ == "__main__":
    main()
