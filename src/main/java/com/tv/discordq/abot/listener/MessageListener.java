package com.tv.discordq.abot.listener;

import com.tv.discordq.abot.service.GeminiService;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;


@Component
public class MessageListener extends ListenerAdapter {


    private final GeminiService geminiService;

    public MessageListener(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    private List<String> splitMessage(String message) {
        List<String> parts = new ArrayList<>();
        int maxLength = 1990; //

        while (message.length() > maxLength) {
            int splitAt = maxLength;

            int lastNewLine = message.lastIndexOf("\n", maxLength);
            int lastSpace = message.lastIndexOf(" ", maxLength);

            if (lastNewLine > 0) {
                splitAt = lastNewLine;
            } else if (lastSpace > 0) {
                splitAt = lastSpace;
            }

            parts.add(message.substring(0, splitAt).trim());
            message = message.substring(splitAt).trim();
        }


        if (!message.isEmpty()) {
            parts.add(message);
        }

        return parts;
    }


    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        System.out.println("Received message: " + event.getMessage().getContentRaw());

        if (event.getAuthor().isBot()) return;

        String message = event.getMessage().getContentRaw();

        if (message.startsWith("!ask")) {
            String question = message.replace("!ask", "").trim();

            // Reply immediately
            event.getChannel().sendMessage("Thinking... 🤔").queue(sentMessage -> {
                // Run in a separate thread
                CompletableFuture.runAsync(() -> {
                    String answer = geminiService.getAnswer(question);
                    List<String> answerChunks = splitMessage(answer);

                    // Edit the first message or send new ones if the response is long
                    if (answerChunks.size() == 1) {
                        sentMessage.editMessage(answerChunks.get(0)).queue();
                    } else {
                        sentMessage.editMessage(answerChunks.get(0)).queue();
                        for (int i = 1; i < answerChunks.size(); i++) {
                            event.getChannel().sendMessage(answerChunks.get(i)).queue();
                        }
                    }
                });
            });
        }
    }



}
