/**
 * Copyright 2000-present Liferay, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.liferay.blade.samples.corejsphook;

import com.liferay.portal.deploy.hot.CustomJspBag;
import com.liferay.portal.kernel.url.URLContainer;

import java.net.URL;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * Sample to override Liferay core/kernel JSPs by adding them to the META-INF/jsps directory 
 * in this module. If you change the directory, see the comments in this class for more 
 * required changes.
 * 
 * Typically you'll copy the original JSPs from Liferay into the named directory and modify 
 * them. Careful: This will never pick up any changes made to the underlying JSPs - in case 
 * there are any bugfixes or changes made in them. It's up to the maintainer of this JSP hook
 * to properly maintain and adapt to changes in the underlying JSP implementation.
 * 
 * In case you find a version of with extension *-ext.jsp of the page that you want to 
 * modify, it's preferred to make changes to this *-ext.jsp, as the original files are empty
 * and a lot easier to maintain.
 *
 * TODO: Inspect the comments in this class and the other template directories and work
 * according to them - delete the comments when you're done.
 *
 * @author Liferay, Olaf Kock
 */
@Component(
	immediate = true,
	// TODO: If you change the name of the class, update the context.id value as well.
	// Name your JSP Bag properly and adjust the ranking: The higher this value is,
	// the higher the implied importance of your change is. There is no "correct" 
	// ranking, this only helps Liferay to deal with potential conflicting overrides.
	// If everybody would choose MAX_INT, not a lot would be achieved. Be gentle
	property = {
		"context.id=BladeCustomJspBag", 
		"context.name=Blade Sample Custom JSP Bag",
		"service.ranking:Integer=100"
	}
)
public class BladeCustomJspBag implements CustomJspBag {

	@Override
	public String getCustomJspDir() {
		// TODO This value needs to reflect the path that your custom JSPs are deployed in this bundle.
		// Some documentation tells you to store them in META-INF/custom-jsps while this sample 
		// obviously decides for META-INF/jsps. 
		return "META-INF/jsps/";
	}

	/**
	 */
	
	@Override
	public List<String> getCustomJsps() {
		// The default implementation is typically ok - this gets initialized in activate(). 
		// If you change activate, you might want to look at this method as well, otherwise
		// you're fine to use the sample
		return _customJsps;
	}

	@Override
	public URLContainer getURLContainer() {
		// The default implementation is typically ok - see the embedded nested class below.
		return _urlContainer;
	}

	@Override
	public boolean isCustomJspGlobal() {
		// Hm - I didn't know Liferay still supported ApplicationAdapters - but seems like 
		// we do. That's the only thing I can make of this parameter: Set to false in order
		// to be able to configure this hook site by site. Careful: I've not 
		// tried/validated/tested this - it's just an expectation 
		return true;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		// Typically this boilerplate implementation of the activate method is ok and 
		// doesn't need any change.
		_bundle = bundleContext.getBundle();

		_customJsps = new ArrayList<>();

		Enumeration<URL> entries = _bundle.findEntries(
			getCustomJspDir(), "*.jsp", true);

		while (entries.hasMoreElements()) {
			URL url = entries.nextElement();

			_customJsps.add(url.getPath());
		}
	}

	private Bundle _bundle;
	private List<String> _customJsps;

	private final URLContainer _urlContainer = new URLContainer() {
		
		// Typically this boilerplate implementation is ok and doesn't need any change.
		
		@Override
		public URL getResource(String name) {
			return _bundle.getEntry(name);
		}

		@Override
		public Set<String> getResources(String path) {
			Set<String> paths = new HashSet<>();

			for (String entry : _customJsps) {
				if (entry.startsWith(path)) {
					paths.add(entry);
				}
			}

			return paths;
		}

	};

}