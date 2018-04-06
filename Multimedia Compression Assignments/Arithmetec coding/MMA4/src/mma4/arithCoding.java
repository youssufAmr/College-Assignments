package mma4;

import static com.sun.org.apache.xalan.internal.lib.ExsltMath.power;
import java.io.*;
import static java.lang.Integer.min;
import static java.lang.Math.log;
import java.util.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ESCA
 */
public class arithCoding {

    private void generateprobabilities(String s) {
        double arr[] = new double[129];
        for (int i = 0; i < s.length(); ++i) {
            ++arr[((int) s.charAt(i))];
        }
        for (int i = 0; i < 128; ++i) {
            if (arr[i] > 0.0) {
          
                // System.out.println(arr[i]+" "+i);
                probability.add(new pair(arr[i] / s.length(), ("" + (char) i)));
                //tag.add(new pair(arr[i] / s.length(), ("" + (char) i)));
            }
        }
    }
    private void generatemychars(){
    for(int i=0;i<probability.size();++i)
        mychars.add(probability.get(i).second()+"");
    }
     private void printTagsAndCodeOnFile(String name) {
        try {
            BufferedWriter fw = new BufferedWriter(new FileWriter(name));
            for(int i=0;i<code.size();++i){
              fw.write(code.get(i).toString() + " ");
            }
            fw.write(1002+" ");
            fw.write(len.toString() + " ");
            for (pair i : probability) {
                fw.write(i.first().toString() + " ");
                Integer x = (int) i.second().charAt(0);
                fw.write(x.toString() + " ");
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
     private void getCodeAndTagsfromfile(String path) {
        code.clear();
        probability.clear();
        try {
            Scanner s = new Scanner(new File(path));
            while (s.hasNextDouble()) {
                Double d=s.nextDouble();
                if(d==1002){break;}
                code.add(d);
            }
             len=s.nextInt();
            while (s.hasNextDouble()) {
                pair p = new pair();
                p.setfirst(s.nextDouble());
                int x = s.nextInt();
                p.setsecond(((char) x) + "");
                System.out.println(p.first() + " " + p.second());
                probability.add(p);
            }
      

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    private void generateHighAndLow() {
        sort(probability);
        low.put(probability.get(0).second(), 0.0);
        high.put(probability.get(0).second(), probability.get(0).first());
        for (int i = 1; i < probability.size(); ++i) {
            double pre = high.get(probability.get(i - 1).second());
            low.put(probability.get(i).second(), pre);
            high.put(probability.get(i).second(), pre + probability.get(i).first());
        }
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

    private void sort(ArrayList<pair> p) {
        for (int i = 0; i < p.size(); ++i) {
            for (int j = 0; j < p.size(); ++j) {
                if (p.get(i).second().compareTo(p.get(j).second())>0) {
                    p.get(i).swap(p.get(j));
                }
            }
        }
    }

    private void printlist(ArrayList<pair> l) {
        System.out.println(l.size());
        for (int i = 0; i < l.size(); ++i) {
            System.out.println(l.get(i).second() + " , " + l.get(i).first());
        }
    }

    private void E2() {
        start = (start - 0.5) * 2;
        end = (end - 0.5) * 2;
    }

     private void E1() {
        start *= 2;
        end *= 2;
    }

    private boolean validRange(double start, double end) {
        if (start <= 0.5 && end >= 0.5) {
            return true;
        }
        return false;
    }
    private void clear(){
    probability.clear();
    high.clear();
    low.clear();
    start=0;end=1.0;
    len=0;
    }
    
    // 0.5 = 5 / 10 - 0.25 = 25 / 100 10^2
    private Double todec(String binary){
        double d=0.0;
        for(int i= binary.length() - 1; i >= 0; --i){
             if(binary.charAt(i) == '1')
                 d += power(2,i);
        }
        
        d /= power(2,binary.length());
       return d;
    }
    private String getDesiredchar(double d){
       for(int i=0;i<mychars.size();++i){
           //System.out.println(mychars.get(i)+"- "+high.get(mychars.get(i))+" -"+low.get(mychars.get(i)));
           
       if(high.get(mychars.get(i))>=d && low.get(mychars.get(i))<=d)
           return mychars.get(i);
       }
        return null;
    }
    public void compress(String path) {
        this.clear();
        String s=getStringfromfile(path);
        this.generateprobabilities(s);
        this.generateHighAndLow();
        this.printlist(probability);
        System.out.println(high+"\n"+low);
        len=s.length();
        String seg="";
        int k=0;
        for(int i=0;i<len;++i){
          seg+=s.charAt(i);
        if(seg.length()==5){ 
        code.add(compressLittleString(seg));
        seg="";
        }
        }
        if(seg.length()>0)
           code.add(compressLittleString(seg)); 
        this.printTagsAndCodeOnFile(path);
    }
    private double compressLittleString(String ls){
        start = 0.0;
        end = 1.0;
     for (int i = 0; i < ls.length(); ++i) {
            double st = 0.0, e = 0.0;
            st = (start + ((end - start) * low.get(ls.charAt(i) + "")));
            e = (start + ((end - start) * high.get(ls.charAt(i) + "")));
            start = st;
            end = e;
          }
     System.out.println(start+" "+end+" "+ls);
     return((start+end)/2.0);
    }
    public void decompress(String path){
          this.clear();
        this.getCodeAndTagsfromfile(path);
        System.out.println(code);
        this.generateHighAndLow();
          this.generatemychars();
          String decompressed = "";
            int k=0;
          while(len>0){    
        decompressed +=decompressLittleString(code.get(k),min(5,len));
        System.out.println(len);
        len-=5;
        ++k;
          }
        writeStringonFile(decompressed ,path);
    }
    private String decompressLittleString(Double code,Integer len){
     String c,desiredChar="",res = "";
     start=0.0;
     end=1.0;
    for(int i=0;i<len;){
           Double mycode;
        mycode=((code-start)/(end-start));
        desiredChar=getDesiredchar(mycode);
        //System.out.println(desiredChar);
        res+=desiredChar;
        ++i;
         double st = 0.0, e = 0.0;
            st = (start + ((end - start) * low.get(desiredChar)));
            e = (start + ((end - start) * high.get(desiredChar)));
            start = st;
            end = e;
          
        }
    return res;
    }
    //Entities
    private double start,end;
    private Integer len;
    //private Double code ;
    private  ArrayList<Double> code = new ArrayList<>(); 
    private ArrayList<pair> probability = new ArrayList<>(); 
    private ArrayList<String> mychars = new ArrayList<>();
    private Map<String, Double> high = new HashMap<String, Double>();
    private Map<String, Double> low = new HashMap<String, Double>();
}
