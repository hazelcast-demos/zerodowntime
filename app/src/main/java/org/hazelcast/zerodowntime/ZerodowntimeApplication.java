package org.hazelcast.zerodowntime;

import com.hazelcast.config.*;
import org.hazelcast.zerodowntime.cart.CartRepository;
import org.hazelcast.zerodowntime.cart.CartService;
import org.hazelcast.zerodowntime.customer.CustomerRepository;
import org.hazelcast.zerodowntime.operation.OperationContext;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.support.DatabaseStartupValidator;
import org.springframework.session.MapSession;
import org.springframework.session.hazelcast.Hazelcast4IndexedSessionRepository;
import org.springframework.session.hazelcast.HazelcastSessionSerializer;
import org.springframework.session.hazelcast.config.annotation.web.http.EnableHazelcastHttpSession;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.stream.Stream;

@SpringBootApplication
@EnableHazelcastHttpSession
public class ZerodowntimeApplication {

    @Bean
    public CartService cartService(CustomerRepository customerRepository, CartRepository cartRepository) {
        return new CartService(customerRepository, cartRepository);
    }

    @Bean
    public OperationContext context() {
        return new OperationContext();
    }

    @Bean
    public Config hazelcastConfig() {
        var config = new Config();
        var attributeConfig = new AttributeConfig()
                .setName(Hazelcast4IndexedSessionRepository.PRINCIPAL_NAME_ATTRIBUTE)
                .setExtractorClassName(CustomerIdExtractor.class.getName());
        config.getMapConfig(Hazelcast4IndexedSessionRepository.DEFAULT_SESSION_MAP_NAME)
                .addAttributeConfig(attributeConfig)
                .addIndexConfig(new IndexConfig(
                        IndexType.HASH,
                        Hazelcast4IndexedSessionRepository.PRINCIPAL_NAME_ATTRIBUTE
                ));
        var serializerConfig = new SerializerConfig();
        serializerConfig.setImplementation(new HazelcastSessionSerializer()).setTypeClass(MapSession.class);
        config.getSerializationConfig().addSerializerConfig(serializerConfig);
        return config;
    }

    @Bean
    public DatabaseStartupValidator databaseStartupValidator(DataSource dataSource) {
        var dsv = new DatabaseStartupValidator();
        dsv.setDataSource(dataSource);
        dsv.setInterval(5);
        return dsv;
    }

    @Bean
    public static BeanFactoryPostProcessor dependsOnPostProcessor() {
        return bf -> {
            String[] jpa = bf.getBeanNamesForType(EntityManagerFactory.class);
            Stream.of(jpa)
                    .map(bf::getBeanDefinition)
                    .forEach(it -> it.setDependsOn("databaseStartupValidator"));
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(ZerodowntimeApplication.class, args);
    }
}