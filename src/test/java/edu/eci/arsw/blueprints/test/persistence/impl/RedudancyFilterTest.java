package edu.eci.arsw.blueprints.test.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.impl.InMemoryBlueprintPersistence;
import edu.eci.arsw.blueprints.persistence.impl.RedudancyFilter;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class RedudancyFilterTest {
    private Blueprint bp1, bp2;
    private RedudancyFilter rf;

    @Before
    public void init() {
        rf = new RedudancyFilter();
        Point[] pts0 = new Point[]{new Point(40, 40), new Point(40, 40), new Point(15, 15), new Point(15, 15)};
        bp1 = new Blueprint("mack", "mypaint", pts0);
        Point[] pts1 = new Point[]{new Point(40, 40), new Point(15, 15)};
        bp2 = new Blueprint("mack", "mypaint", pts1);
    }

    @Test
    public void filterBlueprintByRedundancyTest() {
        Blueprint blueprintFilter = rf.filterBlueprint(bp1);
        assertEquals(bp2, blueprintFilter);
    }
    @Test
    public void filterBlueprintsByRedundancyTest() {
        Set<Blueprint> blueprintsResult = new HashSet<>();
        Set<Blueprint> blueprintsFilter = new HashSet<>();
        blueprintsFilter.add(bp1);
        blueprintsFilter.add(bp2);
        blueprintsResult.add(bp2);
        blueprintsResult.add(bp2);
        blueprintsFilter = rf.filterBlueprints(blueprintsFilter);
        assertEquals(blueprintsResult, blueprintsFilter);
    }
}
