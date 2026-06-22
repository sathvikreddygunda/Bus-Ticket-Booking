package com.busticketbooking.service;

import com.busticketbooking.dto.BusDto;
import com.busticketbooking.enums.BusType;
import com.busticketbooking.exception.ResourceNotFoundException;
import com.busticketbooking.mapper.BusMapper;
import com.busticketbooking.model.Bus;
import com.busticketbooking.model.BusOperator;
import com.busticketbooking.repository.BusRepository;
import com.busticketbooking.repository.SeatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BusServiceTest {

    // Which repository(s) are u mocking

    @Mock
    private BusRepository busRepository;

    @Mock
    private SeatRepository seatRepository;

    @Mock
    private BusOperatorService busOperatorService;

    private BusMapper busMapper;

    // In which service are u testing

    private BusService busService;

    private Bus bus;
    private Bus bus1;

    private BusOperator operator;

    // Common Sample data for all test cases
    // Sequence:- Sample data loads - Test case runs - Sample data deloads

    @BeforeEach
    public void sampleData() {

        busMapper = new BusMapper();

        busService = new BusService(
                busRepository,
                busMapper,
                busOperatorService,
                seatRepository
        );

        operator = new BusOperator();
        operator.setOperatorId(1);
        operator.setOperatorName("PK Travels");
        operator.setEmail("PK@gmail.com");

        bus = new Bus();
        bus.setBusId(1);
        bus.setBusName("Orange Travels");
        bus.setBusNumber("AP01AA1111");
        bus.setBusType(BusType.AC);
        bus.setTotalSeats(16);
        bus.setBusOperator(operator);

        bus1 = new Bus();
        bus1.setBusId(2);
        bus1.setBusName("VRL Travels");
        bus1.setBusNumber("AP01AA2222");
        bus1.setBusType(BusType.NON_AC);
        bus1.setTotalSeats(16);
        bus1.setBusOperator(operator);
    }

    @Test
    public void getAllBuses_MustReturnSomething() {

        when(busRepository.findAll()).thenReturn(List.of(bus, bus1));

        List<Bus> actualCall = busService.getAll();

        assertThat(actualCall).hasSize(2);
        assertThat(actualCall.getFirst().getBusName()).isEqualToIgnoringCase("Orange Travels");
        assertThat(actualCall.get(1).getBusName()).isEqualToIgnoringCase("VRL Travels");
    }

    @Test
    public void getAllBuses_ReturnsEmptyList() {

        when(busRepository.findAll()).thenReturn(List.of());

        // Actual Call
        List<Bus> actualCall = busService.getAll();

        assertThat(actualCall).isEmpty();
    }

    @Test
    void getById_busExists() {

        when(busRepository.findById(100)).thenReturn(Optional.of(bus));
        when(busRepository.findById(200)).thenReturn(Optional.of(bus1));

        assertThat(busService.getById(100).getBusId()).isEqualTo(1);
        assertThat(busService.getById(200).getBusId()).isEqualTo(2);

        assertThat(busService.getById(100).getBusName()).isEqualTo("Orange Travels");
        assertThat(busService.getById(200).getBusName()).isEqualTo("VRL Travels");
    }

    @Test
    void getById_busDoesNotExist() {

        when(busRepository.findById(100)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> busService.getById(100))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Invalid Bus ID");

        verify(busRepository, times(1)).findById(100);
    }

    @Test
    void addBus_mustSaveBus() {

        when(busOperatorService.getByEmail("operator@gmail.com")).thenReturn(operator);

        BusDto dto = new BusDto("Orange Travels", "AP01AA1111",
                BusType.AC, 16, 1000);

        busService.addBus(dto, "operator@gmail.com");

        verify(busRepository, times(1)).save(any(Bus.class));

        // 16 seats should be created
        verify(seatRepository, times(16)).save(any());
    }

    @Test
    void deleteBus_mustDeleteAndReturnNothing() {

        when(busRepository.findById(100)).thenReturn(Optional.of(bus));
        when(busOperatorService.getByEmail("operator@gmail.com")).thenReturn(operator);

        // When thenReturn does not work in void method tests
        doNothing().when(busRepository).deleteById(100);
        busService.deleteById(100, "operator@gmail.com"
        );

        // Check if repo call happens only once
        verify(busRepository, times(1)).deleteById(100);
        verify(busRepository, times(1)).findById(100);
    }

    @Test
    void getByBusType_mustReturnBuses() {

        when(busRepository.findByBusType(BusType.AC))
                .thenReturn(List.of(bus));

        List<Bus> actualCall = busService.getByBusType(BusType.AC);

        assertThat(actualCall).hasSize(1);
        assertThat(actualCall.getFirst().getBusType()).isEqualTo(BusType.AC);
    }

    @Test
    void getBusCount_mustReturnCount() {

        when(busRepository.count()).thenReturn(2L);

        long count = busService.getBusCount();
        assertThat(count).isEqualTo(2L);

        verify(busRepository, times(1)).count();
    }
}