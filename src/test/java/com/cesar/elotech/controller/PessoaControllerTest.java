package com.cesar.elotech.controller;

import com.cesar.elotech.domain.Pessoa;
import com.cesar.elotech.service.PessoaService;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;

@WebMvcTest
public class PessoaControllerTest {

    @Autowired
    private PessoaController pessoaController;

    @MockBean
    private PessoaService pessoaService;

    @BeforeEach
    public void setUp() {
        standaloneSetup(this.pessoaController);
    }

    @Test
    public void findById_Ok() {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setNome("David Gilmour");
        pessoa.setCpf("13012331026");
        pessoa.setDataNascimento(LocalDate.of(1946, 03, 06));

        when(this.pessoaService.findById(1L))
                .thenReturn(pessoa);

        given().
                accept(ContentType.JSON)
                .when()
                .get("/pessoas/{id}", 1L)
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void findById_NotFound() {
        when(this.pessoaService.findById(-1L)).thenReturn(null);

        given()
                .accept(ContentType.JSON)
                .when()
                .get("/pessoas/{id}", -1L)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void insertPessoa_Ok() {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setNome("David Gilmour");
        pessoa.setCpf("13012331026");
        pessoa.setDataNascimento(LocalDate.of(1946, 03, 06));


        when(pessoaService.insert(pessoa)).thenReturn(pessoa);

        given()
                .contentType(ContentType.JSON)
                .body(pessoa)
                .when()
                .post("/pessoas")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", containsString("/pessoas/" + pessoa.getId()));
    }

    @Test
    public void insertPessoa_BadRequest() {
        Pessoa pessoa = new Pessoa();

        when(pessoaService.insert(pessoa)).thenReturn(pessoa);

        given()
                .contentType(ContentType.JSON)
                .body(pessoa)
                .when()
                .post("/pessoas")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void insertCpf_BadRequest() {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setNome("David Gilmour");
        pessoa.setCpf("1");
        pessoa.setDataNascimento(LocalDate.of(1946, 03, 06));


        when(pessoaService.insert(pessoa)).thenReturn(pessoa);

        given()
                .contentType(ContentType.JSON)
                .body(pessoa)
                .when()
                .post("/pessoas")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void insertDataNascimento_BadRequest() {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setNome("David Gilmour");
        pessoa.setCpf("1");
        pessoa.setDataNascimento(LocalDate.of(2024, 03, 06));

        when(pessoaService.insert(pessoa)).thenReturn(pessoa);

        given()
                .contentType(ContentType.JSON)
                .body(pessoa)
                .when()
                .post("/pessoas")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void updatePessoa_Ok() {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setNome("David Gilmour");
        pessoa.setCpf("13012331026");
        pessoa.setDataNascimento(LocalDate.of(1946, 03, 06));

        when(pessoaService.insert(pessoa)).thenReturn(pessoa);
        when(pessoaService.update(pessoa.getId(), pessoa)).thenReturn(pessoa);

        given()
                .contentType(ContentType.JSON)
                .body(pessoa)
                .when()
                .post("/pessoas")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", containsString("/pessoas/" + pessoa.getId()));

        pessoa.setNome("Novo Nome");

        given()
                .contentType(ContentType.JSON)
                .body(pessoa)
                .when()
                .put("/pessoas/{id}", pessoa.getId())
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void updatePessoa_BadRequest() {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setNome("David Gilmour");
        pessoa.setCpf("13012331026");
        pessoa.setDataNascimento(LocalDate.of(1946, 03, 06));

        when(pessoaService.insert(pessoa)).thenReturn(pessoa);
        when(pessoaService.update(pessoa.getId(), pessoa)).thenReturn(pessoa);

        given()
                .contentType(ContentType.JSON)
                .body(pessoa)
                .when()
                .post("/pessoas")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", containsString("/pessoas/" + pessoa.getId()));

        pessoa.setNome(null);

        given()
                .contentType(ContentType.JSON)
                .body(pessoa)
                .when()
                .put("/pessoas/{id}", pessoa.getId())
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void updateCpf_BadRequest() {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setNome("David Gilmour");
        pessoa.setCpf("13012331026");
        pessoa.setDataNascimento(LocalDate.of(1946, 03, 06));

        when(pessoaService.insert(pessoa)).thenReturn(pessoa);
        when(pessoaService.update(pessoa.getId(), pessoa)).thenReturn(pessoa);

        given()
                .contentType(ContentType.JSON)
                .body(pessoa)
                .when()
                .post("/pessoas")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", containsString("/pessoas/" + pessoa.getId()));

        pessoa.setCpf("12345678921");

        given()
                .contentType(ContentType.JSON)
                .body(pessoa)
                .when()
                .put("/pessoas/{id}", pessoa.getId())
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void updateDataNascimento_BadRequest() {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setNome("David Gilmour");
        pessoa.setCpf("13012331026");
        pessoa.setDataNascimento(LocalDate.of(1946, 03, 06));

        when(pessoaService.insert(pessoa)).thenReturn(pessoa);
        when(pessoaService.update(pessoa.getId(), pessoa)).thenReturn(pessoa);

        given()
                .contentType(ContentType.JSON)
                .body(pessoa)
                .when()
                .post("/pessoas")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", containsString("/pessoas/" + pessoa.getId()));

        pessoa.setDataNascimento(LocalDate.of(2024, 03, 06));

        given()
                .contentType(ContentType.JSON)
                .body(pessoa)
                .when()
                .put("/pessoas/{id}", pessoa.getId())
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void updatePessoa_MethodNotAllowed() {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setNome("David Gilmour");
        pessoa.setCpf("13012331026");
        pessoa.setDataNascimento(LocalDate.of(1946, 03, 06));

        when(pessoaService.insert(pessoa)).thenReturn(pessoa);
        when(pessoaService.update(pessoa.getId(), pessoa)).thenReturn(pessoa);

        given()
                .contentType(ContentType.JSON)
                .body(pessoa)
                .when()
                .post("/pessoas")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", containsString("/pessoas/" + pessoa.getId()));


        given()
                .contentType(ContentType.JSON)
                .body(pessoa)
                .when()
                .put("/pessoas/{id}/", pessoa.getId())
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deletePessoa_Ok() {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setNome("David Gilmour");
        pessoa.setCpf("13012331026");
        pessoa.setDataNascimento(LocalDate.of(1946, 03, 06));

        when(pessoaService.findById(pessoa.getId())).thenReturn(pessoa);

        given()
                .accept(ContentType.JSON)
                .when()
                .delete("/pessoas/{id}", pessoa.getId())
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void deletePessoa_NotFound() {
        Long id = -10L;
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .delete("/pessoas/{id}", id)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

}