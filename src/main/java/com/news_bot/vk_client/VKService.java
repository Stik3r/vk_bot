package com.news_bot.vk_client;

import com.news_bot.models.exception.SendMessageException;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VKService {

    private final VkApiClient vk;
    private final GroupActor actor;
    private final long userId;

    public VKService(String accessToken, long groupId, long userId){
        TransportClient transportClient = new HttpTransportClient();
        vk = new VkApiClient(transportClient);
        actor = new GroupActor(groupId, accessToken);
        this.userId = userId;
    }

    public void sendMessage(String message){
        try {
            vk.messages().sendDeprecated(actor).randomId(0).userId(userId).message(message).execute();
        } catch (ApiException | ClientException e) {
            log.error(e.getMessage());
            throw new SendMessageException("Ошибка отправки сообщения", e);
        }
    }
}
