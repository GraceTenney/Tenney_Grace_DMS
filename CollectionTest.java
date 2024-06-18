import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class CollectionTest {

    Collection cats = new Collection();

    @Test
    void removeById() {
        cats.add(new Cat(1, "Emmy", "Black", 'F', 16, "Friendly", true));
        cats.removeById(1);
        assertNull(cats.findById(1));
    }

    @Test
    void findById() {
        cats.add(new Cat(1, "Emmy", "Black", 'F', 16, "Friendly", true));
        assertEquals(cats.findById(1).getId(), 1);
    }

    @Test
    void findByName() {
        cats.add(new Cat(1, "Emmy", "Black", 'F', 16, "Friendly", true));
        assertEquals(cats.findByName("Emmy").get(0).getName(), "Emmy");
    }

    @Test
    void select() {
        cats.add(new Cat(1, "Emmy", "Black", 'F', 16, "Friendly", true));
        assertEquals(cats.select("Emmy").getName(), "Emmy");
    }

    @Test
    void add() {
        cats.add(new Cat(1, "Emmy", "Black", 'F', 16, "Friendly", true));
        cats.add(new Cat(1, "Emmy", "Black", 'F', 16, "Friendly", true));
        assertEquals(cats.findById(1).getId(), 1);
    }
    @Test
    void removeByName() {
        cats.add(new Cat(1, "Emmy", "Black", 'F', 16, "Friendly", true));
        Cat cat = cats.select("Emmy");
        cats.removeById(cat.getId());
        assertNull(cats.select("Emmy"));
    }
    @Test
    void update() {
        cats.add(new Cat(1, "Emmy", "Black", 'F', 16, "Friendly", false));
        Cat cat = cats.findById(1);
        cat.setWeight(22);
        cat.setGender('M');
        cat.setDisposition("Affectionate");
        cat.setChipped(true);
        cat.setColoring("Tuxedo");
        cat.setName("Oscar");
        Cat finalCat = cats.findById(1);
        assertEquals(finalCat.getName(), "Oscar");
        assertEquals(finalCat.getDisposition(), "Affectionate");
        assertTrue(finalCat.isChipped());
        assertEquals(finalCat.getGender(), 'M');
        assertEquals(finalCat.getColoring(), "Tuxedo");
    }
    @Test
    void upload() {
        FileIO.initialize();
        FileIO.fc.setSelectedFile(new File("./example.txt"));
        cats.upload();
        Cat cat = cats.findById(1);
        Cat exampleCat = new Cat(1, "Emmy", "Black", 'F', 16, "Friendly and forgiving", true);
        assertEquals(cat.getName(), exampleCat.getName());
        assertEquals(cat.getColoring(), exampleCat.getColoring());
        assertEquals(cat.getGender(), exampleCat.getGender());
        assertEquals(cat.getWeight(), exampleCat.getWeight());
        assertEquals(cat.getDisposition(), exampleCat.getDisposition());
        assertEquals(cat.isChipped(), exampleCat.isChipped());
    }
}