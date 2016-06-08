/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Domain.CrawlResult;
import Domain.Crawlers;
import Domain.EnrichedRating;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author BartKneepkens
 */
@Stateless
public class RatingDAO
{
    @PersistenceContext(unitName="MovieRaterClientPU")
    private EntityManager em;
    
    public List<EnrichedRating> findAll(){
        return em.createNamedQuery("EnrichedRating.findAll").getResultList();
    }
    
    public EnrichedRating find(Long id){
        return em.find(EnrichedRating.class, id);
    }
    
    public void add(EnrichedRating er){
        for (CrawlResult cr : er.getQueryResult().getCrawlResults()) {
            em.persist(cr);
        }
        
        em.persist(er.getQueryResult());
        em.persist(er);
    }
    
    public void edit(EnrichedRating er){
        em.merge(er);
    }
    
    public void remove(EnrichedRating er){
        em.remove(er);
    }
    
}
