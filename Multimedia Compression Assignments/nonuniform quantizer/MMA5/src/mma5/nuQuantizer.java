/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mma5;

import static com.sun.org.apache.xalan.internal.lib.ExsltMath.power;
import java.io.*;
import java.util.*;

/**
 *
 * @author ESCA
 */
public class nuQuantizer {
    
    public nuQuantizer(){}
    public nuQuantizer(NewJFrame g){gui=g;}
    public nuQuantizer(ArrayList<Integer> d,int ll){data=d;numoflevels=ll;}
    
    private Double calcDis(Double d1,Double d2)
    {
     
     Double dis;
     dis=(d1-d2)*(d1-d2);
     return dis;
    }
    private Integer calcAvg(ArrayList<Integer> l)
    {
    Integer avg=0;
    for(Integer k : l)
       avg+=k;
    avg/=l.size();
    return avg;
    }
    private void construct(ArrayList<Integer> l,int avg,int lvl)
    {
     if(lvl == numoflevels){
         ArrayList<Integer> l1=new ArrayList<>();
     ArrayList<Integer> l2=new ArrayList<>();
     for(int h : l){
        if(calcDis((double)h,(double)(avg-1))>calcDis((double)h,(double)(avg+1)))
            l1.add(h);
        else
            l2.add(h);
        }
         int avg1=calcAvg(l1),avg2=calcAvg(l2),newavg1,newavg2,c=0;
       do{
         for(int h : l){
           if(calcDis((double)h,(double)(avg1))>calcDis((double)h,(double)(avg2)))
              l1.add(h);
           else
              l2.add(h);
         }
         newavg1=calcAvg(l1);newavg2=calcAvg(l2);
         if(newavg1!=avg1 || newavg2!=avg2){
         avg1=newavg1;avg2=newavg2;
         }else{
         break;
         }
         if(c==100){
         break;
         }
         ++c;
        }while(true);
       // yourpoints.add(avg1);
        yourpoints.add(avg2);
     }else{
     ArrayList<Integer> l1=new ArrayList<>();
     ArrayList<Integer> l2=new ArrayList<>();
     for(int h : l){
        if(calcDis((double)h,(double)(avg-1))>calcDis((double)h,(double)(avg+1)))
            l1.add(h);
        else
            l2.add(h);
        }
      int avg1=calcAvg(l1),avg2=calcAvg(l2);
      construct(l1,avg1,lvl+1);
      construct(l2,avg2,lvl+1);
      }
    }
     
    private void buildquantizer(){
    int av=calcAvg(data);
    construct(data,av,0);
    Collections.sort(yourpoints);
    ranges.add(0);
    for(int h=1;h<yourpoints.size();++h)
    {
    ranges.add((yourpoints.get(h-1)+yourpoints.get(h))/2);
    }
    ranges.add(255);
    for(int h=0;h<yourpoints.size();++h){
    gui.addRawToTable(h, ranges.get(h), ranges.get(h+1), yourpoints.get(h));
    }
    //System.out.println();
    /*for(int h:ranges)
        System.out.print(h+" ");*/
    }
    public String compressWithFiles(String path){
        readdatafromfile(path);
        String res=compress(data,numoflevels);
        printQuantizerOnFile(path);
        return res;
    }
    public String decompressthefile(String path)
    {
        readquantizerfromfile(path);
        return decompress();
    }
    public String compress(ArrayList<Integer> d,int ll)
    {
    setData(d);
    setlevels(ll);
    buildquantizer();
    String s="";
    for(int i=0;i<data.size();++i){
        for(int h=1;h<ranges.size();++h){
           if(data.get(i)<ranges.get(h)){
             s+=(toBinary(h-1));
             break;
           }
        }
    }
    compCode=s;
    return s;
    }
    public String decompress(){
    
    String res="";
    for(int i=0;i<compCode.length();i+=numoflevels){
        if(i>0)
            res+=" - ";
    String s="";
          for(int k=0;k<numoflevels;++k)
              s+=(compCode.charAt(i+k));
        Integer x=toDic(s);
        x=yourpoints.get(x);
        res+=x.toString();
        decompData.add(x);
    }
    return res;
    }
    private String toBinary(int num){
    String bin="";
    do{
    if(num%2==1)
        bin+='1';
    else
        bin+='0';
    num/=2;
    }while(num!=0);
    for(int n=bin.length();n<numoflevels;++n){
    bin+='0';
    }
    return new StringBuilder(bin).reverse().toString();
    }
    private int toDic(String bin)
    {
       int dic=0;
       bin=new StringBuilder(bin).reverse().toString();
       for(int i=0;i<bin.length();++i){
       if(bin.charAt(i)=='1')
       dic+=power(2,i);
       }
       return dic;
    }
    public Double calcMSE(){
    Double mse=0.0;
    for(int i=0;i<decompData.size();++i)
    {
     mse+=((decompData.get(i)-data.get(i))*(decompData.get(i)-data.get(i)));
    }
    return mse/data.size();
    }
     
     public void printQuantizerOnFile(String name) {
		try {
			BufferedWriter fw= new BufferedWriter(new FileWriter(name));
                        fw.write(numoflevels.toString()+" ");
			for(Integer i : yourpoints) {
				fw.write(i.toString()+" ");
			}
                         fw.write("x"+compCode);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
      private void readdatafromfile(String name) {
              data.clear();
		try {
			Scanner s=new Scanner (new File(name));
                        numoflevels=s.nextInt();
			while(s.hasNextInt()) {
				data.add(s.nextInt());
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
     private void readquantizerfromfile(String name) {
            yourpoints.clear();
            ranges.clear();
		try {
			Scanner s=new Scanner (new File(name));
                        numoflevels=s.nextInt();
			while(s.hasNextInt()) {
				yourpoints.add(s.nextInt());
			}
                        compCode=s.nextLine();
                        compCode=compCode.substring(0);
                        System.out.print(compCode);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
                ranges.add(0);
    for(int h=1;h<yourpoints.size();++h)
    {
    ranges.add((yourpoints.get(h-1)+yourpoints.get(h))/2);
    }
    ranges.add(255);
            
	}
    public void setData(ArrayList<Integer> d){data=d;}
    public void setlevels(int ll){numoflevels=ll;}
    ///
    private ArrayList<Integer> data=new ArrayList<>();
    private ArrayList<Integer> decompData=new ArrayList<>();
    private ArrayList<Integer> yourpoints=new ArrayList<>();
    private ArrayList<Integer> ranges=new ArrayList<>();
    private NewJFrame gui;
    private String compCode;
    private Integer numoflevels;
}
