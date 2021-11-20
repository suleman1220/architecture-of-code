/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author DELL Inspiron 15
 */
public class Method {
    public String name;
    public String scope;
    
    public Method(String name, String scope){
        this.name = name;
        this.scope = scope;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setScope(String scope){
        this.scope = scope;
    }
}
