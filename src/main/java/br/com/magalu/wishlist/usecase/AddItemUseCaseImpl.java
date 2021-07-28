package br.com.magalu.wishlist.usecase;


import br.com.magalu.wishlist.entities.Item;
import br.com.magalu.wishlist.exceptions.CustomerNotFoundException;
import br.com.magalu.wishlist.exceptions.MaximumLimitException;
import br.com.magalu.wishlist.gateways.nosql.WishlistRepository;
import br.com.magalu.wishlist.gateways.nosql.documents.CustomerCollection;
import br.com.magalu.wishlist.gateways.nosql.documents.ItemCollection;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Log4j2
@Component
public class AddItemUseCaseImpl implements AddItemUseCase {

    private static final String LIMIT_ERROR_MESSAGE = "Maximum Limit of 20 Products.";
    private static final String SUCCESS_MESSAGE = "Item added to wishlist.";
    private static final String CUSTOMER_NOT_FOUND = "Customer not found, check customer ID.";
    private static final Integer MAX_SIZE_WISHLIST = 20;

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public void execute(String customerId, Item item) {
        log.info("execute : customerId : {} , item : {}", customerId, item.toString());
        CustomerCollection customerCollection = getCustomer(customerId);
        if (customerCollection.getWishList().size() == MAX_SIZE_WISHLIST)
            throw new MaximumLimitException(LIMIT_ERROR_MESSAGE);
        customerCollection.getWishList().add(convertToMongo(item));
        wishlistRepository.save(customerCollection);
        log.info(SUCCESS_MESSAGE, item.toString());
    }

    private CustomerCollection getCustomer(String customerId) {
        log.info("getCustomer : customerId : {}", customerId);
        return Optional.ofNullable(mongoTemplate.findById(customerId, CustomerCollection.class))
                .orElseThrow(() -> new CustomerNotFoundException(CUSTOMER_NOT_FOUND));
    }

    private ItemCollection convertToMongo(Item item) {
        log.info("convertToMongo : item{}", item.toString());
        return ItemCollection.builder()
                .productId(item.getProductId())
                .title(item.getTitle())
                .image(item.getImage())
                .price(item.getPrice()).build();
    }
}
