package controller;

import exception.CustomIllegalArgumentException;

public class OrderValidationController {


    public Boolean validateCreateOrder(String userId, String productName, int amount, int quantity, String address) {
        if (userId.isEmpty() || productName.isEmpty() || amount <= 0 || quantity <= 0 || address.isEmpty()) {
            return false;
        }
        return true;
    }
}
