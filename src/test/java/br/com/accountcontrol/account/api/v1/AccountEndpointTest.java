package br.com.accountcontrol.account.api.v1;

import br.com.accountcontrol.account.builder.AccountBuilder;
import br.com.accountcontrol.account.dto.AccountCreateDTO;
import br.com.accountcontrol.account.dto.AccountReturnDTO;
import br.com.accountcontrol.account.service.AccountService;
import br.com.accountcontrol.api.AbstractRestControllerTest;
import br.com.accountcontrol.handler.RestExceptionHandler;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AccountEndpointTest extends AbstractRestControllerTest {

    @Mock
    private AccountService service;

    @InjectMocks
    private AccountEndpoint endpoint;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders
                .standaloneSetup(endpoint)
                .setControllerAdvice(new RestExceptionHandler())
                .setCustomArgumentResolvers(
                        new PageableHandlerMethodArgumentResolver(
                                new SortHandlerMethodArgumentResolver()
                        )
                )
                .build();
    }

    @Test
    public void saveAccountShouldReturnStatusCodeCreated() throws Exception {
        AccountCreateDTO accountCreateDto = AccountBuilder.ACCOUNT_CREATE_DTO;
        AccountReturnDTO accountReturnDto = AccountBuilder.ACCOUNT_RETURN_DTO;

        when(service.save(accountCreateDto)).thenReturn(accountReturnDto);

        mockMvc.perform(post(AccountEndpoint.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(accountCreateDto))
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description", is(accountReturnDto.getDescription())))
                .andExpect(jsonPath("$.balance").value("20"));
    }

    @Test
    public void saveInvalidAccountShouldReturnStatusCodeBadRequest() throws Exception {
        mockMvc.perform(post(AccountEndpoint.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(AccountReturnDTO.builder().build()))
        )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", equalTo("Field Validation Errors")))
                .andExpect(jsonPath("$.detail", equalTo("Field Validation Errors")))
                .andExpect(jsonPath("$.developerMessage",
                        equalTo("org.springframework.web.bind.MethodArgumentNotValidException")))
                .andExpect(jsonPath("$.date", notNullValue()))
                .andExpect(jsonPath("$.fieldErrors", hasSize(2)))
                .andExpect(jsonPath("$.fieldErrors[*].field", containsInAnyOrder(
                        "description", "balance")))
                .andExpect(jsonPath("$.fieldErrors[*].message", containsInAnyOrder(
                        "The description not be empty", "The balance not be empty")))
                .andExpect(jsonPath("$.fieldErrors[*].code", containsInAnyOrder(
                        "NotBlank", "NotNull")));
    }
}
