package br.com.accountcontrol.category.api.v1;

import br.com.accountcontrol.category.builder.CategoryBuilder;
import br.com.accountcontrol.category.dto.CategoryCreateDTO;
import br.com.accountcontrol.category.dto.CategoryReturnDTO;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
public class CategoryEndpointIT {

    @Autowired
    private TestRestTemplate restTemplate;

    private HttpEntity<Void> header;

    @Before
    public void configHeaders() {
        String str = "{\"username\":\"luiz.reis\",\"password\":123}";
        HttpHeaders headers = restTemplate.postForEntity("/login", str, String.class).getHeaders();
        header = new HttpEntity<>(headers);
    }

    @Test
    @DatabaseSetup("/data/categoryEndpoint/data.xml")
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT, value = "/data/categoryEndpoint/dataExpected.xml")
    @DatabaseTearDown(value = "/data/tearDown.xml")
    public void saveCategoryShouldReturnStatusCodeCreated() {
        CategoryCreateDTO createDTO = CategoryBuilder.CATEGORY_CREATE_DTO;

        ResponseEntity<CategoryReturnDTO> entity =
                restTemplate.postForEntity(CategoryEndpoint.BASE_URL, new HttpEntity<>(createDTO, header.getHeaders()), CategoryReturnDTO.class);

        Assert.assertThat(entity.getStatusCode(), equalTo(HttpStatus.CREATED));
    }
}
