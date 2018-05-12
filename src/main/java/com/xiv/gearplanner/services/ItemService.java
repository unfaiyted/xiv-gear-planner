package com.xiv.gearplanner.services;

import com.xiv.gearplanner.models.Item;
import com.xiv.gearplanner.repositories.Items;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    private Items items;

    @Autowired
    public ItemService(Items items) {
        this.items = items;
    }

    public Items getItems() {
        return items;
    }

    public void save(Item item) {
        getItems().save(item);
    }

    public void saveAll(List<Item> items) {
        for(Item item : items) {
            save(item);
        }
    }
}
