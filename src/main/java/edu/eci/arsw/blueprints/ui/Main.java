package edu.eci.arsw.blueprints.ui;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] a) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        BlueprintsServices blueprintService = ac.getBean(BlueprintsServices.class);
        String author = "mack";
        Point[] pts0 = new Point[]{new Point(40, 40), new Point(40, 40), new Point(15, 15), new Point(15, 15)};
        Blueprint bp0 = new Blueprint(author, "mypaint", pts0);
        Point[] pts1 = new Point[]{new Point(50, 20), new Point(80, 40), new Point(10, 15), new Point(20, 15), new Point(60, 5)};
        Blueprint bp1 = new Blueprint("john", "thepaint", pts1);
        Point[] pts2 = new Point[]{new Point(80, 10), new Point(10, 5), new Point(10, 5)};
        Blueprint bp2 = new Blueprint(author, "mypaint2", pts2);
        System.out.println("Agregando blueprints:");
        try {
            blueprintService.addNewBlueprint(bp0);
            blueprintService.addNewBlueprint(bp1);
            blueprintService.addNewBlueprint(bp2);
        } catch (BlueprintPersistenceException ex) {
            ex.printStackTrace();
        }
        System.out.println("\nObteniendo todos los Blueprints (con filtro)...");
        blueprintService.getAllBlueprints().forEach(System.out::println);
        System.out.println("\nObteniendo todos los Blueprints del autor " + author + " (con filtro): ");
        try {
            blueprintService.getBlueprintsByAuthor(author).forEach(System.out::println);
        } catch (BlueprintNotFoundException ex) {
            ex.printStackTrace();
        }
        System.out.println("\nObteniendo un blueprint especifico (" + bp2 + ") (con filtro): ");
        try {
            System.out.println(blueprintService.getBlueprint(bp2.getAuthor(), bp2.getName()));
        } catch (BlueprintNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
