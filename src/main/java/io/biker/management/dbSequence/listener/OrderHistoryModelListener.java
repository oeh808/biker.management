package io.biker.management.dbSequence.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import io.biker.management.dbSequence.service.SequenceGeneratorService;
import io.biker.management.orderHistory.entity.OrderHistory;

@Component
public class OrderHistoryModelListener extends AbstractMongoEventListener<OrderHistory> {
    @Autowired
    private SequenceGeneratorService sequenceGenerator;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<OrderHistory> event) {
        if (event.getSource().getId() < 1) {
            event.getSource().setId(sequenceGenerator.generateSequence(OrderHistory.SEQUENCE_NAME));
        }
    }
}
