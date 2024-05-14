package example.cashcard;

import org.springframework.data.annotation.Id;
import org.springframework.data.repository.CrudRepository;

interface CashCardRepository extends CrudRepository<CashCard, Long> {
    record CashCard(@Id Long id, Double amount) {}
}