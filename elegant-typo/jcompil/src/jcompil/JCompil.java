package jcompil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Random;

/**
 * This website compiler assumes preexisting:
 * - the main page, which also defines a template, thanks to a few "comment tags" that identify the parts to remove/replace
 * - pages "software/pageXX.html": the compiler creates a NEW index page with links to each software page
 * - texts "news/newsXX.txt": the compiler creates a NEW page with all news titles + one NEW page with a single news (long version)
 * 								+ randomly chooses one news and prints its summary in the main template
 * - texts "research/researchXX.txt": the compiler creates a NEW page with all news titles + one NEW page with a single research (long version)
 * 								+ randomly chooses one research and prints its summary in the main template
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

	String maintemplate, pagetemplate;
	String[] newstitle, newsabstract, newscontent;
	String[] researchtitle, researchabstract, researchcontent;
	Random rand = new Random();

	// loads the main page
	// create template1: remove the news & research sections
	// create template2: remove the content section
	void loadTemplate() {
		try {
			BufferedReader f = new BufferedReader(new InputStreamReader(new FileInputStream("../index0.html"), Charset.forName("UTF-8")));
			StringBuilder sb0 = new StringBuilder();
			StringBuilder sb1 = new StringBuilder();
			boolean add0=true, add1=true;
			for (;;) {
				String s = f.readLine();
				if (s==null) break;
				if (s.indexOf("<!-- detsoncontent deb -->")>=0) {
					sb1.append("tofill_detson_content\n");
					add1=false;
				} else if (s.indexOf("<!-- detsonnews deb -->")>=0) {
					sb0.append("tofill_detson_news\n");
					add0=false;
				} else if (s.indexOf("<!-- detsonnews fin -->")>=0) {
					add0=true;
				} else if (s.indexOf("<!-- detsoncontent fin -->")>=0) {
					add1=true;
				} else {
					if (add0) sb0.append(s+"\n");
					if (add1) sb1.append(s+"\n");
				}
			}
			f.close();
			maintemplate=sb0.toString();
			pagetemplate=sb1.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// look for text files in the news dir
	void look4news() {
		File f = new File("../news");
		File[] fs = f.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File arg0, String arg1) {
				return arg1.endsWith(".txt");
			}
		});
		newstitle = new String[fs.length];
		newsabstract = new String[fs.length];
		newscontent = new String[fs.length];
		try {
			for (int i=0;i<fs.length;i++) {
				BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(fs[i]), Charset.forName("UTF-8")));
				newstitle[i]=bf.readLine().trim();
				newsabstract[i]=bf.readLine().trim();
				StringBuilder sb = new StringBuilder();
				for (;;) {
					String s = bf.readLine();
					if (s==null) break;
					sb.append(s);
				}
				newscontent[i]=sb.toString();
				bf.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// look for research pages in the research dir
	// TODO: use jmath
	void look4research() {
		File f = new File("../research");
		File[] fs = f.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File arg0, String arg1) {
				return arg1.endsWith(".txt");
			}
		});
		researchtitle = new String[fs.length];
		researchabstract = new String[fs.length];
		researchcontent = new String[fs.length];
		try {
			for (int i=0;i<fs.length;i++) {
				BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(fs[i]), Charset.forName("UTF-8")));
				researchtitle[i]=bf.readLine().trim();
				researchabstract[i]=bf.readLine().trim();
				StringBuilder sb = new StringBuilder();
				for (;;) {
					String s = bf.readLine();
					if (s==null) break;
					sb.append(s);
				}
				researchcontent[i]=sb.toString();
				bf.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	// select one news/research at random and create the HOME page
	void createHomePage() {
		int newsidx = rand.nextInt(newstitle.length);
		System.out.println("chosen news: "+newstitle[newsidx]);
		String s = "<h2>"+newstitle[newsidx]+"</h2>\n";
		s += "<p>"+newsabstract[newsidx]+"</p>\n";
		String ss = maintemplate.replaceAll("tofill_detson_news", s);
		int researchidx = rand.nextInt(researchtitle.length);
		System.out.println("chosen research: "+researchtitle[researchidx]);
		s = "<h2>"+researchtitle[researchidx]+"</h2>\n";
		s += "<p>"+researchabstract[researchidx]+"</p>\n";
		String sss = ss.replaceAll("tofill_detson_research", s);

		try {
			PrintWriter f = new PrintWriter(new FileWriter("../index.html"));
			f.println(sss);
			f.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
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