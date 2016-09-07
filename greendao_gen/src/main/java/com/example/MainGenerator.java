package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

public class MainGenerator {
    public static void main(String[] args) {
        Schema schema = new Schema(1, "vldmr.testtask.database");
        schema.enableKeepSectionsByDefault();

        Entity product = schema.addEntity("Product");
            product.addIdProperty();
        product.addStringProperty("name").notNull();
        product.addIntProperty("price").notNull();
        product.addIntProperty("amount").notNull();
        try {
            new DaoGenerator().generateAll(schema, ".\\app\\src\\main\\java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
