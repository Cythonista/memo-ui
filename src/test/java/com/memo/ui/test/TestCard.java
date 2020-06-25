package com.memo.ui.test;

import com.memo.ui.domain.Card;
import org.springframework.stereotype.Component;

@Component
public class TestCard {

    public Card create() {
        Card card = new Card();
        card.setCardId(1L);
        card.setCardName("カード1");
        card.setOverview("概要1");
        card.setDeleteFlag(false);
        return card;
    }
}
