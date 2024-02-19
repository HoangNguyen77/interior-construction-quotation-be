package com.swp.spring.interiorconstructionquotation.service.email;

public interface IEmailSerivce {
    public void sendEmail(String from, String to, String subject, String text);
}
