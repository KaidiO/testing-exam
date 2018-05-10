package ee.tptlive.testing.exam.service;

import ee.tptlive.testing.exam.dao.CarDao;
import ee.tptlive.testing.exam.model.Car;
import ee.tptlive.testing.exam.model.CarEngineType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static ee.tptlive.testing.exam.model.CarEngineType.DIESEL;
import static ee.tptlive.testing.exam.model.CarEngineType.PETROL;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

//@RunWith(MockitoJUnitRunner.class)
//public class CarServiceTest {
//
//}
@RunWith(MockitoJUnitRunner.class)
public class CarServiceTest {

    @Mock
    private CarDao carDao;

    @InjectMocks
    private CarService carService;

    @Test
    public void getAll_ReturnsEmptyList_IfNothingFound() {
        // given
        when(carDao.findAll()).thenReturn(emptyList());

        // when
        List<Car> result = carService.getAll();

        // then
        assertTrue(result.isEmpty());
    }

    @Test
    public void getAll_ReturnsAllFromDao_IfDaoFindsSomething() {
        // given
        when(carDao.findAll()).thenReturn(asList(
                new Car(1, "BMW", "320i", 2010, PETROL, true),
                new Car(2, "BMW", "X5", 2017, DIESEL, true)
        ));

        // when
        List<Car> result = carService.getAll();

        // then
        assertEquals(2, result.size());
        assertEquals("BMW", result.get(0).getBrand());
        assertEquals("BMW", result.get(1).getBrand());
    }

    @Test
    public void getAllFilteredByBrandAndAvailable_ReturnsEmptyList_IfNothingFoundAndNoFilters() {
        // given
        when(carDao.findAll()).thenReturn(emptyList());

        String brand = null;
        Boolean available = null;

        // when
        List<Car> result = carService.getAllFilteredByBrandAndAvailability(brand, available);

        // then
        assertTrue(result.isEmpty());
    }

    @Test
    public void getAllFilteredByBrandAndAvailable_ReturnsEmptyList_IfNothingFoundAndHasFilters() {
        // given
        when(carDao.findAll()).thenReturn(emptyList());

        String brand = "e";
        Boolean available = true;

        // when
        List<Car> result = carService.getAllFilteredByBrandAndAvailability(brand, available);

        // then
        assertTrue(result.isEmpty());
    }

    @Test
    public void getAllFilteredByBrandAndAvailable_ReturnsEmptyList_IfFoundAndEverythingIsFilteredOut() {
        // given
        when(carDao.findAll()).thenReturn(asList(
                new Car(1, "BMW", "320i", 2010, CarEngineType.PETROL, true),
                new Car(2, "BMW", "X5", 2017, CarEngineType.DIESEL, true),
                new Car(3, "Lexus", "NX200", 2015, CarEngineType.HYBRYD, false),
                new Car(4, "Toyota", "Prius", 2010, CarEngineType.ELECTRIC, true),
                new Car(5, "Toyota", "Avensis", 1995, CarEngineType.PETROL, true),
                new Car(6, "Audi", "RS3", 2018, CarEngineType.PETROL, false),
                new Car(7, "Mitsubishi", "Eclipse", 1999, CarEngineType.DIESEL, true)
        ));

        String brand = "this_will_not_match_anything";
        Boolean available = true;

        // when
        List<Car> result = carService.getAllFilteredByBrandAndAvailability(brand, available);

        // then
        assertTrue(result.isEmpty());
    }

    @Test
    public void getAllFilteredByBrandAndAvailable_ReturnsOneEntry_IfFoundAndFilteringByNameWithExactMatch() {
        // given
        when(carDao.findAll()).thenReturn(asList(
                new Car(1, "BMW", "320i", 2010, CarEngineType.PETROL, true),
                new Car(2, "BMW", "X5", 2017, CarEngineType.DIESEL, true),
                new Car(3, "Lexus", "NX200", 2015, CarEngineType.HYBRYD, false),
                new Car(4, "Toyota", "Prius", 2010, CarEngineType.ELECTRIC, true),
                new Car(5, "Toyota", "Avensis", 1995, CarEngineType.PETROL, true),
                new Car(6, "Audi", "RS3", 2018, CarEngineType.PETROL, false),
                new Car(7, "Mitsubishi", "Eclipse", 1999, CarEngineType.DIESEL, true)
        ));

        String brand = "Lexus";
        Boolean available = null;

        // when
        List<Car> result = carService.getAllFilteredByBrandAndAvailability(brand, available);

        // then
        assertEquals(1, result.size());
        assertEquals("Lexus", result.get(0).getBrand());
    }

