package com.bougsid;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.http.HttpStatus;

/**
 * Created by ayoub on 7/29/2016.
 */
public class ServerCustomization extends ServerProperties {

    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {

        super.customize(container);
        container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND,
                "/404"));
        container.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR,
                "/404"));
        container.addErrorPages(new ErrorPage("/error"));
    }

}