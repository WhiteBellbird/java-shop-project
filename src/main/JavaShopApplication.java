package main;

import controller.CartController;
import controller.OrderController;
import controller.ProductController;
import domain.Product;
import iolayer.AdminIOLayer;
import iolayer.CartIOLayer;
import iolayer.MainIOLayer;
import iolayer.ProductIOLayer;
import iolayer.UserIOLayer;

import java.util.Scanner;

import controller.UserController;
import domain.User;
import helper.PasswordEncoder;
import helper.PasswordEncoderImpl;
import iolayer.OrderIOLayer;
import repository.CartRepository;
import repository.CartRepositoryImpl;
import repository.OrderRepository;
import repository.OrderRepositoryImpl;
import repository.ProductRepository;
import repository.ProductRepositoryImpl;
import repository.UserRepository;
import repository.UserRepositoryImpl;
import service.CartService;
import service.CartServiceImpl;
import service.OrderService;
import service.OrderServiceImpl;
import service.ProductService;
import service.ProductServiceImpl;
import service.SessionService;
import service.SessionServiceImpl;
import service.UserService;
import service.UserServiceImpl;

public class JavaShopApplication {

    private static Scanner scanner;
    private static SessionService sessionService;
    private static ProductIOLayer productIOLayer;
    private static UserIOLayer userIOLayer;
    private static AdminIOLayer adminIOLayer;
    private static CartIOLayer cartIOLayer;
    private static OrderIOLayer orderIOLayer;
    private static UserRepository userRepository;
    private static PasswordEncoder passwordEncoder;
    private static ProductRepository productRepository;
    private static CartRepository cartRepository;
    private static OrderRepository orderRepository;
    private static UserService userService;
    private static UserController userController;
    private static ProductService productService;
    private static ProductController productController;
    private static CartService cartService;
    private static CartController cartController;
    private static OrderService orderService;
    private static MainIOLayer mainIOLayer;
    private static OrderController orderController;

    public static void main(String[] args) {
        // please move this to field
        dependencyInjection();
        initializeProducts(productController);
        mainIOLayer.main();
    }

    private static void dependencyInjection() {
        scanner = new Scanner(System.in);
        userRepository = new UserRepositoryImpl();
        passwordEncoder = new PasswordEncoderImpl();
        productRepository = new ProductRepositoryImpl();
        cartRepository = new CartRepositoryImpl();
        orderRepository = new OrderRepositoryImpl();
        // every controller, repository, service, ioLayer should be created here
        userService = new UserServiceImpl(userRepository, passwordEncoder);
        userController = new UserController(userService);
        productService = new ProductServiceImpl(productRepository);
        productController = new ProductController(productService);
        cartService = new CartServiceImpl(cartRepository, userRepository, productRepository);
        cartController = new CartController(cartService);
        sessionService = new SessionServiceImpl(userRepository, passwordEncoder);
        orderService = new OrderServiceImpl(orderRepository, cartRepository, productRepository, userRepository);
        orderController = new OrderController(orderService);
        orderIOLayer = new OrderIOLayer(scanner, orderController, sessionService);
        productIOLayer = new ProductIOLayer(scanner, productController);
        userIOLayer = new UserIOLayer(scanner, userController, sessionService);
        adminIOLayer = new AdminIOLayer(scanner, userController, sessionService, productController);
        cartIOLayer = new CartIOLayer(cartController, sessionService, scanner);
        mainIOLayer = new MainIOLayer(scanner, orderIOLayer, productIOLayer,
                sessionService, userIOLayer, adminIOLayer, cartIOLayer);
        deleteAllDatas();
        initialUser(userController);
        initializeProducts(productController);
        addCart(cartController);
        mainIOLayer.main();
    }

    private static void initializeProducts(ProductController productController) {
        // create some initial products createProduct field is String name, String
        // category, int price, int stock, String description go copilot;
        // but, please create product using korean name
        productController.createProduct("삼성 갤럭시 S23", "스마트폰", 1000000, 10, "최신형 스마트폰");
        productController.createProduct("애플 아이폰 14", "스마트폰", 1200000, 5, "최신형 스마트폰");
        productController.createProduct("LG 그램 16", "노트북", 1500000, 7, "가벼운 노트북");
        productController.createProduct("맥북 프로 16", "노트북", 2500000, 3, "고성능 노트북");
        productController.createProduct("삼성 QLED TV", "TV", 2000000, 4, "고화질 TV");
        productController.createProduct("LG OLED TV", "TV", 3000000, 2, "최고급 TV");
        productController.createProduct("소니 WH-1000XM4", "헤드폰", 400000, 15, "노이즈 캔슬링 헤드폰");
        productController.createProduct("애플 에어팟 프로", "헤드폰", 300000, 20, "무선 이어폰");
        // more products can be added here
        productController.createProduct("샤오미 홍미노트 11", "스마트폰", 300000, 12, "가성비 좋은 스마트폰");
        productController.createProduct("레노버 아이디어패드 3", "노트북", 600000, 8, "합리적인 가격의 노트북");
        productController.createProduct("파나소닉 4K TV", "TV", 1800000, 6, "고화질 4K TV");
        Product product = productController.createProduct("젠하이저 HD 450BT", "헤드폰", 200000, 18, "무선 헤드폰");
        product.addSellCount(120);

    }

    private static void initialUser(UserController userController) {
        // controller method field is String username, String email, String password, String address, String phone;
        userController.createUser("admin", "test@example.com", "Adminpass!", "Seoul, Korea", "010-1234-5678");
        userController.createUser("user1", "test2@example.com", "User1pass!", "Busan, Korea", "010-2345-6789");
        userController.createUser("user2", "seoul@example.com", "User2pass!", "Incheon, Korea", "010-3456-7890");
        User u = userController.createUser("u", "seoul2@example.com", "Aaa1!", "Incheon, Korea", "010-3456-7890");
//        userController.updateManager(u,u.getUsername());
        u.giveManagerAuthentication();
    }

    private static void deleteAllDatas() {
        userRepository.clearAll();
        productRepository.clearAll();
        cartRepository.clearAll();
        orderRepository.clearAll();
    }

    private static void addCart(CartController cartController) {
        User user = userRepository.findUserByUsername("u");
        Product product = productRepository.findByName("젠하이저 HD 450BT").get();
        cartController.createCart(user);
        cartController.addProductByCart(user, product.getName(), 1);
    }
}