package com.br.johndeere.msdelivery.componet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class Dictionary {


    public static ResourceBundleMessageSource messageSource;

    @Autowired
    public Dictionary(ResourceBundleMessageSource messageSource) {
        Dictionary.messageSource = messageSource;
    }

    public static String getMessage(final String msgCode) {
        return getMessage(msgCode, null);
    }

    public static String getMessage(final String msgCode, final String... args) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(msgCode, args, locale);
    }
}
