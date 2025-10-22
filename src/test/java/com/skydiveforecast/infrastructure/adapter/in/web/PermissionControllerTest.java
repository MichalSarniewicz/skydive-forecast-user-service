package com.skydiveforecast.infrastructure.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skydiveforecast.domain.port.in.CreatePermissionUseCase;
import com.skydiveforecast.domain.port.in.DeletePermissionUseCase;
import com.skydiveforecast.domain.port.in.GetAllPermissionsUseCase;
import com.skydiveforecast.domain.port.in.UpdatePermissionUseCase;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.PermissionsDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PermissionControllerTest {

    private MockMvc mockMvc;
    private GetAllPermissionsUseCase getAllPermissionsUseCase;

    @BeforeEach
    void setup() {
        getAllPermissionsUseCase = mock(GetAllPermissionsUseCase.class);
        CreatePermissionUseCase createPermissionUseCase = mock(CreatePermissionUseCase.class);
        UpdatePermissionUseCase updatePermissionUseCase = mock(UpdatePermissionUseCase.class);
        DeletePermissionUseCase deletePermissionUseCase = mock(DeletePermissionUseCase.class);
        PermissionController adminPermissionController = new PermissionController(getAllPermissionsUseCase,
                createPermissionUseCase, updatePermissionUseCase, deletePermissionUseCase);
        mockMvc = MockMvcBuilders.standaloneSetup(adminPermissionController).build();
    }

    @Test
    void getAllPermissions_shouldReturnListOfPermissions() throws Exception {
        // Arrange
        PermissionsDto permissionsDto = PermissionsDto.builder().build();
        when(getAllPermissionsUseCase.getAllPermissions()).thenReturn(permissionsDto);

        // Act & Assert
        mockMvc.perform(get("/api/users/permissions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(permissionsDto)));
    }

    @Test
    void getAllPermissions_shouldHandleEmptyList() throws Exception {
        // Arrange
        PermissionsDto emptyPermissionsDto = PermissionsDto.builder().build();
        when(getAllPermissionsUseCase.getAllPermissions()).thenReturn(emptyPermissionsDto);

        // Act & Assert
        mockMvc.perform(get("/api/users/permissions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(emptyPermissionsDto)));
    }
}