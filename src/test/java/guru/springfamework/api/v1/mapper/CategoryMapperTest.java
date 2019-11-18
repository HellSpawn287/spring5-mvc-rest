package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.domain.Category;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CategoryMapperTest {
    public static final String NAME = "someName";
    CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    @Test
    public void categoryToCategoryDTO() {
//        given
        Category category = new Category();
        category.setName(NAME);

//        when
        CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);

//        then
        assertEquals(category.getName(), categoryDTO.getName());

    }
}