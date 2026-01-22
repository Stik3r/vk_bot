package com.pets.news_bot.service;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.queries.messages.MessagesGetLongPollHistoryQuery;

import java.util.List;
import java.util.Random;

public class VKService {

    public void connect() throws ClientException, ApiException, InterruptedException {
        TransportClient transportClient = new HttpTransportClient();
        VkApiClient vk = new VkApiClient(transportClient);
        long groupId = 235352264L;
        String accessToken = "vk1.a.a6vCrWzT9zO-2NZTRpxi-Lr06e_AU25T3E1fqlHxn6JPeQpgbmu3Jz5rQnVKScfxwyormLL_DbK-lU4NIHMNkxU-FY0LIW0jVE-5b55aAkg_BJKejAEkN74liSzeSIQSwSEZ6jU-NA2vdZcB788YP-AuasL1dzt-YLPAc5GBsjrJaRfjrUlEeLI9q3sQIf2-XwatWlXeAwMZiiR9lOVvVw";
        GroupActor actor = new GroupActor(groupId, accessToken);

        Integer ts = vk.messages().getLongPollServer(actor).execute().getTs();

        while (true) {
            // Запрос обновлений
            MessagesGetLongPollHistoryQuery historyQuery = vk.messages().getLongPollHistory(actor).ts(ts);
            var messages1 = vk.messages().getConversations(actor).execute();
            List<Message> messages = historyQuery.execute().getMessages().getItems();

            if (!messages.isEmpty()) {
                for (Message message : messages) {
                    System.out.println("Новое сообщение: " + message.getText());

                    // Отвечаем пользователю (Эхо)
                    vk.messages().sendUserIds(actor)
                            .message("Вы написали: " + message.getText())
                            .userId(message.getFromId())
                            .randomId(new Random().nextInt(10000))
                            .execute();
                }
            }

            // Обновляем метку времени ts для следующего запроса
            ts = vk.messages().getLongPollServer(actor).execute().getTs();
            Thread.sleep(500); // Небольшая пауза, чтобы не спамить запросами
        }
    }
}
