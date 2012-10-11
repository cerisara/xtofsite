package jcompil;

/**
 * This website compiler assumes preexisting:
 * - the main page, which also defines a template, thanks to a few "comment tags" that identify the parts to remove/replace
 * - pages "software/pageXX.html": the compiler creates a NEW index page with links to each software page
 * - texts "news/newsXX.txt": the compiler creates a NEW page with all news titles + one NEW page with a single news (long version)
 * - pages "others/pageXX.html": the compiler just add a link in the main template menu to each of these pages
 * - pages "static/pageXX.html": the compile does not touch this dir ! (eventual links must be added manually to the main template, in particular for JTrans and JSafran)
 * 
 * 
 * @author xtof
 *
 */
public class JCompil {
	public static void main(String args[]) {
		JCompil m = new JCompil();
		m.loadTemplate();
		m.look4news();
		m.look4research();
		
		m.createHomePage();
		m.createNewsPages();
		m.createResearchPages();
		
		m.createSoftwarePages();
		
		// TODO: replace the header/footer of existing full pages by the one from template 1 ?
		
		// TODO: search index ?
	}
	
	// loads the main page
	// create template1: remove the news & research sections
	// create template2: remove the content section
	void loadTemplate() {
		
	}
	// look for text files in the news dir
	void look4news() {
		
	}
	// look for research pages in the research dir
	// WARNING: use jmath
	void look4research() {
		
	}
	// select one news/research at random and create the HOME page
	void createHomePage() {
		
	}
	// create research index page by concatenating all research summary in template 2
	// + create one template2 per research
	void createResearchPages() {
		
	}
	// create 1 news page by concatenating all news in template 2
	void createNewsPages() {
		
	}
	// look for full pages in softwa dir, insert each in a template 2
	void createSoftwarePages() {
		
	}
}