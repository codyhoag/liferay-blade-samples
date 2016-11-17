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

package com.liferay.blade.samples.resourcebundle;

import com.liferay.portal.kernel.language.UTF8Control;

import java.util.Enumeration;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;

/**
 * Sample to override Liferay's core localization with new translations. Note that
 * this applies only to those localizations contained in portal-impl.jar, not to
 * any modules (unless the modules specifically load their translations from Liferay's
 * core.
 *
 * @see CustomResourceBundleLoader in this sample project for changing module's translations.
 * @author Liferay, Olaf Kock
 */
 
@Component(
	immediate = true, 
	property = {"language.id=en_US"},
	service = ResourceBundle.class
)
public class CustomResourceBundle extends ResourceBundle {

	// Typically no changes required in this class. Note that the default implementation
	// utilizes Liferay's default encoding for Language.properties: UTF-8. You might need
	// to convince your IDE to accept properties files as UTF-8 or change the encoding
	// here.

	@Override
	public Enumeration<String> getKeys() {
		return _resourceBundle.getKeys();
	}

	@Override
	protected Object handleGetObject(String key) {
		return _resourceBundle.getObject(key);
	}

	private final ResourceBundle _resourceBundle = ResourceBundle.getBundle(
		"content.Language", UTF8Control.INSTANCE);

}