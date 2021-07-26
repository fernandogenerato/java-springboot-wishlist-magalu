package br.com.magalu.wishlist.gateways.nosql;


import br.com.magalu.wishlist.gateways.nosql.documents.CustomerCollection;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistRepository extends MongoRepository<CustomerCollection, String> {

}
