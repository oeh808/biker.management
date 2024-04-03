package io.biker.management.dbSequence.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import io.biker.management.dbSequence.service.SequenceGeneratorService;
import io.biker.management.order.entity.Order;

@Component
public class OrderModelListener extends AbstractMongoEventListener<Order> {
    @Autowired
    private SequenceGeneratorService sequenceGenerator;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Order> event) {
        if (event.getSource().getOrderId() < 1) {
            event.getSource().setOrderId(sequenceGenerator.generateSequence(Order.SEQUENCE_NAME));
        }
    }
}
