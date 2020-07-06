package com.memo.ui.controller;

import com.memo.ui.dao.CardDao;
import com.memo.ui.domain.Card;
import com.memo.ui.domain.CardList;
import com.memo.ui.domain.CardSelector;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/v1")
public class CardController {
    private final CardDao cardDao;

    public CardController(CardDao cardDao) {
        this.cardDao = cardDao;
    }

    @GetMapping(path="/list")
    public String list(Model model) {
        CardList cardList = this.cardDao.find();
        model.addAttribute("cards", cardList.getCards());
        model.addAttribute("alertString1", "alertString1_val");
        return "list";
    }

    @GetMapping(path="/add")
    public String add(@ModelAttribute Card card, Model model) {
        return "add";
    }
}
