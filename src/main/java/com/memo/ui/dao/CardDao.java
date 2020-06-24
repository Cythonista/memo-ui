package com.memo.ui.dao;

import com.memo.ui.domain.CardList;
import com.memo.ui.domain.CardSelector;
import javax.smartcardio.Card;

public interface CardDao {
    CardList find(CardSelector cardSelector);
    Card get(Long cardId);
}