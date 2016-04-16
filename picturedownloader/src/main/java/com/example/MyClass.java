package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyClass {
    public static void main(String[] args){

        Schema schema = new Schema(2,"com.wuyue.dllo.mirror.picturedownloader");
        addNote(schema);
        try {
            new DaoGenerator().generateAll(schema, "./app/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addNote(Schema schema) {
        Entity entity = schema.addEntity("PLEntity");

        entity.addIdProperty().autoincrement().primaryKey();
        entity.addStringProperty("uri");
        entity.addStringProperty("name");
        entity.addStringProperty("area");
        entity.addStringProperty("brand");
        entity.addStringProperty("price");

    }


}
