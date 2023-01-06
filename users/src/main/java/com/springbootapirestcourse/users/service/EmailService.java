package com.springbootapirestcourse.users.service;

import com.springbootapirestcourse.users.shared.email.Message;

public interface EmailService {
    public void send(Message message);
}
