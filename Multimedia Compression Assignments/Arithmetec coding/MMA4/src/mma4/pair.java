/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mma4;

/**
 *
 * @author ESCA
 */
public class pair {
   public pair(){}
   public pair(Double p,String name){
       f=p;s=name;
   }
  public void swap(pair b){
   pair tmp=new pair(0.0,"");
   tmp.setfirst(this.first());
   tmp.setsecond(this.second());
   this.setfirst(b.first());
   this.setsecond(b.second());
   b.setfirst(tmp.first());
   b.setsecond(tmp.second());
   }
   public Double first(){return f;} 
   public String second(){return s;}
   public void setfirst(Double d){f=d;}
   public void setsecond(String n){s=n;}
   //intities
   private Double f;
   private String s;
}

