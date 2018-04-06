/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mma3;

import java.io.*;
import java.util.*;

/**
 *
 * @author ESCA
 */
public class hufman {

    private void generateprobabilities(String s) {
        double arr[] = new double[129];
        for (int i = 0; i < s.length(); ++i) {
            ++arr[((int) s.charAt(i))];
        }
        for (int i = 0; i < 128; ++i) {
            if (arr[i] > 0.0) {
                // System.out.println(arr[i]+" "+i);
                probability.add(new pair(arr[i] / s.length(), ("" + (char) i)));
                tag.add(new pair(arr[i] / s.length(), ("" + (char) i)));
            }
        }
        /*
        for(int i=0;i<probability.size();++i){ 
        probability.get(i).setfirst(probability.get(i).first()+1);
        }*/
    }

    private void writeStringonFile(String s, String path) {
        try {
            BufferedWriter fw = new BufferedWriter(new FileWriter(path));
            fw.write(s);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getStringfromfile(String path) {
        String res = "";
        try {
            Scanner s = new Scanner(new File(path));
            while (s.hasNextLine()) {
                res += (s.nextLine());
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;
    }

    private Integer getindex(String s) {
        s = s.substring(1);
        Integer i = 0;
        return i.parseInt(s);
    }

    private void coding() {
        this.generatecode(logtree.get(logtree.size() - 1), "");
    }

    private void generatecode(String node, String ncode) {
        if (node.length() == 1) {
            code.put(node, ncode);
            decode.put(ncode, node);
        } else {
            node = logtree.get(this.getindex(node));
            String left = node.split(",")[0];
            String right = node.split(",")[1];
            generatecode(left, ncode + "1");
            generatecode(right, ncode + "0");
        }
    }

    private void generatelog() {
        ArrayList<pair> list2 = new ArrayList<>();
        String s = "";
        while (probability.size() > 1) {
            s = "";
            this.sort(probability);
            fill(list2, probability, probability.size());
            fill(probability, list2, list2.size() - 2);
            Double d;
            s = list2.get(list2.size() - 1).second() + "," + list2.get(list2.size() - 2).second();
            d = list2.get(list2.size() - 1).first() + list2.get(list2.size() - 2).first();
            logtree.add(s);
            Integer e = logtree.size() - 1;
            s = "e" + e.toString();
            pair p = new pair(d, s);
            probability.add(p);
        }
        logtree.add(s);
    }

    private void fill(ArrayList<pair> p, ArrayList<pair> p2, int toind) {
        p.clear();
        for (int i = 0; i < toind; ++i) {
            p.add(p2.get(i));
        }
    }

    private void sort(ArrayList<pair> p) {
        for (int i = 0; i < p.size(); ++i) {
            for (int j = 0; j < p.size(); ++j) {
                if (p.get(i).first() > p.get(j).first()) {
                    p.get(i).swap(p.get(j));
                } else if (p.get(i).first() == p.get(j).first() && i != j) {
                    if (p.get(i).second().compareTo(p.get(j).second()) != 1) {
                        p.get(i).swap(p.get(j));
                    }
                }
            }
        }
    }

    private void printlist() {
        System.out.println(tag.size());
        for (int i = 0; i < tag.size(); ++i) {
            System.out.println(tag.get(i).second() + " , " + tag.get(i).first());
        }
    }

    private void printlog() {
        for (int i = 0; i < logtree.size(); ++i) {
            System.out.println(logtree.get(i));
        }
    }

    private void clear() {
        logtree.clear();
        probability.clear();
        code.clear();
        tag.clear();
        decode.clear();
    }

    private void printTagsAndCodeOnFile(String name, String code) {
        try {
            BufferedWriter fw = new BufferedWriter(new FileWriter(name));
            for (pair i : tag) {
                fw.write(i.first().toString() + " ");
                Integer x = (int) i.second().charAt(0);
                fw.write(x.toString() + " ");
            }
            fw.write("xx" + code);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getCodeAndTagsfromfile(String path) {
        String res = "";
        tag.clear();
        try {
            Scanner s = new Scanner(new File(path));

            while (s.hasNextDouble()) {
                pair p = new pair();
                p.setfirst(s.nextDouble());
                int x = s.nextInt();
                p.setsecond(((char) x) + "");
                System.out.println(p.first() + " " + p.second());
                tag.add(p);
            }
            res = (s.nextLine());
            res = res.substring(3);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;
    }

    
    
    public void compress(String path) {
        clear();
        String c = "";
        String s = this.getStringfromfile(path);
        generateprobabilities(s);
        generatelog();
        coding();
        for (int i = 0; i < s.length(); ++i) {
            c += code.get("" + s.charAt(i));
        }

       // System.out.println("compress : " + s);
        System.out.println(code);
       // System.out.println(logtree);
        this.printTagsAndCodeOnFile(path, c);
        //return c;
    }

    public void decompress(String path) {
        this.clear();
        String s, r = "", seg = "";
        s = this.getCodeAndTagsfromfile(path);
        probability = tag;
        generatelog();
        coding();
        for (int i = 0; i < s.length(); ++i) {
            seg += s.charAt(i);
            if (decode.containsKey(seg)) {
                r += decode.get(seg);
                seg = "";
            }
            //System.out.println(seg);
        }

        this.writeStringonFile(r, path);
    }
    //Entities
    public Map code = new HashMap();
    private Map decode = new HashMap();
    private ArrayList<String> logtree = new ArrayList<>();
    private ArrayList<pair> tag = new ArrayList<>();
    private ArrayList<pair> probability = new ArrayList<>();

}
