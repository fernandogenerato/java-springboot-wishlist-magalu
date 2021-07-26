package br.com.magalu.wishlist.gateways.converters;


import br.com.magalu.wishlist.entities.Item;
import br.com.magalu.wishlist.gateways.rest.ItemRest;
import br.com.magalu.wishlist.gateways.rest.RestConverter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ItemRestConverter implements RestConverter<ItemRest, Item> {

    @Override
    public Item mapToEntity(ItemRest rest) {
        return Item.builder()
                .image(rest.getImage())
                .price(rest.getPrice())
                .productId(rest.getProductId())
                .title(rest.getTitle())
                .build();
    }

    @Override
    public List<ItemRest> mapToRest(List<Item> listEntity) {
        List<ItemRest> items = new ArrayList<>();
        for (Item ic : listEntity) {
            ItemRest item = ItemRest.builder()
                    .image(ic.getImage())
                    .price(ic.getPrice())
                    .productId(ic.getProductId())
                    .title(ic.getTitle())
                    .build();
            items.add(item);
        }
        return items;
    }
}
