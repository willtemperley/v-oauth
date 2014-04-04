package org.vaadin.addons.guice;

import java.util.Map;
import java.util.Set;


import com.google.inject.Inject;
import com.google.inject.Provider;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewProvider;

/**
 * The guiced-up version of the Vaadin view
 * 
 * TODO 
 * 
 * @author Will Temperley
 */
public class GuicedViewProvider implements ViewProvider {
    
	

	private Map<String, Provider<View>> viewMapping;
	private Set<String> keys;
	
	@Inject
	public GuicedViewProvider(Map<String, Provider<View>> viewMapping) {
		this.viewMapping = viewMapping;
		this.keys = viewMapping.keySet();
	}

	public String getViewName(String viewAndParameters) {
		
		for (String key : keys) {
			if (viewAndParameters.startsWith(key)) {
				return key;
			}
		}
		return "A";
	}

	public View getView(String viewName) {
		
		Provider<View> provider = viewMapping.get(viewName);
		if (provider != null) {
			return provider.get();
		}

		return viewMapping.get("A").get();
		
	}

}
