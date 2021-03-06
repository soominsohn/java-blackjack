package blackjack.domain.card;

import static org.assertj.core.api.Assertions.assertThat;

import blackjack.domain.card.type.CardNumberType;
import blackjack.domain.card.type.CardShapeType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CardTest {
    @DisplayName("카드 생성 테스트")
    @Test
    void createCard() {
        String shape = "다이아몬드";
        String number = "A";
        Card card = new Card(CardShapeType.DIAMOND, CardNumberType.ACE);
        assertThat(card.toString()).isEqualTo(number + shape);
    }

    @DisplayName("카드 번호 반환 테스트")
    @Test
    void getCardNumber() {
        Card card = new Card(CardShapeType.DIAMOND, CardNumberType.ACE);
        assertThat(card.getCardNumber()).isEqualTo(CardNumberType.ACE);
    }
}
