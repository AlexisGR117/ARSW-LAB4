/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.test.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.impl.InMemoryBlueprintPersistence;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author hcadavid
 */
public class InMemoryPersistenceTest {

    private InMemoryBlueprintPersistence ibpp;

    @Before
    public void init() {
        ibpp = new InMemoryBlueprintPersistence();
    }

    @Test
    public void saveNewAndLoadTest() throws BlueprintPersistenceException, BlueprintNotFoundException {

        Point[] pts0 = new Point[]{new Point(40, 40), new Point(15, 15)};
        Blueprint bp0 = new Blueprint("mack", "mypaint", pts0);

        ibpp.saveBlueprint(bp0);

        Point[] pts = new Point[]{new Point(0, 0), new Point(10, 10)};
        Blueprint bp = new Blueprint("john", "thepaint", pts);

        ibpp.saveBlueprint(bp);

        assertNotNull("Loading a previously stored blueprint returned null.", ibpp.getBlueprint(bp.getAuthor(), bp.getName()));

        assertEquals("Loading a previously stored blueprint returned a different blueprint.", ibpp.getBlueprint(bp.getAuthor(), bp.getName()), bp);
    }


    @Test
    public void saveExistingBpTest() {
        Point[] pts = new Point[]{new Point(0, 0), new Point(10, 10)};
        Blueprint bp = new Blueprint("john", "thepaint", pts);

        try {
            ibpp.saveBlueprint(bp);
        } catch (BlueprintPersistenceException ex) {
            fail("Blueprint persistence failed inserting the first blueprint.");
        }

        Point[] pts2 = new Point[]{new Point(10, 10), new Point(20, 20)};
        Blueprint bp2 = new Blueprint("john", "thepaint", pts2);

        try {
            ibpp.saveBlueprint(bp2);
            fail("An exception was expected after saving a second blueprint with the same name and autor");
        } catch (BlueprintPersistenceException ex) {
            assertEquals(BlueprintPersistenceException.EXISTING_BLUEPRINT + bp2, ex.getMessage());
        }
    }

    @Test
    public void getBluePrintTest() {
        Point[] pts = new Point[]{new Point(0, 0), new Point(10, 10)};
        String author = "john";
        String name = "thepaint";
        Blueprint bp = new Blueprint(author, name, pts);
        try {
            ibpp.saveBlueprint(bp);
        } catch (BlueprintPersistenceException ex) {
            fail("Blueprint persistence failed inserting the first blueprint.");
        }
        try {
            assertEquals(bp, ibpp.getBlueprint(author, name));
        } catch (BlueprintNotFoundException ex) {
            fail("Blueprint failed, there is no such blueprint.");
        }
    }

    @Test
    public void getBluePrintByAuthorTest() {
        Point[] pts = new Point[]{new Point(0, 0), new Point(10, 10)};
        String author = "john";
        Set<Blueprint> blueprintSet = new HashSet<>();
        Blueprint bp1 = new Blueprint(author, "thepaint", pts);
        blueprintSet.add(bp1);
        Blueprint bp2 = new Blueprint("juan", "thepaint2", pts);
        Blueprint bp3 = new Blueprint(author, "thepaint3", pts);
        blueprintSet.add(bp3);
        try {
            ibpp.saveBlueprint(bp1);
            ibpp.saveBlueprint(bp2);
            ibpp.saveBlueprint(bp3);
        } catch (BlueprintPersistenceException ex) {
            fail("Blueprint persistence failed inserting the first blueprint.");
        }
        try {
            assertEquals(blueprintSet, ibpp.getBlueprintByAuthor(author));
        } catch (BlueprintNotFoundException ex) {
            fail("Blueprint failed, the given author doesn't exist.");
        }
    }


    @Test
    public void getBluePrintNotFoundTest() {
        try {
            ibpp.getBlueprint("jhon", "thepaint");
            fail("Did not throw exception");
        } catch (BlueprintNotFoundException ex) {
            assertEquals(BlueprintNotFoundException.BLUEPRINT_NOT_FOUND, ex.getMessage());
        }
    }

    @Test
    public void getBluePrintsOfAnAuthorNotFoundTest() {
        try {
            ibpp.getBlueprintByAuthor("jhon");
            fail("Did not throw exception");
        } catch (BlueprintNotFoundException ex) {
            assertEquals(BlueprintNotFoundException.AUTHOR_NOT_FOUND, ex.getMessage());
        }
    }
}
