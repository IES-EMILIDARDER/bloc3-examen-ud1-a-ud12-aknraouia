package examen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main2 {

    public static void main(String[] args) {

        //try(Stream<String> linies = Files.lines(Paths.get("c:\\temp\\vehicles.csv"))) {
            
            List<Vehicle> vehicles = Arrays.asList(
    new Vehicle("1234-HJK", "Toyota",     "Corolla", 2010, 12000),
    new Vehicle("5678-KLM", "Volkswagen", "Golf",    2018, 18000),
    new Vehicle("9012-NPR", "Seat",       "Ibiza",   2015, 10000),
    new Vehicle("3456-STU", "Tesla",      "Model 3", 2022, 40000),
    new Vehicle("7890-VWX", "Renault",    "Clio",    2012,  9000)
);
            
    
            
            long vehicles1 = vehicles.stream()
                    .filter(p -> p.getPreu() > 15000)
                    .count();
            
            double max = vehicles.stream().map(n -> n.getPreu()).mapToDouble(n->n).max().orElse(0);
            double min = vehicles.stream().map(p->p.getPreu()).mapToDouble(e-> e).min().orElse(0);
            double avg = vehicles.stream().map(p->p.getPreu()).mapToDouble(n->n).average().orElse(0);
            
           
            System.out.println("vehicles amb preu major: " + vehicles1);
            System.out.println("Mas caro: " + max);
            System.out.println("Mas barato: " + min);
            System.out.println("Precio medio: " + avg);
        }
        
    }


