package br.com.magalu.wishlist.gateways.nosql.documents;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "item")
public class ItemCollection {

    @Id
    private String productId;
    @Field("title")
    private String title;
    @Field("image")
    private String image;
    @Field("price")
    private Double price;

}
