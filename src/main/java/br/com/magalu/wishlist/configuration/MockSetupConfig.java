package br.com.magalu.wishlist.configuration;

import br.com.magalu.wishlist.gateways.nosql.WishlistRepository;
import br.com.magalu.wishlist.gateways.nosql.documents.CustomerCollection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MockSetupConfig {
    /**
     *  Mocking 2 test clients to use the wish list API.
     */
    @Bean
    public WishlistRepository setup(WishlistRepository repository) {
        CustomerCollection c1 = new CustomerCollection();
        c1.setId("e6ef0843-3394-4270-8844-ec50cc47ff46");
        c1.setName("Anakin");
        repository.save(c1);
        CustomerCollection c2 = new CustomerCollection();
        c2.setId("c70cf2c2-8858-4c1f-8cf2-0ca00dc24587");
        c2.setName("Jack Sparrow");
        repository.save(c2);
        return repository;
    }
}
