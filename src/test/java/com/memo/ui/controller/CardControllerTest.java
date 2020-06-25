package com.memo.ui.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import com.memo.ui.UiApplication;
import com.memo.ui.dao.CardDao;
import com.memo.ui.domain.Card;
import com.memo.ui.domain.CardList;
import com.memo.ui.test.TestCard;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(classes = UiApplication.class)
@AutoConfigureMockMvc
public class CardControllerTest {

    @MockBean
    private CardDao cardDao;

    @Autowired
    private TestCard testCard;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testList() throws Exception {
        CardList findResult = new CardList();
        List<Card> cards = new ArrayList<>();
        cards.add(testCard.create());
        findResult.setCards(cards);
        Mockito.doReturn(findResult).when(cardDao).find();

        mockMvc.perform(MockMvcRequestBuilders
                .get("/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("list"))
                .andExpect(model().attribute("cards", cards));
        Mockito.verify(cardDao, Mockito.times(1)).find();
    }

}
