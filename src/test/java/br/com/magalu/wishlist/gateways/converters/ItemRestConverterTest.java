package br.com.magalu.wishlist.gateways.converters;

import br.com.magalu.wishlist.entities.Item;
import br.com.magalu.wishlist.gateways.rest.ItemRest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ItemRestConverterTest {

    @InjectMocks
    ItemRestConverter itemRestConverter;

    private ItemRest getItemRest() {
        return ItemRest.builder()
                .productId("0ab044a8-ed5a-11eb-9a03-0242ac130003")
                .image("test")
                .price(559.60)
                .title("test")
                .build();
    }
    @Test
    void testMapConvertersTest() {
        List<Item> items = new ArrayList<>();
        items.add(itemRestConverter.mapToEntity(getItemRest()));
        Assertions.assertNotNull(itemRestConverter.mapToRest(items));



    }
}