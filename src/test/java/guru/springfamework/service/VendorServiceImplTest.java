package guru.springfamework.service;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.api.v1.model.VendorListDTO;
import guru.springfamework.controllers.v1.VendorController;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class VendorServiceImplTest {
    public static final Long VENDOR_ID = 2L;
    public static final String NAME = "Peter's Fruits&Veges";

    @Mock
    VendorRepository vendorRepository;
    VendorServiceImpl vendorService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        vendorService = new VendorServiceImpl(VendorMapper.INSTANCE, vendorRepository);
    }

    @Test
    public void getAllVendors() {
//        given
        List<Vendor> vendors = Arrays.asList(new Vendor(), new Vendor(), new Vendor());
        when(vendorRepository.findAll()).thenReturn(vendors);

//        when
        VendorListDTO vendorDTOs = vendorService.getAllVendors();

//        then
        then(vendorRepository).should(times(1)).findAll();
        assertThat(vendorDTOs.getVendors().size(), is(equalTo(3)));
    }

    @Test
    public void getVendorById() throws Exception {
//        given
        Vendor vendor = getVendor1(NAME, VENDOR_ID);

        given(vendorRepository.findById(anyLong())).willReturn(Optional.of(vendor));

//        when
        VendorDTO vendorDTO = vendorService.getVendorById(VENDOR_ID);
//        then
        then(vendorRepository).should(times(1)).findById(anyLong());

        assertEquals(NAME, vendorDTO.getName());
    }

    @Test
    public void createNewVendor() {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("Peter's Fruits&Veges");

        Vendor savedVendor = getVendor1(vendorDTO.getName(), VENDOR_ID);

        given(vendorRepository.save(any(Vendor.class))).willReturn(savedVendor);

        VendorDTO savedDTO = vendorService.createNewVendor(vendorDTO);

        then(vendorRepository).should().save(any(Vendor.class));
        assertThat(vendorDTO.getName(), equalTo(savedDTO.getName()));
        assertThat(savedDTO.getVendor_url(), containsString("1"));
        assertEquals(VendorController.BASE_URL + "/" + VENDOR_ID, savedDTO.getVendor_url());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getVendorByIdNotFound() throws Exception {
        given(vendorRepository.findById(anyLong())).willReturn(Optional.empty());

        VendorDTO vendorDTO = vendorService.getVendorById(VENDOR_ID);

        then(vendorRepository).should(times(1)).findById(anyLong());
    }

    @Test
    public void saveVendorByDTO() {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);

        Vendor savedVendor = getVendor1(vendorDTO.getName(), VENDOR_ID);

        given(vendorRepository.save(any(Vendor.class))).willReturn(savedVendor);

        VendorDTO savedDTO = vendorService.saveVendorByDTO(VENDOR_ID, vendorDTO);

        then(vendorRepository).should().save(any(Vendor.class));
        assertThat(vendorDTO.getName(), equalTo(savedDTO.getName()));
        assertThat(savedDTO.getVendor_url(), containsString("2"));
        assertEquals(VendorController.BASE_URL + "/" + VENDOR_ID, savedDTO.getVendor_url());
    }

    @Test
    public void deleteVendorById() {
        Long id = 1L;

        vendorService.deleteVendorById(id);
        then(vendorRepository).should().deleteById(anyLong());
    }

    @Test
    public void patchVendor() {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("John's News");

        Vendor vendor = getVendor1("John's News", 1L);

        given(vendorRepository.findById(anyLong())).willReturn(Optional.of(vendor));
        given(vendorRepository.save(any(Vendor.class))).willReturn(vendor);

//        when
        VendorDTO savedDTO = vendorService.patchVendor(1L, vendorDTO);

        then(vendorRepository).should().save(any(Vendor.class));
        then(vendorRepository).should(times(1)).findById(anyLong());
        assertThat(savedDTO.getVendor_url(), containsString("1"));
    }

    private Vendor getVendor1(String s, long l) {
        Vendor vendor = new Vendor();
        vendor.setName(s);
        vendor.setId(l);
        return vendor;
    }
}