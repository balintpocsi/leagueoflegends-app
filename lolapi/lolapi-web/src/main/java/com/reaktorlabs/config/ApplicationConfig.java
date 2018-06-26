package com.reaktorlabs.config;

import com.reaktorlabs.resource.AuthResource;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("api")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> clazzez = new HashSet<>();
        clazzez.add(AuthResource.class);
        return Collections.unmodifiableSet(clazzez);
    }
    
}
