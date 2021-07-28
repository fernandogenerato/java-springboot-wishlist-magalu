package br.com.magalu.wishlist.usecase;

import br.com.magalu.wishlist.entities.Item;
import br.com.magalu.wishlist.exceptions.CustomerNotFoundException;
import br.com.magalu.wishlist.exceptions.ProductNotFoundException;
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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RemoveUseCaseImplTest {
    @InjectMocks
    RemoveUseCaseImpl removeUseCase;

    @Mock
    WishlistRepository wishlistRepository;

    @Mock
    MongoTemplate mongoTemplate;


    String customerId = "e6ef0843-3394-4270-8844-ec50cc47ff46";

    @Test
    void customerNotFoundTest() {
        CustomerNotFoundException thrown = assertThrows(
                CustomerNotFoundException.class,
                () -> removeUseCase.execute(customerId, getItem()));
        assertTrue(thrown.getMessage().contains("Customer not found, check customer ID"));
    }

    @Test
    void productNotFoundTest() {
        CustomerCollection collection = mock(CustomerCollection.class);
        when(mongoTemplate.findById(any(), any())).thenReturn(collection);
        ProductNotFoundException thrown = assertThrows(
                ProductNotFoundException.class,
                () -> removeUseCase.execute(customerId, getItem()));
        assertTrue(thrown.getMessage().contains("Product not found, check product ID."));
    }

    @Test
    void testOK() {
        when(mongoTemplate.findById(any(), any())).thenReturn(getCustomerCollection(getItemCollection()));
        removeUseCase.execute(customerId, getItem());
        verify(wishlistRepository, times(1)).save(any());

    }

    public CustomerCollection getCustomerCollection(ItemCollection itemCollection) {
        List<ItemCollection> list = new ArrayList<>();
        list.add(itemCollection);
        return CustomerCollection.builder().id("e6ef0843-3394-4270-8844-ec50cc47ff46")
                .name("test")
                .wishList(list)
                .build();

    }

    public Item getItem() {
        return Item.builder().title("Smartphone Samsung Galaxy S20")
                .productId("0ab044a8-ed5a-11eb-9a03-0242ac130003")
                .price(25.66)
                .image("https://a-static.mlcdn.com.br/618x463/smartphone-samsung-galaxy-s20-fe-128gb-cloud-navy-4g-6gb-ram-tela-65-cam-tripla-selfie-32mp/magazineluiza/155629800/0007bbdc665749ec107d860c3a4b8b2f.jpg")
                .build();
    }

    public ItemCollection getItemCollection() {
        return ItemCollection.builder().title("Smartphone Samsung Galaxy S20")
                .productId("0ab044a8-ed5a-11eb-9a03-0242ac130003")
                .price(25.66)
                .image("https://a-static.mlcdn.com.br/618x463/smartphone-samsung-galaxy-s20-fe-128gb-cloud-navy-4g-6gb-ram-tela-65-cam-tripla-selfie-32mp/magazineluiza/155629800/0007bbdc665749ec107d860c3a4b8b2f.jpg")
                .build();
    }
}