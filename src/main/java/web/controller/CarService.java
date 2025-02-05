package web.controller;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;

@Service

public class CarService {
    private final List<Car> cars = Arrays.asList(
            new Car("Toyota","A",  2020),
            new Car("Honda", "S", 2019),
            new Car("Ford",  "R",2021),
            new Car("Chevrolet",  "K",2018),
            new Car("BMW",  "L",2022)
    );

    public List<Car> getCars(int count) {
        if (count >= 5) {
            return cars;
        }
        return cars.subList(0, count);
    }
}


