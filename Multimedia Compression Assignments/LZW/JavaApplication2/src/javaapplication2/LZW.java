
package javaapplication2;

import java.util.*;
import java.io.*;
public class LZW {
	public LZW() {

	}
        public void writeStringonFile(String s,String path){
            try {
			BufferedWriter fw= new BufferedWriter(new FileWriter(path));
			fw.write(s);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        }
	public void compress(String x) {
                tags.clear();
		this.setinputtext(x);
		for (int i = 0; i < myinput.length(); ++i) {
			String mysegmant = "", myrealseg = "";
			int j = 0, loc = -1;
			mysegmant += myinput.charAt(i);
			while (found(mysegmant) != -1) {
				++j;
				loc = found(mysegmant);
				myrealseg = mysegmant;
				if (i + j > myinput.length() - 1)
					break;
				mysegmant += myinput.charAt(i + j);
			}
			if (myrealseg.length() == 1)
				tags.add((int) myrealseg.charAt(0));
			else {
				tags.add(loc + 128);
			}
			// System.out.println(j+" "+i+" "+mysegmant);
			if (j != 0)
				--j;
			i += j;
			dictionary.add(mysegmant);
		}
		//this.printTags();
		printTagsOnFile("E:\\LZW\\tags.txt");
	}
        public String getStringfromfile(String path){
                 String res="";
                 try {
			Scanner s=new Scanner (new File(path));
			while(s.hasNextLine()) {
				res+=(s.nextLine());
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
                 return res;
        }
	public void printTagsOnFile(String name) {
		try {
			BufferedWriter fw= new BufferedWriter(new FileWriter(name));
			for(Integer i : tags) {
				fw.write(i.toString()+" ");
			}
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void addtag(int k) {
		tags.add(k);
	}
	public void clear() {
		tags.clear();
                dictionary.clear();
	}
	public String printTags() {
            String res="";
		res+="--\n";
		for (int k : tags) {
			res+=String.format("( %d )",k);
                        res+=("\n");
		}
		res+=("--");
                return res;
	}

	public void printDictionary() {
		System.out.print("--\n");
		int y = 128;
		for (String k : dictionary) {
			System.out.print(y + ") " + k + "\n");
			y++;
		}
		System.out.print("\n--");
	}
        public boolean isthereisatag(){
        return tags.isEmpty();
        }
	private void readtagsfromfile(String name) {
            tags.clear();
		try {
			Scanner s=new Scanner (new File(name));
			while(s.hasNextInt()) {
				tags.add(s.nextInt());
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String decompress(String path) {
		readtagsfromfile(path);
		dictionary.clear();
		int last = 0, current = 0;
		String s = "", dc = "";
		for (int i = 0; i < tags.size(); ++i) {
			s="";
			boolean added=false;
			current = tags.get(i);
			if (current < 128) {
				dc += (char)(current);
			} else {
				current -= 128;
				if (current >= dictionary.size()) {
					if(last>=128) {
						s = dictionary.get(last-128);
						}else {
						s+=(char)(last);	
						}
					s += s.charAt(0);
					dictionary.add(s);
					dc += s;
					added=true;
				} else {
					dc += dictionary.get(current);
				}
				current+=128;
			}
			
			if (i > 0 && !added) {
				s="";
				if(last>=128) {
				s = dictionary.get(last-128);
				}else {
				s+=(char)(last);	
				}
				if(current>=128) {
				s += dictionary.get(current-128).charAt(0);
				}else {
				s+=(char)(current);	
				}
				dictionary.add(s);
			}
			last = current;
		}
		//System.out.println("there is your answer :\n"+dc);
		return dc;
	}
	

	private int found(String x) {
		if (x.length() == 1)
			return (int) x.charAt(0);
		for (int k = 0; k < dictionary.size(); ++k) {
			String b = dictionary.get(k);
			// System.out.println(b+" in fun");
			if (b.equals(x)) {
				return k;
			}
		}
		return -1;
	}

	private void setinputtext() {
		myinput = in.nextLine();
	}

	private void setinputtext(String x) {
		myinput = x;
	}

	// Entities

	private Scanner in = new Scanner(System.in);
	private ArrayList<String> dictionary = new ArrayList<>();
	private ArrayList<Integer> tags = new ArrayList<>();
	private String myinput;
}
