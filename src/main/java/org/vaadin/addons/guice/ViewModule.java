package org.vaadin.addons.guice;


import org.vaadin.addons.guice.uiscope.UIScoped;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.MapBinder;
import com.vaadin.navigator.View;

public class ViewModule extends AbstractModule {

	
    protected MapBinder<String, View> mapbinder;
    
    @Override
    protected void configure() {

        mapbinder = MapBinder.newMapBinder(binder(), String.class, View.class);
        
        addBinding("A", ViewA.class);
        addBinding("B", ViewB.class);

    }


    protected void addBinding(String uriFragment, Class<? extends View> clazz) {
        mapbinder.addBinding(uriFragment).to(clazz).in(UIScoped.class);
    }

}