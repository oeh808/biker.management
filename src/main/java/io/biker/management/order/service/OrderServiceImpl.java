package io.biker.management.order.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import io.biker.management.customer.entity.Customer;
import io.biker.management.customer.exception.CustomerExceptionMessages;
import io.biker.management.customer.repo.CustomerRepo;
import io.biker.management.order.constants.OrderStatus;
import io.biker.management.order.constants.Tax;
import io.biker.management.order.entity.Order;
import io.biker.management.order.entity.OrderDetails;
import io.biker.management.order.exception.OrderException;
import io.biker.management.order.repo.OrderRepo;
import io.biker.management.product.entity.Product;
import io.biker.management.product.exception.ProductExceptionMessages;
import io.biker.management.product.repo.ProductRepo;
import io.biker.management.store.repo.StoreRepo;
import io.biker.management.user.Address;

@Service
public class OrderServiceImpl implements OrderService {
    private OrderRepo orderRepo;
    private CustomerRepo customerRepo;
    private ProductRepo productRepo;
    private StoreRepo storeRepo;

    @Override
    public Order createOrder(int customerId, int productId, Address deliveryAddress) {
        Customer customer = getCustomer(customerId);
        Product product = getProduct(productId);
        OrderDetails orderDetails = new OrderDetails(product.getName(), product.getPrice(), Tax.VAT, productId,
                deliveryAddress, null);

        Order order = new Order(0, customer, product.getStore(), null, OrderStatus.AWAITING_APPROVAL, null,
                orderDetails);
        return orderRepo.save(order);
    }

    @Override
    public Order getOrder(int customerId, int orderId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOrder'");
    }

    @Override
    public Order getOrder_BackOffice(int orderId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOrder_BackOffice'");
    }

    @Override
    public List<Order> getAvailableOrders() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAvailableOrders'");
    }

    @Override
    public List<Order> getOrdersByStore(int storeId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOrdersByStore'");
    }

    @Override
    public List<Order> getOrdersByBiker(int bikerId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOrdersByBiker'");
    }

    @Override
    public void rateOrder(int customerId, int orderId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'rateOrder'");
    }

    @Override
    public void updateOrderStatus_Biker(int customerId, int orderId, String status) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateOrderStatus'");
    }

    @Override
    public void updateOrderStatus_Store(int customerId, int orderId, String status) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateOrderStatus'");
    }

    @Override
    public void updateOrderStatus_BackOffice(int orderId, String status) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateOrderStatus_BackOffice'");
    }

    @Override
    public void assignDelivery(int bikerId, int orderId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'assignDelivery'");
    }

    @Override
    public void deleteOrder(int orderId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteOrder'");
    }

    // Helper functions
    private Customer getCustomer(int customerId) {
        Optional<Customer> opCustomer = customerRepo.findById(customerId);
        if (opCustomer.isPresent()) {
            return opCustomer.get();
        } else {
            throw new OrderException(CustomerExceptionMessages.CUSTOMER_NOT_FOUND);
        }
    }

    private Product getProduct(int productId) {
        Optional<Product> opProduct = productRepo.findById(productId);
        if (opProduct.isPresent()) {
            return opProduct.get();
        } else {
            throw new OrderException(ProductExceptionMessages.PRODUCT_NOT_FOUND);
        }
    }
}
