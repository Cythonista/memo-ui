package com.memo.ui.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import org.mockito.ArgumentMatcher;
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

    @Test
    public void testAddGet() throws Exception {
        // when, then: テスト対象メソッド実行
        mockMvc.perform(MockMvcRequestBuilders.get("/add")
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("add"));
    }

    @Test
    public void testAddPost() throws Exception {
        Card card = testCard.create();

        ArgumentMatcher<Card> matcher = argument -> {
          assertEquals(card.getCardName(), argument.getCardName());
          assertEquals(card.getOverview(), argument.getOverview());
          return true;
        };
        Mockito.doNothing().when(cardDao).add(Mockito.argThat(matcher));

        mockMvc.perform(MockMvcRequestBuilders.post("/add")
                        .param("cardName", card.getCardName())
                        .param("overview", card.getOverview())
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(view().name("redirect:/list"));

        Mockito.verify(cardDao, Mockito.times(1)).add(Mockito.argThat(matcher));
    }

    @Test
    public void testEditGet() throws Exception {
        Card card = testCard.create();
        ArgumentMatcher<Long> cardId = argument -> {
            assertEquals(card.getCardId(), argument);
            return true;
        };
        Mockito.doReturn(card).when(cardDao).get(Mockito.argThat(cardId));

        mockMvc.perform(MockMvcRequestBuilders.get("/edit")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .param("cardId", card.getCardId().toString()))
                .andExpect(view().name("edit"))
                .andExpect(model().attribute("card", card));
        Mockito.verify(cardDao, Mockito.times(1)).get(Mockito.argThat(cardId));
    }

    @Test
    public void testEditPost() throws Exception {
        Card card = testCard.create();
        ArgumentMatcher<Card> matcher = argument -> {
            assertEquals(card.getCardId(), argument.getCardId());
            assertEquals(card.getCardName(), argument.getCardName());
            assertEquals(card.getOverview(), argument.getOverview());
            return true;
        };
        Mockito.doNothing().when(cardDao).set(Mockito.argThat(matcher));
        mockMvc.perform(
                MockMvcRequestBuilders.post("/edit")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("cardId", card.getCardId().toString())
                        .param("cardName", card.getCardName())
                        .param("overview", card.getOverview()))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(view().name("redirect:/list"));

        Mockito.verify(cardDao, Mockito.times(1)).set(Mockito.argThat(matcher));
    }

}
