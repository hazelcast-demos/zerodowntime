package org.hazelcast.zerodowntime;

import com.hazelcast.query.extractor.ValueCollector;
import com.hazelcast.query.extractor.ValueExtractor;
import org.hazelcast.zerodowntime.customer.CustomerView;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.MapSession;

public class CustomerIdExtractor implements ValueExtractor<MapSession, Long> {

    @Override
    @SuppressWarnings("unchecked")
    public void extract(MapSession target, Long argument, ValueCollector collector) {
        var attribute = target.getAttribute(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME);
        if (attribute != null) {
            var customer = (CustomerView) attribute;
            var id = customer.getId();
            collector.addObject(id);
        }
    }
}