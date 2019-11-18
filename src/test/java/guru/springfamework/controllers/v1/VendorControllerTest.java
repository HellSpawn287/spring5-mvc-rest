package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.api.v1.model.VendorListDTO;
import guru.springfamework.service.VendorService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static guru.springfamework.controllers.v1.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {VendorController.class})
public class VendorControllerTest {
    public static final String NAME = "John's News";

    @MockBean
    VendorService vendorService;

    @Autowired
    MockMvc mockMvc;

    VendorDTO vendorDTO_1;
    VendorDTO vendorDTO_2;

    @Before
    public void setUp() throws Exception {
        vendorDTO_1 = new VendorDTO(NAME + " 1", VendorController.BASE_URL + "/1");
        vendorDTO_2 = new VendorDTO(NAME + " 2", VendorController.BASE_URL + "/2");
    }

    @Test
    public void getAllVendors() throws Exception {
        VendorListDTO vendorListDTO = new VendorListDTO(Arrays.asList(vendorDTO_1, vendorDTO_2));

        given(vendorService.getAllVendors()).willReturn(vendorListDTO);

        mockMvc.perform(
                get(VendorController.BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(2)));
    }

    @Test
    public void getVendorByID() throws Exception {
        //when
        given(vendorService.getVendorById(anyLong())).willReturn(vendorDTO_1);

        //then

        mockMvc.perform(
                get("/api/v1/vendors/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(vendorDTO_1.getName())))
                .andExpect(jsonPath("$.vendor_url", equalTo("/api/v1/vendors/1")))
                .andExpect(jsonPath("$.vendor_url", equalTo(vendorDTO_1.getVendor_url())));
    }

    @Test
    public void createNewVendor() throws Exception {
        given(vendorService.createNewVendor(vendorDTO_1)).willReturn(vendorDTO_1);

        //then
        mockMvc.perform(
                post(VendorController.BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(vendorDTO_1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(NAME + " 1")))
                .andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL + "/1")))
                .andExpect(jsonPath("$.vendor_url", equalTo(vendorDTO_1.getVendor_url())));
    }

    @Test
    public void updateVendor() throws Exception {
        given(vendorService.saveVendorByDTO(anyLong(), any(VendorDTO.class))).willReturn(vendorDTO_1);
//        then
        mockMvc.perform(
                put(VendorController.BASE_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(vendorDTO_1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(vendorDTO_1.getName())))
                .andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL + "/1")))
                .andExpect(jsonPath("$.vendor_url", equalTo(vendorDTO_1.getVendor_url())));
    }

    @Test
    public void patchVendor() throws Exception {
        given(vendorService.patchVendor(anyLong(), any(VendorDTO.class))).willReturn(vendorDTO_2);

        mockMvc.perform(
                patch(VendorController.BASE_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(vendorDTO_2)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(vendorDTO_2.getName())))
                .andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL + "/2")))
                .andExpect(jsonPath("$.vendor_url", equalTo(vendorDTO_2.getVendor_url())));
    }

    @Test
    public void deleteVendorById() throws Exception {
        mockMvc.perform(
                delete(VendorController.BASE_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(vendorService).deleteVendorById(anyLong());
    }
}