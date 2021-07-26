package br.com.magalu.wishlist.gateways.nosql.documents;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "customer")
public class CustomerCollection {
    @Id
    private String id;
    @Field("customer_name")
    private String name;
    @Field("customer_wishlist")
    private List<ItemCollection> wishList = new ArrayList<>();



}

