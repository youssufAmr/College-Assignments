/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mma6;

/**
 *
 * @author ESCA
 */
public class vector {
   public vector(){
    
    }
   public vector(int fd,int sd){
    Vector=new Integer[fd][sd];
    firstDim=fd;
    secondDim=sd;
    }
   public String printVector(){
       String res="";
       res+="------------\n";
     for(int i=0;i<firstDim;++i){
         res+="{";
        for(int j=0;j<secondDim;++j){
           res+=Vector[i][j].toString()+" ";
        }
         res+="}";
         res+="\n";
     }
     res+="------------\n";
     return res;
   }
   public vector add(int h){
    vector b=new vector(firstDim,secondDim);
    for(int i=0;i<firstDim;++i){
       for(int j=0;j<secondDim;++j){
           b.setCell(i, j, this.getCell(i, j)+h);
       }
    }
    return b;
   }
   public boolean equal(vector b){
    for(int i=0;i<firstDim;++i){
       for(int j=0;j<secondDim;++j){
         if(this.getCell(i,j)!=b.getCell(i,j))
             return false;
       }
    }
    return true;
   }
    public void setCell(int x,int y ,Integer value){ Vector[x][y]=value;}
    public Integer getCell(int x,int y){return Vector[x][y];}
    
    ///
    private Integer Vector[][] , firstDim,secondDim; 
 
}
