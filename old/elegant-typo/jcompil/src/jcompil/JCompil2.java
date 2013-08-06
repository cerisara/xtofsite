package jcompil;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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
 * La separation en colonnes se fait en chargeant tout le texte d'un fichier, puis en divisant sa taille en 2
 * 
 * - attention: les fichiers .html ont pour nom le _dernier_ menu, donc attention aux conflits !
 * 
 * @author xtof
 *
 */
public class JCompil2 {
	public static void main(String args[]) {
		JCompil2 m = new JCompil2();
		m.createDirs();
		m.loadTemplate();
		m.prepareMenus();
		m.generatePages();
	}
	
	public File[] getTextFiles() {
		File d = new File("../texts");
		return d.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File arg0, String nom) {
				return nom.endsWith(".txt");
			}
		});
	}
	// contient les pointeurs vers les elts du hashmap "allmenus" qui apparaissent sur la homepage
	ArrayList<String> mainMenus = new ArrayList<String>();
	// contient les elts de tous les menus et sous-menus
	HashMap<String, List<String>> allmenus = new HashMap<String, List<String>>();
	String template;
	
	public static void recursiveDelete(String path) {
		File f = new File(path);
		File[] fs = f.listFiles(new FileFilter() {
			@Override
			public boolean accept(File x) {
				if (x.isFile()) return true;
				return false;
			}
		});
		if (fs==null) return;
		for (File y : fs) y.delete();
		File[] ds = f.listFiles(new FileFilter() {
			@Override
			public boolean accept(File x) {
				if (x.isDirectory()) return true;
				return false;
			}
		});
		for (File y : ds) recursiveDelete(y.getAbsolutePath());
		f.delete();
	}
	public void createDirs() {
		recursiveDelete("../pages");
		File d = new File("../pages");
		d.mkdirs();
	}
	
	public void generatePages() {
		// HOMEPAGE
		String homepage = changeMenus(template,mainMenus,0);
		savePage(homepage,"../pages/index.html");
		
		for (int i=0;i<mainMenus.size();i++) {
			String[] hist = {"main",mainMenus.get(i)};
			generatePagesRec(hist);
		}
	}
	public void generatePagesRec(String[] path) {
		List<String> l = allmenus.get(path[path.length-1]);
		int activeMenu=0;
		if (l==null) {
			// pas de sous-menus: on garde le menu du parent, avec un activeMenu >0
			l=allmenus.get(path[path.length-2]);
			activeMenu=l.indexOf(path[path.length-1]);
		} else {
			for (int i=0;i<l.size();i++) {
				String[] path2 = Arrays.copyOf(path, path.length+1);
				path2[path.length]=l.get(i);
				generatePagesRec(path2);
			}
		}
		String pg = changeMenus(template, l, activeMenu);
		pg = changeTitre(pg,path[path.length-1]);
		pg = delLowCols(pg);
		
		String filename = "../texts/";
		for (int i=1;i<path.length-1;i++) filename+=path[i]+"_";
		filename+=path[path.length-1]+".txt";
		String[] cols = loadCols(filename);
		pg = changeCol(0, pg,cols[0]);
		pg = changeCol(1, pg,cols[1]);
		savePage(pg,"../pages/"+path[path.length-1]+".html");
	}
	
	public void savePage(String page, String file) {
		try {
			PrintWriter f = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), Charset.forName("UTF-8")));
			f.println(page);
			f.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String delLowCols(String template) {
		return template;
	}
	public String changeCol(int col, String template,String newcol) {
		return template;
	}
	public String changeMenus(String template, List<String> menus, int activeMenu) {
		return template;
	}
	public String changeTitre(String template, String titre) {
		return template;
	}
	
	public String[] loadCols(String fich) {
		String[] tt = {"",""};
		return tt;
	}

	public static String noExt(String fich) {
		int i=fich.lastIndexOf('.');
		if (i<0) return fich;
		return fich.substring(0,i);
	}
	public void prepareMenus() {
		allmenus.clear();
		mainMenus.clear();
		ArrayList<String> main = new ArrayList<String>();
		allmenus.put("main", main);
		
		File[] fs = getTextFiles();
		for (File f : fs) {
			String s = noExt(f.getName().trim());
			String[] ss = s.split("_");
			if (!mainMenus.contains(ss[0])) {
				mainMenus.add(ss[0]);
				// on les duplique ici pour faciliter la recursion de generePages()
				List<String> l = allmenus.get("main");
				l.add(ss[0]);
			}
			String parent = ss[0];
			for (int i=1;i<ss.length;i++) {
				List<String> l = allmenus.get(parent);
				if (l==null) {
					l = new ArrayList<String>();
					allmenus.put(parent, l);
				}
				if (!l.contains(ss[i])) l.add(ss[i]);
				parent = ss[i];
			}
		}
	}
	
	public void loadTemplate() {
	}
	
}
