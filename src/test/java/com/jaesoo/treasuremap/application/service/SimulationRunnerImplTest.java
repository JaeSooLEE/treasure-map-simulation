package com.jaesoo.treasuremap.application.service;


import com.jaesoo.treasuremap.application.port.in.ExecuteActionUseCase;
import com.jaesoo.treasuremap.application.port.out.MapLoaderPort;
import com.jaesoo.treasuremap.application.port.out.MapWriterPort;
import com.jaesoo.treasuremap.domain.model.explorer.Explorer;
import com.jaesoo.treasuremap.domain.model.map.TreasureMap;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SimulationRunnerImplTest {

    @Mock
    MapLoaderPort mapLoader;
    @Mock
    ExecuteActionUseCase  executeCase;
    @Mock
    MapWriterPort resultWriter;

    @InjectMocks
    SimulationRunnerImpl runner;

    @Test
    void runSimulation_orchestratesCorrectly() {
        TreasureMap fakeMap = mock(TreasureMap.class);
        Explorer e1 = mock(Explorer.class);
        Explorer e2 = mock(Explorer.class);

        when(mapLoader.loadMap("in")).thenReturn(fakeMap);
        when(fakeMap.getExplorers()).thenReturn(List.of(e1, e2));
        when(e1.hasActions()).thenReturn(true).thenReturn(false);
        when(e2.hasActions()).thenReturn(false);

        runner.runSimulation("in", "out");

        InOrder inOrder = inOrder(mapLoader, executeCase, resultWriter, e1, e2);
        inOrder.verify(mapLoader).loadMap("in");
        inOrder.verify(executeCase).executeAction(e1, fakeMap);
        verify(executeCase, never()).executeAction(e2, fakeMap);
        inOrder.verify(resultWriter).writeMap(fakeMap, "out");
        inOrder.verifyNoMoreInteractions();
    }
}
