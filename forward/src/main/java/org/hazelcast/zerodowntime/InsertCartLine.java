package org.hazelcast.zerodowntime;

import com.hazelcast.function.BiConsumerEx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InsertCartLine implements BiConsumerEx<Context, CallParameters> {

    private static final Logger LOGGER = LoggerFactory.getLogger(InsertCartLine.class);

    @Override
    public void acceptEx(Context context, CallParameters parameters) throws Exception {
        var dataSource = context.getDataSource();
        try (
             var connection = dataSource.getConnection();
             var statement = connection.prepareCall("{CALL INSERT_CART_LINE(?,?,?)}")
        ) {
            statement.setLong(1, parameters.getCustomerId());
            statement.setLong(2, parameters.getProductId());
            statement.setInt(3, parameters.getQuantity());
            statement.execute();
            LOGGER.info("Called INSERT_CART_LINE callable statement with parameters {}", parameters);
        }
    }
}