package org.example;

import org.example.model.BaseEntity;
import org.example.model.MenuItem;
import org.example.repository.FileMenuRepository;
import org.example.repository.Repository;
import org.example.service.CartService;
import org.example.ui.Console;

public class Main {
    public static void main(String[] args) {
        Repository<Long, MenuItem> repository = new FileMenuRepository("data/menu-items.txt");
        CartService cartService = new CartService();

        Console console = new Console(cartService, repository);

        console.run();

    }
}
