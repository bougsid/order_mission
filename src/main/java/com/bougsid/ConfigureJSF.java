package com.bougsid;

import com.sun.faces.config.FacesInitializer;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ayoub on 6/23/2016.
 */
@Configuration
public class ConfigureJSF {

    @Bean
    public ServletRegistrationBean facesServletRegistration() {

        ServletRegistrationBean servletRegistrationBean = new JsfServletRegistrationBean();

        return servletRegistrationBean;
    }

    public class JsfServletRegistrationBean extends ServletRegistrationBean {

        public JsfServletRegistrationBean() {
            super();
        }

        @Override
        public void onStartup(ServletContext servletContext)
                throws ServletException {

            FacesInitializer facesInitializer = new FacesInitializer();

            Set<Class<?>> clazz = new HashSet<Class<?>>();
            clazz.add(ConfigureJSF.class);
            facesInitializer.onStartup(clazz, servletContext);
        }
    }
}