    @Test
    public void getAllFilteredByBrandAndAvailable_ReturnsOneEntry_IfFoundAndFilteringByNameWithCaseInsensitiveMatch() {
        // given
        when(carDao.findAll()).thenReturn(asList(
                new Car(1, "BMW", "320i", 2010, CarEngineType.PETROL, true),
                new Car(2, "BMW", "X5", 2017, CarEngineType.DIESEL, true),
                new Car(3, "Lexus", "NX200", 2015, CarEngineType.HYBRYD, false),
                new Car(4, "Toyota", "Prius", 2010, CarEngineType.ELECTRIC, true),
                new Car(5, "Toyota", "Avensis", 1995, CarEngineType.PETROL, true),
                new Car(6, "Audi", "RS3", 2018, CarEngineType.PETROL, false),
                new Car(7, "Mitsubishi", "Eclipse", 1999, CarEngineType.DIESEL, true)
        ));

        String brand = "LeXuS";
        Boolean available = null;

        // when
        List<Car> result = carService.getAllFilteredByBrandAndAvailability(brand, available);

        // then
        assertEquals(1, result.size());
        assertEquals("Lexus", result.get(0).getBrand());
    }


    @Test
    public void getAllFilteredByBrandAndAvailable_ReturnsAllWomen_IfFoundAndFilteringByBrandAndAvailable() {
        // given
        when(carDao.findAll()).thenReturn(asList(
                new Car(1, "BMW", "320i", 2010, CarEngineType.PETROL, true),
                new Car(2, "BMW", "X5", 2017, CarEngineType.DIESEL, true),
                new Car(3, "Lexus", "NX200", 2015, CarEngineType.HYBRYD, false),
                new Car(4, "Toyota", "Prius", 2010, CarEngineType.ELECTRIC, true),
                new Car(5, "Toyota", "Avensis", 1995, CarEngineType.PETROL, true),
                new Car(6, "Audi", "RS3", 2018, CarEngineType.PETROL, false),
                new Car(7, "Mitsubishi", "Eclipse", 1999, CarEngineType.DIESEL, true)
        ));

        String brand = null;
        Boolean available = true;

        // when
        List<Car> result = carService.getAllFilteredByBrandAndAvailability(brand, available);

        // then
        assertEquals(5, result.size());
        assertEquals("BMW", result.get(0).getBrand());
        assertEquals("BMW", result.get(1).getBrand());
        assertEquals("Toyota", result.get(2).getBrand());
        assertEquals("Toyota", result.get(3).getBrand());
        assertEquals("Mitsubishi", result.get(4).getBrand());
    }

    @Test
    public void getAllFilteredByBrandAndAvailable_ReturnsAllMen_IfFoundAndFilteringByAvailableWithFalse() {
        // given
        when(carDao.findAll()).thenReturn(asList(
                new Car(1, "BMW", "320i", 2010, CarEngineType.PETROL, true),
                new Car(2, "BMW", "X5", 2017, CarEngineType.DIESEL, true),
                new Car(3, "Lexus", "NX200", 2015, CarEngineType.HYBRYD, false),
                new Car(4, "Toyota", "Prius", 2010, CarEngineType.ELECTRIC, true),
                new Car(5, "Toyota", "Avensis", 1995, CarEngineType.PETROL, true),
                new Car(6, "Audi", "RS3", 2018, CarEngineType.PETROL, false),
                new Car(7, "Mitsubishi", "Eclipse", 1999, CarEngineType.DIESEL, true)
        ));

        String brand = null;
        Boolean available = false;

        // when
        List<Car> result = carService.getAllFilteredByBrandAndAvailability(brand, available);

        // then
        assertEquals(2, result.size());
        assertEquals("Lexus", result.get(0).getBrand());
        assertEquals("Audi", result.get(1).getBrand());
    }

    @Test
    public void getAllFilteredByBrandAndAvailable_ReturnsOneEntry_IfFoundAndFilteringByBrandAndAvailableWithFalse() {
        // given
        when(carDao.findAll()).thenReturn(asList(
                new Car(1, "BMW", "320i", 2010, CarEngineType.PETROL, true),
                new Car(2, "BMW", "X5", 2017, CarEngineType.DIESEL, true),
                new Car(3, "Lexus", "NX200", 2015, CarEngineType.HYBRYD, false),
                new Car(4, "Toyota", "Prius", 2010, CarEngineType.ELECTRIC, true),
                new Car(5, "Toyota", "Avensis", 1995, CarEngineType.PETROL, true),
                new Car(6, "Audi", "RS3", 2018, CarEngineType.PETROL, false),
                new Car(7, "Mitsubishi", "Eclipse", 1999, CarEngineType.DIESEL, true)
        ));

        String brand = "XUS";
        Boolean available = false;

        // when
        List<Car> result = carService.getAllFilteredByBrandAndAvailability(brand, available);

        // then
        assertEquals(1, result.size());
        assertEquals("Lexus", result.get(0).getBrand());
    }
}

