/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MDB;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import org.primefaces.context.RequestContext;

/**
 *
 * @author BartKneepkens
 */
@Stateless
public class Service implements Serializable
{
    List<String> results;
    
    public Service(){
        
    }
    
    @PostConstruct
    public void init(){
        results = new ArrayList<>();
    }

    public List<String> getResults() {
        return results;
    }

    public void setResults(List<String> results) {
        this.results = results;
    }
    
    public void addResult(String text){
        this.results.add(text);
//        RequestContext.getCurrentInstance().update("tabel");
    }
}
