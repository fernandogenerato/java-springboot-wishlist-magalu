package br.com.magalu.wishlist.gateways.rest;

import br.com.magalu.wishlist.gateways.converters.ItemRestConverter;
import br.com.magalu.wishlist.usecase.AddItemUseCase;
import br.com.magalu.wishlist.usecase.GetDetailsUseCase;
import br.com.magalu.wishlist.usecase.RemoveUseCase;
import br.com.magalu.wishlist.usecase.VerifyItemUseCase;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WishlistController.class)
@ExtendWith(MockitoExtension.class)
class WishlistControllerImplTest {

    @Autowired
    MockMvc mockMvc;

    @InjectMocks
    WishlistControllerImpl controller;

    @MockBean
    AddItemUseCase addItemUseCase;

    @MockBean
    RemoveUseCase removeUseCase;

    @MockBean
    GetDetailsUseCase getDetailsUseCase;

    @MockBean
    VerifyItemUseCase verifyItemUseCase;

    @MockBean
    ItemRestConverter itemRestConverter;


    String customerId = "e6ef0843-3394-4270-8844-ec50cc47ff46";
    String productId = "0ab044a8-ed5a-11eb-9a03-0242ac130003";
    String context = "/api/wishlist/";
    String payload = "{\n" +
            "    \"product_id\": \"0ab044a8-ed5a-11eb-9a03-0242ac130003\",\n" +
            "    \"title\": \"Smartphone Samsung Galaxy S20 FE 128GB\",\n" +
            "    \"image\": \"https://a-static.mlcdn.com.br/618x463/65749ec107d860c3a4b8b2f.jpg\",\n" +
            "    \"price\":2.36451\n" +
            "}";

    @SneakyThrows
    @Test
    void saveTest() {
        String url = context.concat(customerId).concat("/save");
        this.mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isCreated());
    }

    @SneakyThrows
    @Test
    void deleteTest() {
        String url = context.concat(customerId).concat("/delete/").concat(productId);
        this.mockMvc.perform(delete(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    void getAllByClientIdTest() {
        String url = context.concat(customerId);
        MvcResult mvcResult = this.mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isOk()).andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @SneakyThrows
    @Test
    void verifyItemInWishlistByIdTest() {
        String url = context.concat(customerId).concat("/verify/").concat(productId);
        MvcResult mvcResult = this.mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isOk()).andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());

    }

    @SneakyThrows
    @Test
    void restError5xx() {
        String url = context.concat(customerId).concat("/save");
        this.mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError());
    }

    @SneakyThrows
    @Test
    void restError4xx() {
        String url = context.concat("error").concat("/error");
        this.mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

}