package com.cesar.elotech.controller;

import com.cesar.elotech.domain.Contato;
import com.cesar.elotech.service.ContatoService;
import com.cesar.elotech.service.PessoaService;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

@WebMvcTest
public class ContatoControllerTest {

    @Autowired
    private ContatoController contatoController;

    @Autowired
    private PessoaController pessoaController;

    @MockBean
    private ContatoService contatoService;

    @MockBean
    private PessoaService pessoaService;

    @BeforeEach
    public void setUp() {
        standaloneSetup(this.contatoController, this.pessoaController);
    }

    @Test
    public void insertContato_Ok() {
        Contato contato = new Contato();
        contato.setId(1L);
        contato.setNome("Roger Waters");
        contato.setTelefone("1140028922");
        contato.setEmail("waters@gmail.com");


        when(contatoService.insert(contato)).thenReturn(contato);

        given()
                .contentType(ContentType.JSON)
                .body(contato)
                .when()
                .post("/contatos")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", containsString("/contatos/" + contato.getId()));
    }

    @Test
    public void findById_Ok() {
        Contato contato = new Contato();
        contato.setId(1L);
        contato.setNome("Roger Waters");
        contato.setTelefone("1140028922");
        contato.setEmail("waters@gmail.com");

        when(contatoService.findById(contato.getId())).thenReturn(contato);

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/contatos/{id}", contato.getId())
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(contato.getId().intValue()))
                .body("nome", equalTo(contato.getNome()))
                .body("telefone", equalTo(contato.getTelefone()))
                .body("email", equalTo(contato.getEmail()));
    }

    @Test
    public void FindById_NotFound() {
        when(this.contatoService.findById(-1L)).thenReturn(null);

        given()
                .accept(ContentType.JSON)
                .when()
                .get("/contatos/{id}", -10L)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void deleteContato_ok() {

        Contato contato = new Contato();
        contato.setId(1L);
        contato.setNome("Roger Waters");
        contato.setTelefone("1140028922");
        contato.setEmail("waters@gmail.com");

        when(contatoService.findById(contato.getId())).thenReturn(contato);

        given()
                .accept(ContentType.JSON)
                .when()
                .delete("/contatos/{id}", contato.getId())
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void deleteContato_notFound() {
        Long id = -1L;

        given()
                .accept(ContentType.JSON)
                .when()
                .delete("/contatos/{id}", id)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void updateContato_ok() {
        Contato contato = new Contato();
        contato.setId(1L);
        contato.setNome("Roger Waters");
        contato.setTelefone("1140028922");
        contato.setEmail("waters@gmail.com");

        when(contatoService.findById(contato.getId())).thenReturn(contato);

        contato.setNome("Novo Nome");

        given()
                .contentType(ContentType.JSON)
                .body(contato)
                .when()
                .put("/contatos/{id}", contato.getId())
                .then()
                .log().body()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void updateContato_BadRequest() {

        Contato contato = new Contato();
        contato.setId(1L);
        contato.setNome("Roger Waters");
        contato.setTelefone("1140028922");
        contato.setEmail("waters@gmail.com");

        when(contatoService.findById(contato.getId())).thenReturn(contato);

        contato.setEmail("email.invalido");

        given()
                .contentType(ContentType.JSON)
                .body(contato)
                .when()
                .put("/contatos/{id}", contato.getId())
                .then()
                .log().body()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}