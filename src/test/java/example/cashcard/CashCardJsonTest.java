package example.cashcard;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.json.JacksonTester;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

@JsonTest
class CashCardJsonTest {

	@Autowired
	private JacksonTester<CashCard[]> jsonList;

	private CashCard[] cashCards;

	@BeforeEach
	void setUp() {
		cashCards = Arrays.array(
				new CashCard(99L, 123.45),
				new CashCard(100L, 1.00),
				new CashCard(101L, 150.00));
	}

	@Test
	void cashCardListSerializationTest() throws IOException {
		assertThat(jsonList.write(cashCards)).isStrictlyEqualToJson("list.json");
	}

	@Test
	void cashCardListDeserializationTest() throws IOException {
		String expected = """
				[
				   { "id": 99, "amount": 123.45 },
				   { "id": 100, "amount": 1.00 },
				   { "id": 101, "amount": 150.00 }
				]
				""";
		assertThat(jsonList.parse(expected)).isEqualTo(cashCards);
	}
}