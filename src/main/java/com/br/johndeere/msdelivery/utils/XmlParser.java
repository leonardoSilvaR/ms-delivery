package com.br.johndeere.msdelivery.utils;

import com.br.johndeere.msdelivery.exceptions.InternalServerError;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.util.Optional;

public class XmlParser {

    private XmlParser() {
    }

    public static Optional xmlUnmarshall(final String xmlString, final Class<? extends Object> objectToSerialize) {
        XmlMapper mapper = new XmlMapper();
        try {
            return Optional.ofNullable(mapper.readValue(xmlString, objectToSerialize));
        } catch (JsonProcessingException e) {
            throw new InternalServerError();
        }
    }
}
