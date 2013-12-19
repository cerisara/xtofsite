import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Build a set of simple HTML files to represent the ancestor tree from a GEDCOM file
 * 
 * @author xtof
 *
 */
public class Gene {
	HashMap<String,Individu> allpersons = new HashMap<String, Gene.Individu>();
	HashSet<Event> allevents = new HashSet<Gene.Event>();
	HashMap<String,Famille> families = new HashMap<String, Gene.Famille>();

	public static boolean isIndiv(String s) {return s.endsWith(" INDI");}
	public static boolean isFamille(String s) {return s.endsWith(" FAM");}
	
	public static boolean isGivenName(String s) {return s.contains(" GIVN ");}
	public static boolean isSurname(String s) {return s.contains(" SURN ");}
	public static boolean isSex(String s) {return s.contains(" SEX ");}
	public static boolean isBirth(String s) {return s.endsWith(" BIRT");}
	public static boolean isDeath(String s) {return s.endsWith(" DEAT");}
	public static boolean isDate(String s) {return s.contains(" DATE ");}
	public static boolean isChildOf(String s) {return s.contains(" FAMC ");}
	public static boolean isMarriedTo(String s) {return s.contains(" FAMS ");}
	public static boolean isHusband(String s) {return s.contains(" HUSB ");}
	public static boolean isWife(String s) {return s.contains(" WIFE ");}
	public static boolean isEnfant(String s) {return s.contains(" CHIL ");}
	// marche si on n'a que des photos dans les personnes
	public static boolean isPhoto(String s) {return s.contains(" FILE ");}

	class Famille {
		String idx;
		String pere=null, mere=null;
		ArrayList<String> enfants = new ArrayList<String>();
		
		public String load(String header, BufferedReader f) throws Exception {
			String[] ss = header.split(" ");
			int nivBase=Integer.parseInt(ss[0]);
			idx = ss[1];
			families.put(idx,this);
			String s = f.readLine();
			for (;;) {
				if (s==null) return s;
				ss = s.split(" ");
				int niv=Integer.parseInt(ss[0]);
				if (niv<=nivBase) return s;
				if (isHusband(s)) {
					String ii = ss[ss.length-1];
					pere = ii;
				} else if (isWife(s)) {
					String ii = ss[ss.length-1];
					mere = ii;
				} else if (isEnfant(s)) {
					String ii = ss[ss.length-1];
					enfants.add(ii);
				}
				s=f.readLine();
			}
		}
	}
	
	class MyDate {
		String date=null;
		public String load(String header, BufferedReader f) throws Exception {
			String[] ss = header.split(" ");
			int nivBase=Integer.parseInt(ss[0]);
			if (!header.contains("DATE EST")) {
				int i=header.indexOf(" DATE");
				date = header.substring(i+6);
			}
			String s = f.readLine();
			for (;;) {
				if (s==null) return s;
				ss = s.split(" ");
				int niv=Integer.parseInt(ss[0]);
				if (niv<=nivBase) return s;
				s=f.readLine();
			}
		}
		public String toHTML() {
			String d="";
			if (date!=null) d=date;
			return d;
		}
		public int getYear() {
			if (date==null) return -1;
			Pattern p = Pattern.compile("\\d\\d\\d\\d");
			Matcher m = p.matcher(date);
			if (m.find()) {
				String g = m.group();
				System.out.println("debug "+g);
				return Integer.parseInt(g);
			} else return -1;
		}
	}
	
	class Event {
		MyDate date=null;
		
		public String load(String header, BufferedReader f) throws Exception {
			String[] ss = header.split(" ");
			int nivBase=Integer.parseInt(ss[0]);
			String s = f.readLine();
			for (;;) {
				if (s==null) return s;
				ss = s.split(" ");
				int niv=Integer.parseInt(ss[0]);
				if (niv<=nivBase) return s;
				if (isDate(s)) {
					date = new  MyDate();
					s=date.load(s,f);
					continue;
				}
				s=f.readLine();
			}
		}
	}

