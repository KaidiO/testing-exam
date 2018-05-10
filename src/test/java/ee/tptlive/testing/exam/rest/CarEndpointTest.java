package ee.tptlive.testing.exam.rest;

import ee.tptlive.testing.exam.model.Car;
import ee.tptlive.testing.exam.service.CarService;
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
//public class CarEndpointTest {
//
//}
@RunWith(MockitoJUnitRunner.class)
public class CarEndpointTest {

    @Mock
    private CarService carService;

    @InjectMocks
    private CarEndpoint carEndpoint;

    @Test
    public void getAll_ReturnsEmptyList_IfServiceReturnsEmptyList() {
        // given
        String brand = "Lexus";
        Boolean available = true;

        when(carService.getAllFilteredByBrandAndAvailability(brand, available)).thenReturn(emptyList());

        // when
        List<Car> result = carEndpoint.getAll(brand, available);

        // then
        assertTrue(result.isEmpty());
    }

    @Test
    public void getAll_ReturnsCars_IfServiceReturnsCars() {
        // given
        String brand = "Lexus";
        Boolean available = true;

        when(carService.getAllFilteredByBrandAndAvailability(brand, available)).thenReturn(asList(
                new Car(1, "BMW", "320i", 2010, PETROL, true),
                new Car(2, "BMW", "X5", 2017, DIESEL, true)
        ));

        // when
        List<Car> result = carEndpoint.getAll(brand, available);

        // then
        assertEquals(2, result.size());
        assertEquals("BMW", result.get(0).getBrand());
        assertEquals("BMW", result.get(1).getBrand());
    }

}