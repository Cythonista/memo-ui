package com.memo.ui.controller;

import com.memo.ui.dao.CardDao;
import com.memo.ui.domain.CardList;
import com.memo.ui.domain.CardSelector;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class CardController {
    private final CardDao cardDao;

    public CardController(CardDao cardDao) {
        this.cardDao = cardDao;
    }

    @GetMapping(path="/list")
    public String list(Model model) {
        CardSelector cardSelector = new CardSelector();
        CardList cardList = this.cardDao.find(cardSelector);
        model.addAttribute("cards", cardList.getCards());
        return "list";
    }
}
