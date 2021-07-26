package br.com.magalu.wishlist.usecase;

import br.com.magalu.wishlist.entities.Item;

public interface AddItemUseCase {

    void execute(String customerId, Item item);
}
