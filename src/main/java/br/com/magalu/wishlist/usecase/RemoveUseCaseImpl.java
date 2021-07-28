package br.com.magalu.wishlist.usecase;

import br.com.magalu.wishlist.entities.Item;
import br.com.magalu.wishlist.exceptions.CustomerNotFoundException;
import br.com.magalu.wishlist.exceptions.ProductNotFoundException;
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
public class RemoveUseCaseImpl implements RemoveUseCase {
    private static final String CUSTOMER_NOT_FOUND = "Customer not found, check customer ID.";
    private static final String PRODUCT_NOT_FOUND = "Product not found, check product ID.";
    private static final String SUCCESS_MESSAGE = "Item removed from wishlist.";

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void execute(String customerId, Item item) {
        log.info("execute : customerId : {} item : {}", customerId, item.toString());
        CustomerCollection customerCollection = getCustomer(customerId);
        ItemCollection itemToRemove = getItemCollection(item, customerCollection);
        customerCollection.getWishList().remove(itemToRemove);
        wishlistRepository.save(customerCollection);
        log.info(SUCCESS_MESSAGE);
    }

    private ItemCollection getItemCollection(Item item, CustomerCollection customerCollection) {
        log.info("getItemCollection : item : {} , customerCollection : {}", item.toString(), customerCollection.toString());
        return Optional.ofNullable(customerCollection
                .getWishList()
                .stream()
                .filter(i -> i.getProductId().equals(item.getProductId()))
                .findFirst().orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND))).get();
    }

    private CustomerCollection getCustomer(String customerId) {
        log.info("getCustomer : customerId {}", customerId);

        return Optional.ofNullable(mongoTemplate.findById(customerId, CustomerCollection.class))
                .orElseThrow(() -> new CustomerNotFoundException(CUSTOMER_NOT_FOUND));

    }
}
