package example.cashcard;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;

import javax.swing.text.Document;

// import java.io.IOException;

// @JsonTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CashCardJsonTest {

	// @Autowired
	// private JacksonTester<CashCard> json;

	@Autowired
	TestRestTemplate restTemplate;

	// @Test
	// void cashCardSerializationTest() throws IOException {
	// CashCard cashCard = new CashCard(99L, 123.45);
	// assertThat(json.write(cashCard)).isStrictlyEqualToJson("expected.json");
	// assertThat(json.write(cashCard)).hasJsonPathNumberValue("@.id");
	// assertThat(json.write(cashCard)).extractingJsonPathNumberValue("@.id").isEqualTo(99);
	// assertThat(json.write(cashCard)).hasJsonPathNumberValue("@.amount");
	// assertThat(json.write(cashCard)).extractingJsonPathNumberValue("@.amount").isEqualTo(123.45);
	// }

	// @Test
	// void cashCardDeserializationTest() throws IOException {
	// String expected = """
	// {
	// "id":99,
	// "amount":123.45
	// }
	// """;
	// assertThat(json.parse(expected)).isEqualTo(new CashCard(99L, 123.45));
	// assertThat(json.parseObject(expected).id()).isEqualTo(99);
	// assertThat(json.parseObject(expected).amount()).isEqualTo(123.45);
	// }

	// @Test
	// void shouldReturnACashCardWhenDataIsSaved() {
	// ResponseEntity<String> response = restTemplate.getForEntity("/cashcards/99",
	// String.class);

	// assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	// DocumentContext documentContext = JsonPath.parse(response.getBody());
	// Number id = documentContext.read("$.id");
	// assertThat(id).isEqualTo(99);

	// Double amount = documentContext.read("$.amount");
	// assertThat(amount).isEqualTo(123.45);
	// }

	// @Test
	// void shouldNotReturnACashCardWithAnUnknownId() {
	// ResponseEntity<String> response =
	// restTemplate.getForEntity("/cashcards/1000", String.class);

	// assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	// assertThat(response.getBody()).isBlank();
	// }

	@Test
	void shouldCreateANewCashCard() {
		CashCard newCashCard = new CashCard(44L, 250.00);
		ResponseEntity<Void> createResponse = restTemplate.postForEntity("/cashcards", newCashCard, Void.class);
		assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

		URI locationOfNewCashCard = createResponse.getHeaders().getLocation();
		ResponseEntity<String> getResponse = restTemplate.getForEntity(locationOfNewCashCard, String.class);
		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
		Number id = documentContext.read("$.id");
		Double amount = documentContext.read("$.amount");

		assertThat(id).isNotNull();
		assertThat(amount).isEqualTo(250.00);
	}
}