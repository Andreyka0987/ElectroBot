package com.example.httpserverbasic.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class TelegramBot extends TelegramLongPollingBot {
    String key;

    public TelegramBot(String key) {
        this.key = key;
    }

    @Override
    public String getBotToken() {
        return key;
    }


    @Override
    public String getBotUsername() {
        return "electro_detector_bot";
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }





    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.hasMessage()){
            String userMessage = update.getMessage().getText();
            long userChatID = update.getMessage().getChatId();

        }


        if (update.hasCallbackQuery()) {
            long userChatID = update.getCallbackQuery().getMessage().getChatId();
            String callBackPrompt = update.getCallbackQuery().getData();
            if (callBackPrompt.equals("check_status")) {
                setButtons(userChatID);
            }
        }


    }



    public void setButtons(long userChatID) {
        try {
            InlineKeyboardButton checkStatusButton = new InlineKeyboardButton();
            checkStatusButton.setText("Перевірити статус");
            checkStatusButton.setCallbackData("check_status");

            List<List<InlineKeyboardButton>> keyBoard = new ArrayList<>();
            List<InlineKeyboardButton> rowOfButtons = new ArrayList<>();

            rowOfButtons.add(checkStatusButton);
            keyBoard.add(rowOfButtons);

            InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
            markup.setKeyboard(keyBoard);

            SendMessage message = new SendMessage();
            message.setReplyMarkup(markup);
            message.setChatId(userChatID);
            message.setText(statusUpdate(DataBase.getStatus(),userChatID));

            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }


    }


    public String statusUpdate(int status, long chatID){

        if (status == 1){
            System.out.println("Status: true");
            return  "Електроенергія ✅";
        }
        if (status == 0){
            System.out.println("Status: false");
            return  "Електроенергія ❌";
        }

        System.err.println("Status: exception");
        return "Щось пішло не так!";
    }



    public void send(long chatID, String message){
        SendMessage messageToSend = new SendMessage();
        messageToSend.setChatId(chatID);
        messageToSend.setText(message);

        try {
            execute(messageToSend);
        } catch (TelegramApiException e) {
            System.out.println("MethodSendException: "+ message);
            e.printStackTrace();
        }
    }



}

