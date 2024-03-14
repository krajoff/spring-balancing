package com.example.balancing.services.target;

import com.example.balancing.models.target.Target;
import com.example.balancing.repository.RecordRepository;
import com.example.balancing.repository.TargetRepository;
import com.example.balancing.services.record.RecordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {TargetService.class, TargetRepository.class})
public class TargetServiceTest {
    @InjectMocks
    private TargetService targetService = new TargetServiceImpl();

    @Mock
    private TargetRepository targetRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddTarget() {
        Target target = new Target();
        targetService.createTarget(target);
        Mockito.verify(targetRepository,
                Mockito.times(1)).save(target);
    }

    @Test
    public void testDeleteTarget() {
        Long id = 1L;
        targetService.deleteTarget(id);
        Mockito.verify(targetRepository,
                Mockito.times(1)).deleteById(id);
    }
}
