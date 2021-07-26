package br.com.magalu.wishlist.entities;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class Item implements Serializable {

    private String productId;
    private String title;
    private String image;
    private Double price;
}