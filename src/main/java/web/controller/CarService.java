package web.controller;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;

@Service

public class CarService {
    private final List<Car> cars = Arrays.asList(
            new Car("LADA","GRANTA",  2020),
            new Car("KAMAZ", "NEO", 2019),
            new Car("Ford",  "FOCUS",2021),
            new Car("JAC",  "JAC",2018),
            new Car("BMW",  "BRABUS",2022)
    );

    public List<Car> getCars(int count) {
        if (count >= 5) {
            return cars;
        }
        return cars.subList(0, count);
    }
}


