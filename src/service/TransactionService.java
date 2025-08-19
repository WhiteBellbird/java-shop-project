package service;

public interface TransactionService<T extends OrderService, K extends UserService> {

	T transaction(T doit);
}
