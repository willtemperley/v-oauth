package org.vaadin.addons.guice.server;

import org.vaadin.addons.guice.AppUI;
import org.vaadin.addons.guice.ViewModule;
import org.vaadin.addons.guice.uiscope.UIScopeModule;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class GuiceContextListener extends GuiceServletContextListener {
	@Override
	protected Injector getInjector() {
		return Guice.createInjector(new ExampleGuiceServletModule(), new UIScopeModule(AppUI.class), new ViewModule());
	}
}
