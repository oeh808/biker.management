package io.biker.management.dbSequence.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import io.biker.management.dbSequence.service.SequenceGeneratorService;
import io.biker.management.product.entity.Product;

@Component
public class ProductModelListener extends AbstractMongoEventListener<Product> {
    @Autowired
    private SequenceGeneratorService sequenceGenerator;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Product> event) {
        if (event.getSource().getProductId() < 1) {
            event.getSource().setProductId(sequenceGenerator.generateSequence(Product.SEQUENCE_NAME));
        }
    }
}
