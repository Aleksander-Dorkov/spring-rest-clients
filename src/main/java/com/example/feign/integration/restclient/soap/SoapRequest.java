package com.example.feign.integration.restclient.soap;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JacksonXmlRootElement(localName = "soap:Envelope")
public class SoapRequest {

    @JacksonXmlProperty(localName = "xmlns:soap", isAttribute = true)
    private final String soapNamespace = "http://schemas.xmlsoap.org/soap/envelope/";

    @JacksonXmlProperty(localName = "soap:Body")
    private SoapBody soapBody;

    @Getter
    @AllArgsConstructor
    public static class SoapBody {

        @JacksonXmlProperty(localName = "NumberToWords")
        private NumberToWords numberToWords;

    }

    @Getter
    @AllArgsConstructor
    public static class NumberToWords {

        @JacksonXmlProperty(localName = "xmlns", isAttribute = true)
        private final String soapNamespace = "http://www.dataaccess.com/webservicesserver/";

        @JacksonXmlProperty(localName = "ubiNum")
        private int ubiNum;

    }
}
