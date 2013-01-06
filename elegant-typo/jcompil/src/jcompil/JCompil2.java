package jcompil;

/**
 * Genere toutes les pages de mon site web a partir de la SEULE homepage.
 * 
 * L'idee est de definir, via des text files sous GIT, les contenus des differentes pages web,
 * ajouter un post-commit hook sous GIT pour generer automatiquement les pages web et deployer le site.
 * 
 * Toutes les pages respectent le format des deux colonnes de texte + une colonne a droite de menu et widgets
 * 
 * Ici, on s'interesse seulement a la generation du site:
 * - il faut changer le menu a droite pour pouvoir proposer des sous-menus
 * - changer le titre au-dessus des 2 colonnes de texte
 * - supprimer les 2 sous-colonnes "JSafran" et "JTrans" qui ne doivent apparaitre que dans la homepage
 * - changer le texte des 2 colonnes
 * - attention a changer le (sous-)menu "actif" a chaque page
 * 
 * Dans GIT, on place un fichier par menu dans le repertoire textfiles/
 * - Le menu = nom du fichier .txt; c'est aussi le titre mis au-dessus des 2 colonnes
 * - Lorsque le nom du fichier est de la forme "research_Inference.txt"
 *   alors on cree un sous-menu "inference" pour le menu "research"
 * - quand on clic sur le menu "research", le texte du premier sous-menu charge est affiche par defaut
 * - les sous-menus possedent tous un premier choix = homepage
 * 
 * 
 * @author xtof
 *
 */
public class JCompil2 {

}
