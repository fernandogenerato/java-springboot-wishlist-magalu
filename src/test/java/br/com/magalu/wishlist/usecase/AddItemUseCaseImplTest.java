package br.com.magalu.wishlist.usecase;

import br.com.magalu.wishlist.entities.Item;
import br.com.magalu.wishlist.exceptions.CustomerNotFoundException;
import br.com.magalu.wishlist.exceptions.MaximumLimitException;
import br.com.magalu.wishlist.gateways.nosql.WishlistRepository;
import br.com.magalu.wishlist.gateways.nosql.documents.CustomerCollection;
import br.com.magalu.wishlist.gateways.nosql.documents.ItemCollection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddItemUseCaseImplTest {

    @InjectMocks
    AddItemUseCaseImpl addItemUseCase;

    @Mock
    WishlistRepository wishlistRepository;

    @Mock
    MongoTemplate mongoTemplate;

    @Mock
    Item item;
    String customerId = "e6ef0843-3394-4270-8844-ec50cc47ff46";


    @Test
    void customerNotFoundTest() {
        CustomerNotFoundException thrown = assertThrows(
                CustomerNotFoundException.class,
                () -> addItemUseCase.execute(customerId, item),
                "Expected doThing() to throw, but it didn't"
        );
        assertTrue(thrown.getMessage().contains("Customer not found, check customer ID"));
    }

    @Test
    void limitMaxWishlistErrorTest() {
        CustomerCollection collection = new CustomerCollection();
        List<ItemCollection> list = new ArrayList<>();
        int count = 0;
        while (count < 20) {
            ItemCollection itemCollection = new ItemCollection();
            itemCollection.setProductId(UUID.randomUUID().toString());
            list.add(itemCollection);
            count++;
        }

        collection.setWishList(list);
        when(mongoTemplate.findById(customerId, CustomerCollection.class)).thenReturn(collection);
        MaximumLimitException thrown = assertThrows(
                MaximumLimitException.class,
                () -> addItemUseCase.execute(customerId, item),
                "Expected doThing() to throw, but it didn't"
        );
        assertTrue(thrown.getMessage().contains("Maximum Limit of 20 Products."));

    }


    @Test
    void testOK() {
        CustomerCollection collection = mock(CustomerCollection.class);
        when(mongoTemplate.findById(any(), any())).thenReturn(collection);
        addItemUseCase.execute(customerId, item);
        verify(wishlistRepository,times(1)).save(collection);

    }
}