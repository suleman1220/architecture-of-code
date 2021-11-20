/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author DELL Inspiron 15
 */
public class Variable {
    public String type;
    public String name;
    
    public Variable(String type, String name){
        this.type = type;
        this.name = name;
    }
    
    public void setType(String type){
        this.type = type;
    }
    
    public void setName(String name){
        this.name = name;
    }
}
