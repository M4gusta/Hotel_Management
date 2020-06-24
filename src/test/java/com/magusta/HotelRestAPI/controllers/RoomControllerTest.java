package com.magusta.HotelRestAPI.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.magusta.HotelRestAPI.models.Room;
import com.magusta.HotelRestAPI.repositories.RoomRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RoomController.class)
class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RoomRepository roomRepository;

    @MockBean
    private RoomController roomController;

    @Test
    void insertRoomTest_validInput() throws Exception {
        Room room = new Room(100, "Normal", 40);

        mockMvc.perform(post("/api/rooms")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(room)))
                .andExpect(status().isOk());
    }
    @Test
    void insertRoomTest_blankValue() throws Exception {
        Room room = new Room(100, "", 50);

        mockMvc.perform(post("/api/rooms")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(room)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void insertRoomTest_mapsToBusinessModel() throws Exception {
        Room room = new Room(100, "Normal", 40);

        mockMvc.perform(post("/api/rooms")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(room)));

        ArgumentCaptor<Room> roomArgumentCaptor = ArgumentCaptor.forClass(Room.class);
        verify(roomController, times(1)).insertRoom(roomArgumentCaptor.capture());
        assertThat(roomArgumentCaptor.getValue().getRoomNum()).isEqualTo(100);
        assertThat(roomArgumentCaptor.getValue().getRoomCat()).isEqualToIgnoringCase("normal");
    }

    @Test
    void updateRoomTest_mapsToBusinessModel() throws Exception {
        Room room = new Room(100, "Normal", 40);

        mockMvc.perform(put("/api/rooms/101")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(room)));

        ArgumentCaptor<Room> roomArgumentCaptor = ArgumentCaptor.forClass(Room.class);
        verify(roomController, times(1)).updateRoom(eq(101), roomArgumentCaptor.capture());
        assertThat(roomArgumentCaptor.getValue().getRoomCat()).isEqualToIgnoringCase("normal");
    }

    @Test
    void getRoomByIdTest_whenExists() throws Exception {

        Room room = new Room(100, "Normal", 40);

        given(roomController.getRoomById(100))
                .willReturn(room);

        MockHttpServletResponse response = mockMvc
                .perform(get("/api/rooms/100")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(room));
    }

    @Test
    void deleteRoomTest() throws Exception {

        mockMvc.perform(delete("/api/rooms/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}