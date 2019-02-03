# vaha-localdb-android

#### What is it?
An implementation (powered natively) of [vaha-localdb](https://github.com/vahithanoglu/vaha-localdb) interface library that can be used on Android platform.

#### ExampleDBCore.java (an implementation of abstract class [AndroidDBCore](./src/main/java/com/vahabilisim/localdb/android/AndroidDBCore.java))
```java
package com.vahabilisim.localdb.android.example;

import android.content.Context;
import com.vahabilisim.localdb.LocalDBTrans;
import com.vahabilisim.localdb.android.AndroidDBCore;

public class ExampleDBCore extends AndroidDBCore {

    private static final int VERSION = 3;

    public ExampleDBCore(Context context) {
        super(context, "exampledb.sqlite", VERSION);
    }

    @Override
    public void onCreate(LocalDBTrans trans) {
        // table "car" is from version 1
        trans.execSQL("CREATE TABLE car (id TEXT PRIMARY KEY, vendor TEXT, model TEXT, year TEXT)");

        // table "truck" is from version 2
        trans.execSQL("CREATE TABLE truck (id TEXT PRIMARY KEY, vendor TEXT, model TEXT, year TEXT)");

        // table "bike" is from this version = version 3
        trans.execSQL("CREATE TABLE bike (id TEXT PRIMARY KEY, vendor TEXT, model TEXT, year TEXT)");
    }

    @Override
    public void onUpgrade(LocalDBTrans trans, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            trans.execSQL("CREATE TABLE truck (id TEXT PRIMARY KEY, vendor TEXT, model TEXT, year TEXT)");
        }

        if (oldVersion < 3) {
            trans.execSQL("CREATE TABLE bike (id TEXT PRIMARY KEY, vendor TEXT, model TEXT, year TEXT)");
        }
    }
}
```

#### ExampleActivity.java (a basic example of how to use [vaha-localdb](https://github.com/vahithanoglu/vaha-localdb) and [vaha-localdb-android](https://github.com/vahithanoglu/vaha-localdb-android) together)
```java
package com.vahabilisim.exampleapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.vahabilisim.localdb.LocalDBException;
import com.vahabilisim.localdb.android.example.ExampleDBCore;
import com.vahabilisim.localdb.example.Example;

import java.util.LinkedList;
import java.util.List;

public class ExampleActivity extends AppCompatActivity {

    private TextView logView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        logView = new TextView(getApplicationContext());
        setContentView(logView);

        try {
            runExample();
        } catch (LocalDBException ex) {
            println(ex.getStackTrace().toString());
        }
    }

    private void println(String line){
        logView.append(line);
        logView.append("\n");
    }

    private void runExample() throws LocalDBException {
        final ExampleDBCore core = new ExampleDBCore(getApplicationContext());
        final Example db = new Example(core);

        long id = 1L;
        final Example.Car car = new Example.Car(id++, "Audi", "A3", 2017);

        final List<Example.Car> cars = new LinkedList<>();
        cars.add(new Example.Car(id++, "Audi", "Q7", 2018));
        cars.add(new Example.Car(id++, "Toyota", "Yaris", 2017));
        cars.add(new Example.Car(id++, "Renault", "Captur", 2019));
        cars.add(new Example.Car(id++, "Renault", "Kadjar", 2019));

        println("Insert: " + db.insert(car));
        println("Insert2: " + db.insert2(cars));

        for (Example.Car c : db.queryAll()) {
            println(c.id + ", " + c.vendor + ", " + c.model + ", " + c.year);
        }
        println("*********************************************");

        car.year = 2019;
        println("Update: " + db.update(car));
        System.out.println("Update2: " + db.update2(2019, 2020));

        for (Example.Car c : db.queryAll()) {
            println(c.id + ", " + c.vendor + ", " + c.model + ", " + c.year);
        }
        println("*********************************************");

        println("Delete: " + db.delete(cars.get(0)));
        println("Delete2: " + db.delete2("Renault", "Kadjar"));

        for (Example.Car c : db.queryAll()) {
            println(c.id + ", " + c.vendor + ", " + c.model + ", " + c.year);
        }
        println("*********************************************");
    }
}
```
