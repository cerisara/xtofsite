package jcompil;

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