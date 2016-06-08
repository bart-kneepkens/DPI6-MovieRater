/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import DAO.RatingDAO;
import Domain.EnrichedRating;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author BartKneepkens
 */
@Stateless
public class RatingService implements Serializable
{
    @Inject
    private RatingDAO dao;
    
    public void addRating(EnrichedRating er){
        er.setDate(new Date());
        dao.add(er);
    }
    
    public void editRating(){
        
    }
    
    public List<EnrichedRating> getHistory(){
        return dao.findAll();
    }
}
