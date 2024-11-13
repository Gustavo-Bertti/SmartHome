package br.com.fiap.smarthome.report.reportAI;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.stereotype.Service;

@Service
public class ReportAIService {

    private final ChatClient chatClient;

    public ReportAIService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder
                .defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()))
                .build();
    }

    public String sendChatMessage(String message){
        return chatClient
                .prompt()
                .user(String.valueOf(message))
                .system("""
                        Você é gestor de energia.
                        Você deve falar resumidamente sobre o uso da energia.
                        Responda sempre de forma objetiva, pouco texto e de maneira eficiente.
                        Não responda listando tópicos.
                       """)
                .call()
                .content();
    }

}
