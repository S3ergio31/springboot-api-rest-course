package com.springbootapirestcourse.users.shared.email;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Message {
    private String sender;
    private String recipient;
    private String subject;
    private String bodyText;
    private String bodyHTML;
}
