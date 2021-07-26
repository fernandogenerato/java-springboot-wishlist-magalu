package br.com.magalu.wishlist.gateways.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class ItemRest implements Serializable {
    @JsonProperty("product_id")
    private String productId;
    @JsonProperty("title")
    private String title;
    @JsonProperty("image")
    private String image;
    @JsonProperty("price")
    private Double price;
}

