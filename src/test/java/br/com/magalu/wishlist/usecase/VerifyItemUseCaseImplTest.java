package br.com.magalu.wishlist.usecase;

import br.com.magalu.wishlist.entities.Item;
import br.com.magalu.wishlist.exceptions.CustomerNotFoundException;
import br.com.magalu.wishlist.gateways.nosql.documents.CustomerCollection;
import br.com.magalu.wishlist.gateways.nosql.documents.ItemCollection;
import org.junit.jupiter.api.Assertions;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VerifyItemUseCaseImplTest {

    @InjectMocks
    VerifyItemUseCaseImpl verifyItemUseCase;

    @Mock
    MongoTemplate mongoTemplate;

    String customerId = "e6ef0843-3394-4270-8844-ec50cc47ff46";
    String productId = "0ab044a8-ed5a-11eb-9a03-0242ac130003";

    @Test
    void itemNotFoundTest() {
        when(mongoTemplate.findById(any(), any())).thenReturn(getCustomerCollection(ItemCollection.builder()
                .title("Smartphone Samsung Galaxy s1000")
                .productId("1ab044a8-ed5a-11eb-9a03-0242ac130003")
                .price(500.00)
                .build()));
        Assertions.assertFalse(verifyItemUseCase.execute(customerId, productId));
    }

    @Test
    void itemFoundTest() {
        when(mongoTemplate.findById(any(), any())).thenReturn(getCustomerCollection(getItemCollection()));
        Assertions.assertTrue(verifyItemUseCase.execute(customerId, productId));
    }

    @Test
    void customerNotFoundTest() {
        CustomerNotFoundException thrown = assertThrows(
                CustomerNotFoundException.class,
                () -> verifyItemUseCase.execute(customerId, getItem().getProductId()));
        assertTrue(thrown.getMessage().contains("Customer not found, check customer ID"));
    }

    public Item getItem() {
        return Item.builder().title("Smartphone Samsung Galaxy S20")
                .productId("0ab044a8-ed5a-11eb-9a03-0242ac130003")
                .price(25.66)
                .image("https://a-static.mlcdn.com.br/618x463/smartphone-samsung-galaxy-s20-fe-128gb-cloud-navy-4g-6gb-ram-tela-65-cam-tripla-selfie-32mp/magazineluiza/155629800/0007bbdc665749ec107d860c3a4b8b2f.jpg")
                .build();
    }


    public CustomerCollection getCustomerCollection(ItemCollection itemCollection) {
        List<ItemCollection> list = new ArrayList<>();
        list.add(itemCollection);
        return CustomerCollection.builder().id("e6ef0843-3394-4270-8844-ec50cc47ff46")
                .name("test")
                .wishList(list)
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