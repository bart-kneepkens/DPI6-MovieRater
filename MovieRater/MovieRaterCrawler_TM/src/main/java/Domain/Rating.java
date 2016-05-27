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
// POJO
public class Rating
{
    private double rating;
    private int weight;

    public Rating(double rating, int weight) {
        this.rating = rating;
        this.weight = weight;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
    
    
}
