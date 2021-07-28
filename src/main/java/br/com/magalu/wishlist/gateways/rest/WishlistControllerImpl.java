package br.com.magalu.wishlist.gateways.rest;

import br.com.magalu.wishlist.gateways.converters.ItemRestConverter;
import br.com.magalu.wishlist.gateways.json.ApiResponse;
import br.com.magalu.wishlist.usecase.AddItemUseCase;
import br.com.magalu.wishlist.usecase.GetDetailsUseCase;
import br.com.magalu.wishlist.usecase.RemoveUseCase;
import br.com.magalu.wishlist.usecase.VerifyItemUseCase;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RequestMapping("/api/wishlist/")
@RestController
@ResponseBody
@AllArgsConstructor
public class WishlistControllerImpl implements WishlistController {

    @Autowired
    private final AddItemUseCase addItemUseCase;

    @Autowired
    private final RemoveUseCase removeUseCase;

    @Autowired
    private final GetDetailsUseCase getDetailsUseCase;

    @Autowired
    private final VerifyItemUseCase verifyItemUseCase;

    private ItemRestConverter itemRestConverter;


    @Override
    public ApiResponse save(String customerId, ItemRest body) {
        log.info("save : customerId : {} , body : {}", customerId, body.toString());
        addItemUseCase.execute(customerId, itemRestConverter.mapToEntity(body));
        return ApiResponse.builder().message("Item added!").build();
    }

    @Override
    public ApiResponse delete(String customerId, String itemId) {
        log.info("delete : customerId : {} , itemId : {}", customerId, itemId);
        removeUseCase.execute(customerId, itemRestConverter.mapToEntity(ItemRest.builder().productId(itemId).build()));
        return ApiResponse.builder().message("Item removed").build();
    }

    @Override
    public ApiResponse<List<ItemRest>> getAllByClientId(String customerId) {
        log.info("getAllByClientId : customerId : {}", customerId);
        ApiResponse<List<ItemRest>> response = new ApiResponse<>();
        response.setData(itemRestConverter.mapToRest(getDetailsUseCase.execute(customerId)));
        return response;

    }

    @Override
    public ApiResponse<Boolean> verifyItemInWishlistById(String customerId, String itemId) {
        log.info("verifyItemInWishlistById : customerId : {} , itemId : {}", customerId,itemId);
        Boolean response = verifyItemUseCase.execute(customerId, itemId);
        ApiResponse<Boolean> apiResponse = new ApiResponse<>();
        apiResponse.setMessage(response.equals(true) ? "Item exists on wishlist" : "Item does not exist on wishlist");
        apiResponse.setData(response);
        return apiResponse;


    }


}
