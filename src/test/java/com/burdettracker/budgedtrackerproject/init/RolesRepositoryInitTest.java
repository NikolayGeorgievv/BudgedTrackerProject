package com.burdettracker.budgedtrackerproject.init;

import com.burdettracker.budgedtrackerproject.repository.RolesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RolesRepositoryInitTest {
    private RolesRepositoryInit rolesRepositoryInit;
    private RolesRepository rolesRepository;

    @BeforeEach
    public void setUp() {
        rolesRepository = Mockito.mock(RolesRepository.class);
        rolesRepositoryInit = new RolesRepositoryInit(rolesRepository);
    }

    @Test
    public void testRun() throws Exception {
        when(rolesRepository.count()).thenReturn(0L);

        rolesRepositoryInit.run();

        verify(rolesRepository, times(1)).saveAllAndFlush(any(List.class));
    }
}