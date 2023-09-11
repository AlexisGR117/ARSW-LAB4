package edu.eci.arsw.blueprints.test.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.impl.RedudancyFilter;
import edu.eci.arsw.blueprints.persistence.impl.SubsamplingFilter;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class SubsamplingFilterTest {

    private Blueprint bp1, bp2;
    private SubsamplingFilter rf;

    @Before
    public void init() {
        rf = new SubsamplingFilter();
        Point[] pts0 = new Point[]{new Point(50, 20), new Point(80, 40), new Point(10, 15), new Point(20, 15), new Point(60, 5)};
        bp1 = new Blueprint("mack", "mypaint", pts0);
        Point[] pts1 = new Point[]{new Point(80, 40), new Point(20, 15)};
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
        Point[] pts3 = new Point[]{new Point(20, 15)};
        Blueprint bp3 = new Blueprint("mack", "mypaint", pts3);
        blueprintsResult.add(bp3);
        blueprintsFilter = rf.filterBlueprints(blueprintsFilter);
        assertEquals(blueprintsResult, blueprintsFilter);
    }
}
