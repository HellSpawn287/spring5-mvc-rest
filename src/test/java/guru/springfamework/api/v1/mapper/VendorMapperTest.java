package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VendorMapperTest {

    public static final String NAME = "someName";
    VendorMapper vendorMapper = VendorMapper.INSTANCE;

    @Test
    public void vendorToVendorDTO() {
//        given
        Vendor vendor = new Vendor();
        vendor.setName(NAME);
//        when
        VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);

//        then
        assertEquals(vendor.getName(), vendorDTO.getName());
    }

    @Test
    public void vendorDTO_toVendor() {
//        given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);

//        when
        Vendor vendor = vendorMapper.vendorDTO_toVendor(vendorDTO);

//        then
        assertEquals(vendorDTO.getName(), vendor.getName());
    }
}