	private String getPhoto(String s) {
		String r=null;
		int i=s.indexOf(" FILE ");
		if (i>=0) r=s.substring(i+6);
		return r;
	}
	
	class Individu {
		String idx;
		String givenName=null, surname=null;
		char sex='U';
		Event birth=null, death=null;
		ArrayList<String> idxMarried = new ArrayList<String>();
		String idxParents=null;
		String photo=null;

		public String load(String header, BufferedReader f) throws Exception {
			String[] ss = header.split(" ");
			int nivBase=Integer.parseInt(ss[0]);
			idx = ss[1];
			allpersons.put(idx,this);
			String s = f.readLine();
			for (;;) {
				if (s==null) return s;
				ss = s.split(" ");
				int niv=Integer.parseInt(ss[0]);
				if (niv<=nivBase) return s;
				if (isGivenName(s)) {
					givenName=ss[2];
				} else if (isSurname(s)) {
					surname=ss[2];
				} else if (isSex(s)) {
					sex=ss[ss.length-1].charAt(0);
				} else if (isBirth(s)) {
					birth = new Event(); s=birth.load(s,f);
					continue;
				} else if (isDeath(s)) {
					death = new Event(); s=death.load(s,f);
					continue;
				} else if (isChildOf(s)) {
					idxParents = ss[ss.length-1];
				} else if (isMarriedTo(s)) {
					idxMarried.add(ss[ss.length-1]);
				} else if (photo==null&&isPhoto(s)) {
					photo=getPhoto(s);
				}
				s=f.readLine();
			}
		}

		public String toString() {return idx+" "+givenName+" "+surname;}
		public String showHTML() {
			String pren="", nom="";
			if (givenName!=null) pren=givenName;
			if (surname!=null) nom=surname;
			String s="<a href=\""+idx+".html\">"+pren+" "+nom+"</a><br>";
			if (birth!=null && birth.date!=null) {
				if (birth.date.getYear()>1919 && (death==null || death.date==null) ) {
				} else {
					s+=birth.date.toHTML();
					if (death!=null && death.date!=null) {
						s+="-"+death.date.toHTML();
					}
				}
			}
/*
			if (photo!=null) {
				s+="<br>";
				<img style="width: 2549px; height: 1912px;" alt=""
						src="file:///home/xtof/Pictures/agnes.jpg"><br>
			}
			*/
			return s;
		}
	}
	void load(String gedcom) throws Exception {
		BufferedReader f = new BufferedReader(new FileReader(gedcom));
		String s=f.readLine();
		for (;;) {
			if (s==null) break;
			if (isIndiv(s)) {
				Individu p = new Individu();
				s=p.load(s,f);
				System.out.println("loaded pers "+allpersons.size()+" "+p);
				continue;
			} else if (isFamille(s)) {
				s=(new Famille()).load(s,f);
				System.out.println("loaded fami "+families.size());
				continue;
			}
			s=f.readLine();
		}
		f.close();
	}

	String children2html(Individu p) {
		String s="";
		for (String fam: p.idxMarried) {
			Famille f = families.get(fam);
			for (String e: f.enfants) {
				Individu fils = allpersons.get(e);
				s+="<a href=\""+fils.idx+".html\">"+fils.givenName+"</a> ";
			}
		}
		return s+"<br>";
	}
	
