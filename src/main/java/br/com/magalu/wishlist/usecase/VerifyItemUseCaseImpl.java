package br.com.magalu.wishlist.usecase;

import br.com.magalu.wishlist.exceptions.CustomerNotFoundException;
import br.com.magalu.wishlist.gateways.nosql.documents.CustomerCollection;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Log4j2
@Component
public class VerifyItemUseCaseImpl implements VerifyItemUseCase {
    private static final String CUSTOMER_NOT_FOUND = "Customer not found, check customer ID.";

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Boolean execute(String customerId, String itemId) {
        log.info("execute : customerId : {} itemId : {}", customerId, itemId);
        return getCustomer(customerId).getWishList()
                .stream()
                .filter(i -> i.getProductId().equals(itemId))
                .count() > 0;

    }

    private CustomerCollection getCustomer(String customerId) {
        log.info("getCustomer : customerId : {}", customerId);
        return Optional.ofNullable(mongoTemplate.findById(customerId, CustomerCollection.class))
                .orElseThrow(() -> new CustomerNotFoundException(CUSTOMER_NOT_FOUND));
    }


}
