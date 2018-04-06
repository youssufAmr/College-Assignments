/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mma6;

import java.io.*;
import java.util.*;

/**
 *
 * @author ESCA
 */
public class vectorQuantizer {
    
    public vectorQuantizer(int fd, int sd) {
        this.fd = fd;
        this.sd = sd;
    }
    public void compress(String path,int NOV){
        setNumofvectors(NOV);
        makevectors(path);
        buildquantizer();
        writecode();
        printDatarOnFile("E:\\mmA\\vector quantizer\\out.txt");
    }
    public void decompress(String path){
        readdatafromfile(path);
        int height=H;
        int width=W;
        int ind=0;
        for(int i=0;i<height;++i){
           for(int j=0;j<width;++j){
               
               int index=code.get(ind);
               vector c=codeBook.get(index);
               for(int k=0;k<fd;++k){
                  for(int t =0;t<sd;++t){
                     if(i+k<height && j+t <width){
                     img.setPixel(i+k, j+t, c.getCell(k, t));
                     }
                  }
               }
               ind++;
               j+=sd-1;
           }
           i+=(fd-1);
        }
        img.writeimage();
    }
    ///
    private void makevectors(String path){
        img.getimage(path);
        int height=img.getHeight();
        int width=img.getWidth();
        for(int i=0;i<height;++i){
           for(int j=0;j<width;++j){
               vector v=new vector(fd,sd);
               for(int k=0;k<fd;++k){
                  for(int t=0;t<sd;++t){
                     if(i+k<height && j+t <width){
                     v.setCell(k, t, img.getPixel(i+k, j+t));
                     }else{
                     v.setCell(k, t, 0);
                     }
                  }
               }
               vectorsPool.add(v);
               j+=sd-1;
           }
           i+=(fd-1);
        }
        //this.print();
       // System.out.println(this.calcAvg(vectorsPool).printVector());
    }
    private void print(){
    for(vector v :vectorsPool){
    System.out.println(v.printVector());
    }
    }
    private vector calcAvg(ArrayList <vector> vectorslist){
        vector avg=new vector(fd,sd);
       for(int i=0;i<fd;++i){
           for(int j=0;j<sd;++j){
               avg.setCell(i, j, 0);
               for(int k=0;k<vectorslist.size();++k){
                   int value=avg.getCell(i, j)+vectorslist.get(k).getCell(i, j);
                   avg.setCell(i, j, value);
               }
               int value=0;
               if(vectorslist.size()!=0)
                  value=avg.getCell(i, j)/vectorslist.size();
               avg.setCell(i, j, value);
           }
       }
       return avg;
    }
    private int calcDis(vector a,vector b){
    int dis=0;
     for(int i=0;i<fd;++i){
           for(int j=0;j<sd;++j){
               int value=a.getCell(i, j)-b.getCell(i, j);
               dis+=value*value;
           }
     }
    return dis;
    }
    private void construct(ArrayList<vector> l,vector avg,int lvl){
   if(lvl == numofvectors){
         ArrayList<vector> l1=new ArrayList<>();
     ArrayList<vector> l2=new ArrayList<>();
     for(vector h : l){
        if(calcDis(h,(avg.add(-1)))>calcDis(h,(avg.add(1))))
            l1.add(h);
        else
            l2.add(h);
        }
         vector avg1=calcAvg(l1),avg2=calcAvg(l2),newavg1=new vector(fd,sd),newavg2=new vector(fd,sd);
         int c=0;
         
       do{
         l1.clear();
         l2.clear();
         for(vector h : l){
           if(calcDis(h,avg1)>calcDis(h,(avg2)))
              l1.add(h);
           else
              l2.add(h);
         }
         newavg1=calcAvg(l1);newavg2=calcAvg(l2);
         if(newavg1.equal(avg1) && newavg2.equal(avg2)){
            break;
         }else{
            avg1=newavg1;avg2=newavg2;
         }
         if(c==5){
         break;
         }
         ++c;
        }while(true);
       // yourpoints.add(avg1);
         codeBook.add(avg1);
         codeBook.add(avg2);
     }else{
     ArrayList<vector> l1=new ArrayList<>();
     ArrayList<vector> l2=new ArrayList<>();
     for(vector h : l){
        if((calcDis(h,avg.add(-1)))>calcDis(h,(avg.add(1))))
            l1.add(h);
        else
            l2.add(h);
        }
      vector avg1=calcAvg(l1),avg2=calcAvg(l2);
      construct(l1,avg1,lvl+1);
      construct(l2,avg2,lvl+1);
      }
    }
    public void buildquantizer(){
    vector av=calcAvg(vectorsPool);
    construct(vectorsPool,av,1);
//
    }
    private void setNumofvectors(int numofvectors) {
        numofvectors=(int) (Math.log10(numofvectors)/Math.log10(2));
        //System.out.print(numofvectors);
        this.numofvectors = numofvectors;
    }
    private void writecode(){
     int height=img.getHeight();
        int width=img.getWidth();
        for(int i=0;i<height;++i){
           for(int j=0;j<width;++j){
               vector v=new vector(fd,sd);
               for(int k=0;k<fd;++k){
                  for(int t=0;t<sd;++t){
                     if(i+k<height && j+t <width){
                     v.setCell(k, t, img.getPixel(i+k, j+t));
                     }else{
                     v.setCell(k, t, 0);
                     }
                  }
               }
               int min=7000000;
               int c=0,ind=0;
               for(vector h : codeBook){
                   int dis=calcDis(h,v);
                 if(dis<min){
                     min=dis;
                     c=ind;
                 }
                 ind++;
               }
               code.add(c);
               j+=sd-1;
           }
           i+=(fd-1);
        }
}
    private void printDatarOnFile(String name) {
		try {
			BufferedWriter fw= new BufferedWriter(new FileWriter(name));
                        fw.write(img.getHeight().toString()+" ");
                        fw.write(img.getWidth().toString()+" ");
                        fw.write(fd.toString()+" ");
                        fw.write(sd.toString()+" ");
                        numofvectors= (int)Math.pow(2,numofvectors);
                        fw.write(numofvectors.toString()+" ");
			for(vector v : codeBook) {
                            for(int i=0;i<fd;++i){
                                for(int j=0;j<sd;++j){
                                   fw.write(v.getCell(i,j).toString()+" ");   
                                }
                            }
			}
                        for(Integer i : code)
                         fw.write(i.toString()+" ");  
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    private void readdatafromfile(String name) {
              codeBook.clear();
              code.clear();
		try {
			Scanner s=new Scanner (new File(name));
                        H=s.nextInt();
                        W=s.nextInt();
                        fd=s.nextInt();
                        sd=s.nextInt();
                        numofvectors=s.nextInt();
                        for(int k=0;k<numofvectors;++k){
                            vector v = new vector(fd,sd);
			for(int i=0;i<fd;++i){
                            for(int j=0;j<sd;++j){
                                int value=s.nextInt();
                               v.setCell(i,j,value);
                            }
                        }
                          codeBook.add(v);
                        }
                        while(s.hasNextInt())
                            code.add(s.nextInt());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    ///Entities
    private int H,W;
    private ArrayList <vector> vectorsPool=new ArrayList <>();
    private ArrayList <vector> codeBook=new ArrayList <>();
    private ArrayList <Integer> code=new ArrayList <>();
    private Integer fd , sd,numofvectors;
    private image img=new image();
}
