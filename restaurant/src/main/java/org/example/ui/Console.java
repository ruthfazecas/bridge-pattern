package org.example.ui;

import org.example.model.MenuItem;
import org.example.repository.Repository;
import org.example.service.CartService;

import java.util.Scanner;

public class Console {
    private final CartService cartService;
    private final Repository<Long, MenuItem> repository;
    private final Scanner scanner = new Scanner(System.in);

    public Console(CartService cartService, Repository<Long, MenuItem> repository) {
        this.cartService = cartService;
        this.repository = repository;
    }

    private void printMenus() {
        repository.findAll().forEach(menuItem ->
                System.out.println(menuItem.getId() + " -- " + menuItem.getName())
        );
    }

    private void printSubmenu(MenuItem menuItem) {
        menuItem.getSubMenuItems().forEach(subMenuItem -> System.out.println("\t" + subMenuItem.getId() + " -- " + subMenuItem));
    }

    public void run() {
        boolean running = true;
        while (running) {
            printMenus();
            System.out.println("Choose menu: ");
            String choice = scanner.nextLine();

            var itemOptional = repository.findOne(Long.parseLong(choice));
            itemOptional.ifPresent(item -> {
                printSubmenu(item);
                System.out.println("Choose item: ");
                var itemId = Long.parseLong(scanner.nextLine());

                item.getSubMenuItems().stream().filter(it -> it.getId().equals(itemId))
                        .findAny().ifPresent(
                                e -> {
                                    System.out.println("Added to cart: " + e);
                                    cartService.addItemToCart(e);
                                }
                        );

            });
        }
    }
}