	void writeHTML(String outdir) throws Exception {
		File d = new File(outdir);
		d.mkdirs();
		for (Individu p : allpersons.values()) {
			String pere="", mere="";
			Individu ppere = null, pmere=null;
			if (p.idxParents!=null) {
				Famille fam = families.get(p.idxParents);
				if (fam.mere!=null) {
					pmere = allpersons.get(fam.mere);
					mere=pmere.showHTML();
				}
				if (fam.pere!=null) {
					ppere = allpersons.get(fam.pere);
					pere=ppere.showHTML();
				}
			}

			PrintWriter f = new PrintWriter(new OutputStreamWriter(new FileOutputStream(outdir+"/"+p.idx+".html"), Charset.forName("UTF-8")));
			f.println("<!--");
			f.println(".. title: Généalogie");
			f.println(".. slug: "+p.idx);
			f.println(".. date: 2013/12/13 16:18:18");
			f.println(".. tags: ");
			f.println(".. link: ");
			f.println(".. description: ");
			f.println("-->");

			f.println("<body>");
			f.println("Arbre simplifié avec les parents et les enfants, centré sur une personne. Cliquez sur un nom pour naviguer:<br><br>");
			f.println("<table style=\"text-align: left; width: 100%;\" border=\"0\" cellpadding=\"2\" cellspacing=\"2\">");
			f.println("<tbody>");
			f.println("<tr>");
			f.println("<td style=\"vertical-align: top; text-align: center; width: 33%;\">");
			f.println(mere);
			f.println("</td>");
			f.println("<td style=\"vertical-align: top; width: 34%;\"><br>");
			f.println("</td>");
			f.println("<td style=\"vertical-align: top; text-align: center; width: 33%;\">");
			f.println(pere);
			f.println("</td>");
			f.println("</tr>");
			f.println("<tr>");
			f.println("<td style=\"vertical-align: top; text-align: center;\">");
			f.println("<hr style=\"width: 100%; height: 2px;\"></td>");
			f.println("</td>");
			f.println("<td style=\"vertical-align: top;\"><br>");
			f.println("</td>");
			f.println("<td style=\"vertical-align: top; text-align: center;\">");
			f.println("<hr style=\"width: 100%; height: 2px;\"></td>");
			f.println("</td>");
			f.println("</tr>");
			f.println("<tr>");
			f.println("<td style=\"vertical-align: top;\"><br>");
			f.println("</td>");
			f.println("<td style=\"vertical-align: top; text-align: center;\">");
			f.println(p.showHTML()+"<br>");
			f.println(children2html(p));
			f.println("</td>");
			f.println("<td style=\"vertical-align: top;\"><br>");
			f.println("</td>");
			f.println("</tr>");
			f.println("</tbody>");
			f.println("</table>");
			f.println("<br>");
			f.println("<br>");
			
			f.println("<hr style=\"width: 100%; height: 4px;\">");
			f.println("<br>");

			// table de 8 colonnes avec toutes les personnes
			f.println("Liste de toutes les personnes de la base de donnée:<br><br>");
			final int ncols=8;
			String[] ev = new String[allpersons.size()];
			int ii=0;
			for (Individu tmpp : allpersons.values()) {
				ev[ii++]=tmpp.surname+" "+tmpp.givenName+" IDX="+tmpp.idx;
			}
			Arrays.sort(ev);
			int nl=ev.length/ncols;
			if (nl*ncols<ev.length) nl++;
			
			f.println("<table style=\"text-align: left; width: 100%;\" border=\"0\" cellpadding=\"2\" cellspacing=\"2\">");
			f.println("<tbody>");
			ii=0;
			for (int l=0;l<nl;l++) {
				f.println("<tr>");
				for (int c=0;c<ncols;c++) {
					f.println("<td style=\"vertical-align: top;\">");
					int j=ev[ii].indexOf(" IDX=");
					f.println("<a href=\""+ev[ii].substring(j+5)+".html\">"+ev[ii].substring(0, j)+"</a>");
					f.println("</td>");
					if (++ii>=ev.length) break;
				}
				f.println("</tr>");
			}
			f.println("</tbody>");
			f.println("</table>");
			f.println("<br>");
			f.println("</body>");
			f.close();
			
			// TODO: copier plutot que dupliquer le code...
			if (p.surname!=null&&p.givenName!=null&&p.surname.toLowerCase().equals("cerisara")&&p.givenName.toLowerCase().equals("christophe")) {
				f = new PrintWriter(new OutputStreamWriter(new FileOutputStream(outdir+"/gene.html"), Charset.forName("UTF-8")));
				f.println("<!--");
				f.println(".. title: Généalogie");
				f.println(".. slug: gene");
				f.println(".. date: 2013/12/13 16:18:18");
				f.println(".. tags: ");
				f.println(".. link: ");
				f.println(".. description: ");
				f.println("-->");

				f.println("<body>");
				f.println("Arbre simplifié avec les parents et les enfants, centré sur une personne. Cliquez sur un nom pour naviguer:<br><br>");
				f.println("<table style=\"text-align: left; width: 100%;\" border=\"0\" cellpadding=\"2\" cellspacing=\"2\">");
				f.println("<tbody>");
				f.println("<tr>");
				f.println("<td style=\"vertical-align: top; text-align: center; width: 33%;\">");
				f.println(mere);
				f.println("</td>");
				f.println("<td style=\"vertical-align: top; width: 34%;\"><br>");
				f.println("</td>");
				f.println("<td style=\"vertical-align: top; text-align: center; width: 33%;\">");
				f.println(pere);
				f.println("</td>");
				f.println("</tr>");
				f.println("<tr>");
				f.println("<td style=\"vertical-align: top; text-align: center;\">");
				f.println("<hr style=\"width: 100%; height: 2px;\"></td>");
				f.println("</td>");
				f.println("<td style=\"vertical-align: top;\"><br>");
				f.println("</td>");
				f.println("<td style=\"vertical-align: top; text-align: center;\">");
				f.println("<hr style=\"width: 100%; height: 2px;\"></td>");
				f.println("</td>");
				f.println("</tr>");
				f.println("<tr>");
				f.println("<td style=\"vertical-align: top;\"><br>");
				f.println("</td>");
				f.println("<td style=\"vertical-align: top; text-align: center;\">");
				f.println(p.showHTML()+"<br>");
				f.println(children2html(p));
				f.println("</td>");
				f.println("<td style=\"vertical-align: top;\"><br>");
				f.println("</td>");
				f.println("</tr>");
				f.println("</tbody>");
				f.println("</table>");
				f.println("<br>");
				f.println("<br>");
				
				f.println("<hr style=\"width: 100%; height: 4px;\">");
				f.println("<br>");

				// table de 8 colonnes avec toutes les personnes
				f.println("Liste de toutes les personnes de la base de donnée:<br><br>");
				ev = new String[allpersons.size()];
				ii=0;
				for (Individu tmpp : allpersons.values()) {
					ev[ii++]=tmpp.surname+" "+tmpp.givenName+" IDX="+tmpp.idx;
				}
				Arrays.sort(ev);
				nl=ev.length/ncols;
				if (nl*ncols<ev.length) nl++;
				
				f.println("<table style=\"text-align: left; width: 100%;\" border=\"0\" cellpadding=\"2\" cellspacing=\"2\">");
				f.println("<tbody>");
				ii=0;
				for (int l=0;l<nl;l++) {
					f.println("<tr>");
					for (int c=0;c<ncols;c++) {
						f.println("<td style=\"vertical-align: top;\">");
						int j=ev[ii].indexOf(" IDX=");
						f.println("<a href=\""+ev[ii].substring(j+5)+".html\">"+ev[ii].substring(0, j)+"</a>");
						f.println("</td>");
						if (++ii>=ev.length) break;
					}
					f.println("</tr>");
				}
				f.println("</tbody>");
				f.println("</table>");
				f.println("<br>");
				f.println("</body>");
				f.close();
			}
		}
	}
	
	public static void main(String args[]) throws Exception {
		if (args.length==0) {
			System.out.println("GEDCOM file must be passed as argument");
			return;
		}
		String gedcom = args[0];
		Gene m = new Gene();
		m.load(gedcom);
		
		m.writeHTML("treeHtml");
	}
}
