package br.com.magalu.wishlist.usecase;

import br.com.magalu.wishlist.entities.Item;
import br.com.magalu.wishlist.exceptions.CustomerNotFoundException;
import br.com.magalu.wishlist.gateways.nosql.documents.CustomerCollection;
import br.com.magalu.wishlist.gateways.nosql.documents.ItemCollection;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@Component
public class GetDetailsUseCaseImpl implements GetDetailsUseCase {

    private static final String CUSTOMER_NOT_FOUND = "Customer not found, check customer ID.";

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Item> execute(String customerId) {
        return convertFromCollection(getCustomer(customerId).getWishList());
    }

    private List<Item> convertFromCollection(List<ItemCollection> collections) {
        List<Item> items = new ArrayList<>();
        for (ItemCollection ic : collections) {
            Item item = Item.builder()
                    .image(ic.getImage())
                    .price(ic.getPrice())
                    .productId(ic.getProductId())
                    .title(ic.getTitle())
                    .build();
            items.add(item);
        }
        return items;
    }

    private CustomerCollection getCustomer(String customerId) {

        return Optional.ofNullable(mongoTemplate.findById(customerId, CustomerCollection.class))
                .orElseThrow(() -> new CustomerNotFoundException(CUSTOMER_NOT_FOUND));
    }

}
