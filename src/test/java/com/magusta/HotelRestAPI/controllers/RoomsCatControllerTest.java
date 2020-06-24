package com.magusta.HotelRestAPI.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.magusta.HotelRestAPI.models.RoomsCat;
import com.magusta.HotelRestAPI.repositories.RoomsCatRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RoomsCatController.class)
class RoomsCatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RoomsCatRepository roomsCatRepository;

    @MockBean
    private RoomsCatController roomsCatController;

    @Test
    void insertRoomsCategoryTest_validInput() throws Exception {
        RoomsCat roomsCat = new RoomsCat("Apartments", true);

        mockMvc.perform(post("/api/roomscat")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(roomsCat)))
                .andExpect(status().isOk());
    }

    @Test
    void insertRoomsCategoryTest_blankValue() throws Exception {
        RoomsCat roomsCat = new RoomsCat("", true);

        mockMvc.perform(post("/api/roomscat")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(roomsCat)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void insertRoomsCategoryTest_mapsToBusinessModel() throws Exception {
        RoomsCat roomsCat = new RoomsCat("Apartments", true);

        mockMvc.perform(post("/api/roomscat")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(roomsCat)));

        ArgumentCaptor<RoomsCat> roomsCatArgumentCaptor = ArgumentCaptor.forClass(RoomsCat.class);
        verify(roomsCatController, times(1)).insertRoomsCategory(roomsCatArgumentCaptor.capture());
        assertThat(roomsCatArgumentCaptor.getValue().getNameOfCat()).isEqualTo("Apartments");
        assertThat(roomsCatArgumentCaptor.getValue().isKitchen()).isEqualTo(true);
    }

    @Test
    void updateRoomsCategoryTest_mapsToBusinessModel() throws Exception {
        RoomsCat roomsCat = new RoomsCat("Apartments", true);

        mockMvc.perform(put("/api/roomscat/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(roomsCat)));

        ArgumentCaptor<RoomsCat> roomsCatArgumentCaptor = ArgumentCaptor.forClass(RoomsCat.class);
        verify(roomsCatController, times(1)).updateRoomsCategory(eq(1), roomsCatArgumentCaptor.capture());
        assertThat(roomsCatArgumentCaptor.getValue().getNameOfCat()).isEqualTo("Apartments");
        assertThat(roomsCatArgumentCaptor.getValue().isKitchen()).isEqualTo(true);
    }

    @Test
    void getCategoryByIdTest_whenExists() throws Exception {

        RoomsCat roomsCat = new RoomsCat("Apartments", true);

        given(roomsCatController.getCategoryById(1)).willReturn(roomsCat);

        MockHttpServletResponse response = mockMvc
                .perform(get("/api/roomscat/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(roomsCat));
    }

    @Test
    void deleteRoomsCategoryTest() throws Exception {

        mockMvc.perform(delete("/api/roomscat/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}