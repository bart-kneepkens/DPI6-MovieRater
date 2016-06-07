/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Domain.CrawlResult;
import Domain.Crawlers;
import Domain.EnrichedRating;
import Domain.QueryResult;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author BartKneepkens
 */
@Stateless
@Named
public class RatingDAO
{
    @PersistenceContext(unitName="MovieRaterClientPU")
    EntityManager em;
    
    
    public void test(){
        
        CrawlResult cr = new CrawlResult();
        cr.setCrawler(Crawlers.IM);
        cr.setRating(5);
        cr.setWeight(222);
        em.persist(cr);
        
//        EnrichedRating er = new EnrichedRating();
//        er.setId(0L);
//        
//        QueryResult qr = new QueryResult();
//        qr.setQuery("Blablba query");
//        qr.getCrawlResults().add(cr);
//        er.setQueryResult(qr);
//        er.setUserRating(5);
//        
//        em.persist(qr);
//        em.persist(er);
    }
    
}
