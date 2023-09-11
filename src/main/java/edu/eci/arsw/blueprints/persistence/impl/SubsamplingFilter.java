package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.Filter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

public class SubsamplingFilter extends Filter {
    @Override
    public Blueprint filterBlueprint(Blueprint blueprint) {
        List<Point> points = new ArrayList<>(blueprint.getPoints());
        Blueprint newBlueprint = new Blueprint(blueprint);
        for (int i = 0; i < points.size(); i += 2) newBlueprint.removePoint(points.get(i));
        return newBlueprint;
    }
}