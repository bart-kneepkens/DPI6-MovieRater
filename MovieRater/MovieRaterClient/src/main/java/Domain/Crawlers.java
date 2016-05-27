/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

/**
 *
 * @author BartKneepkens
 */
public enum Crawlers
{
    IM("Internet Movie Database"),
    TM("The Movie Database"),
    RT("Rotten Tomatoes");
    
    private final String name;       

    private Crawlers(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : name.equals(otherName);
    }

    public String getName() {
        return name;
    }
}
