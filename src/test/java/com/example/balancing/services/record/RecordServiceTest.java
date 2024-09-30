package com.example.balancing.services.record;

import com.example.balancing.dtos.record.RecordDto;
import com.example.balancing.repositories.record.RecordRepository;
import com.example.balancing.models.record.Record;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {RecordService.class, RecordRepository.class})
public class RecordServiceTest {

    @InjectMocks
    private RecordService recordService = new RecordServiceImpl();

    @Mock
    private RecordRepository recordRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddRecord() {
        Record record = new Record();
        recordService.createRecord(record);
        Mockito.verify(recordRepository,
                Mockito.times(1)).save(record);
    }

    @Test
    public void testGetRecordById() {
        Record mockRecord = new Record();
        Long id = mockRecord.getId();
        when(recordRepository.findById(id)).thenReturn(Optional.of(mockRecord));
        RecordDto realRecord = recordService.getRecordById(id);
        assertEquals(id, realRecord.getId());
    }

    @Test
    public void testDeleteRecord() {
        Long id = 1L;
        recordService.deleteRecord(id);
        Mockito.verify(recordRepository,
                Mockito.times(1)).deleteById(id);
    }
}
