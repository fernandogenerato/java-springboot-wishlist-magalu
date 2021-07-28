package br.com.magalu.wishlist.usecase;

import br.com.magalu.wishlist.exceptions.CustomerNotFoundException;
import br.com.magalu.wishlist.gateways.nosql.documents.CustomerCollection;
import br.com.magalu.wishlist.gateways.nosql.documents.ItemCollection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetDetailsUseCaseImplTest {

    @InjectMocks
    GetDetailsUseCaseImpl getDetailsUseCase;
    @Mock
    MongoTemplate mongoTemplate;
    String customerId = "e6ef0843-3394-4270-8844-ec50cc47ff46";

    @Test
    void execute() {
        List<ItemCollection> list = new ArrayList<>();
        list.add(ItemCollection.builder()
                .productId("0ab044a8-ed5a-11eb-9a03-0242ac130003")
                .image("test")
                .price(2033.00)
                .title("batatinha")
                .build());
        when(mongoTemplate.findById(customerId, CustomerCollection.class))
                .thenReturn(CustomerCollection.builder().wishList(list).id("idtest").name("nametest").build());
        Assertions.assertTrue(getDetailsUseCase.execute(customerId).size() > 0);
    }

    @Test
    void customerNotFoundTest() {
        CustomerNotFoundException thrown = assertThrows(
                CustomerNotFoundException.class,
                () -> getDetailsUseCase.execute(customerId));
        assertTrue(thrown.getMessage().contains("Customer not found, check customer ID"));
    }
}