package com.example.feign.integration.restclient.soap;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;
import lombok.NoArgsConstructor;

//Unrecognized field "Body" (class com.example.feign.integration.restclient.soap.SoapResponse), not marked as ignorable
@Getter
@NoArgsConstructor
@JacksonXmlRootElement(localName = "Envelope")
public class SoapResponse {

    @JacksonXmlProperty(localName = "Body")
    private SoapBody soapBody;

    @Getter
    @NoArgsConstructor
    public static class SoapBody {

        @JacksonXmlProperty(localName = "NumberToWordsResponse")
        private NumberToWords numberToWords;
    }

    @Getter
    @NoArgsConstructor
    public static class NumberToWords {

        @JacksonXmlProperty(localName = "NumberToWordsResult")
        private String result;
    }
}
