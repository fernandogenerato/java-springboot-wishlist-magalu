package br.com.magalu.wishlist.gateways.rest;

import br.com.magalu.wishlist.gateways.json.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/default")
public interface WishlistController {
    @PostMapping(value = "/{customerId}/save")
    @ResponseStatus(HttpStatus.CREATED)
    ApiResponse save(@PathVariable String customerId, @RequestBody ItemRest body);

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{customerId}/delete/{itemId}")
    ApiResponse delete(@PathVariable String customerId,@PathVariable String itemId);

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{customerId}")
    ApiResponse<List<ItemRest>> getAllByClientId(@PathVariable String customerId);

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{customerId}/verify/{itemId}")
    ApiResponse<Boolean> verifyItemInWishlistById(@PathVariable String customerId,@PathVariable String itemId);
}