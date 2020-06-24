package com.magusta.HotelRestAPI.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.magusta.HotelRestAPI.models.Guest;
import com.magusta.HotelRestAPI.repositories.GuestRepository;
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

@WebMvcTest(controllers = GuestController.class)
class GuestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GuestRepository guestRepository;

    @MockBean
    private GuestController guestController;

    @Test
    void insertGuestTest_validInput() throws  Exception {
        Guest guest = new Guest("George", "Law", "george.law@mail.com");

        mockMvc.perform(post("/api/guests")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(guest)))
                .andExpect(status().isOk());
    }

    @Test
    void insertGuestTest_blankValue() throws Exception {
        Guest guest = new Guest("", "Law", "george.law@mail.com");

        mockMvc.perform(post("/api/guests")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(guest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void insertGuestTest_mapsToBusinessModel() throws Exception {
        Guest guest = new Guest("George", "Law", "george.law@mail.com");

        mockMvc.perform(post("/api/guests")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(guest)));

        ArgumentCaptor<Guest> guestArgumentCaptor = ArgumentCaptor.forClass(Guest.class);
        verify(guestController, times(1)).insertGuest(guestArgumentCaptor.capture());
        assertThat(guestArgumentCaptor.getValue().getFirstName()).isEqualTo("George");
        assertThat(guestArgumentCaptor.getValue().getMail()).isEqualTo("george.law@mail.com");
    }

    @Test
    void getGuestByIdTest_whenExists() throws Exception {
        Guest guest = new Guest("George", "Law", "george.law@mail.com");

        given(guestController.getGuestById(1)).willReturn(guest);

        MockHttpServletResponse response = mockMvc
                .perform(get("/api/guests/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(guest));
    }

    @Test
    void updateGuestTest_mapToBusinessModel() throws Exception {
        Guest guest = new Guest("George", "Law", "george.law@mail.com");

        mockMvc.perform(put("/api/guests/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(guest)))
                .andDo(MockMvcResultHandlers.print());


        ArgumentCaptor<Guest> guestArgumentCaptor = ArgumentCaptor.forClass(Guest.class);
        verify(guestController, times(1)).updateGuest(eq(1), guestArgumentCaptor.capture());
        assertThat(guestArgumentCaptor.getValue().getFirstName()).isEqualTo("George");
        assertThat(guestArgumentCaptor.getValue().getLastName()).isEqualTo("Law");
    }

    @Test
    void deleteGuestTest() throws Exception {

        mockMvc.perform(delete("/api/guests/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}