package br.com.magalu.wishlist.usecase;

import br.com.magalu.wishlist.entities.Item;

import java.util.List;

public interface GetDetailsUseCase {

     List<Item> execute(String customerId);

}
