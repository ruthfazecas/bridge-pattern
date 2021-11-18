package org.example.repository;

import lombok.SneakyThrows;
import org.example.model.MenuItem;
import org.example.model.SubMenuItem;
import org.example.model.VAT;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;


public class FileMenuRepository implements Repository<Long, MenuItem> {


    private final Map<Long, MenuItem> entities = new HashMap<>();


    private final String SEPARATOR = "|";

    private final String fileName;

    public FileMenuRepository(String filename) {
        this.fileName = filename;
        loadData();
    }

    @Override
    public Optional<MenuItem> findOne(Long aLong) {
        return Optional.ofNullable(entities.get(aLong));
    }

    @Override
    public List<MenuItem> findAll() {
        return new ArrayList<>(entities.values());
    }

    @Override
    public Optional<MenuItem> save(MenuItem entity) {
        return Optional.ofNullable(entities.putIfAbsent(entity.getId(), entity));
    }

    @Override
    public Optional<MenuItem> delete(Long aLong) {
        return Optional.ofNullable(entities.remove(aLong));
    }

    @Override
    public Optional<MenuItem> update(MenuItem entity) {
        return Optional.ofNullable(entities.computeIfPresent(entity.getId(), (k, v) -> entity));
    }

    @SneakyThrows
    private void loadData() {
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(fileName)))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.isEmpty())
                    continue;
                var tokens = Arrays.asList(line.split(Pattern.quote(SEPARATOR)));
                long menuId = Long.parseLong(tokens.get(0));
                String menuName = tokens.get(1);
                int nrOrSubmenuItems = Integer.parseInt(tokens.get(2));
                List<SubMenuItem> subMenuItems = new ArrayList<>();
                while (nrOrSubmenuItems > 0) {
                    line = scanner.nextLine();
                    if (!line.isEmpty()) {
                        tokens = Arrays.asList(line.split(Pattern.quote(SEPARATOR)));
                        long id = Long.parseLong(tokens.get(0));
                        String name = tokens.get(1);
                        VAT vat = VAT.valueOf(tokens.get(2));
                        double price = Double.parseDouble(tokens.get(3));
                        subMenuItems.add(SubMenuItem.builder().name(name).price(price).id(id).vat(vat).build());
                        nrOrSubmenuItems--;
                    }
                }

                var menuItem = new MenuItem(menuId, menuName, subMenuItems);
                entities.put(menuId, menuItem);
            }

        } catch (final IOException ioException) {
            throw new RuntimeException(String.format("Error loading data from file %s.", fileName));
        }
    }
}
