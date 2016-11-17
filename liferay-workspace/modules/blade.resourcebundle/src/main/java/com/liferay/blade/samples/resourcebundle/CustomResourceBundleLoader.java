package com.liferay.blade.samples.resourcebundle;

import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.util.AggregateResourceBundleLoader;
import com.liferay.portal.kernel.util.CacheResourceBundleLoader;
import com.liferay.portal.kernel.util.ClassResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoader;

/**
 * Sample to override any of Liferay's module localization with new translations. Note that
 * this applies only to those localizations contained in individual modules (no matter if 
 * default modules or custom built), but not to those in portal-impl.jar (unless a module
 * specifically load its translations from Liferay's core.
 *
 * @see CustomResourceBundle in this sample project for changing Liferay's core translations.
 * @author Liferay, Olaf Kock
 */
 @Component(
		immediate = true,
		// TODO: Change the bundle.symbolic.name to the one of the module you'd like to override.
		// TODO: Change the servlet.context.name to the one of the module you'd like to override.
		// TODO: Documentation/sample for overriding several modules at once if that's good practice
		//       instead of introducing an individual CustomResourceBundleLoader for each module.
		property = {
			"bundle.symbolic.name=ADD_YOUR_MODULES_SYMBOLIC_NAME_HERE",
			"resource.bundle.base.name=content.Language",
			"servlet.context.name=WEB_CONTEXT_PATH_OF_THE_OVERRIDDEN_MODULE"
		}
	)
public class CustomResourceBundleLoader implements ResourceBundleLoader {

	@Override
	public ResourceBundle loadResourceBundle(String languageId) {
		return _resourceBundleLoader.loadResourceBundle(languageId);
	}

	// TODO: Change the bundle.symbolic.name to the on eof the module that you'd like to override
	@Reference(target = "(bundle.symbolic.name=ADD_YOUR_MODULES_SYMBOLIC_NAME_HERE)")
	public void setResourceBundleLoader(
		ResourceBundleLoader resourceBundleLoader) {

		_resourceBundleLoader = new AggregateResourceBundleLoader(
			new CacheResourceBundleLoader(
				new ClassResourceBundleLoader(
					"content.Language",
					CustomResourceBundleLoader.class.getClassLoader())),
			resourceBundleLoader);
	}

	private AggregateResourceBundleLoader _resourceBundleLoader;

}
