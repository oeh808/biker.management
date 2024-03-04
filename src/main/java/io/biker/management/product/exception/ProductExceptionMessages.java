package io.biker.management.product.exception;

public class ProductExceptionMessages {
    public static final String PRODUCT_NOT_FOUND = "The product with the given id does not exist";

    public static final String PRODUCT_NOT_FOUND(int storeId) {
        return "There is no product with the given product id associated with storeId: " + storeId;
    }
}
