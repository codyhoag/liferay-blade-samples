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

package com.liferay.blade.samples.portlet.ds;

import java.io.IOException;
import java.io.PrintWriter;

import javax.portlet.GenericPortlet;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

/**
 * Sample portlet using OSGi Declarative Services.
 * This portlet just shows the message "Hello World!". The properties for the Component
 * can be looked up in http://java.sun.com/xml/ns/portlet/portlet-app_2_0.xsd for the 
 * JSR-286 derived javax.portlet.* properties and http://www.liferay.com/dtd/liferay-portlet-app_7_0_0.dtd
 * and http://www.liferay.com/dtd/liferay-display_7_0_0.dtd for the Liferay-specific 
 * extensions to the JSR-286 standard. Map the xml structure to the properties names
 * similar to what you see in this sample. Authoritative documentation for the 
 * individual properties can be found in the comments of the linked DTDs.
 *
 * Multiple values, as seen here in javax.portlet.security-role-ref, are separated by comma.
 *
 * @author Liferay, Olaf Kock
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=DS Portlet",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class DSPortlet extends GenericPortlet {

	// TODO: Override any method from GenericPortlet as needed. 

	@Override
	protected void doView(RenderRequest request, RenderResponse response)
		throws IOException, PortletException {

		PrintWriter printWriter = response.getWriter();

		printWriter.print("DS Portlet - Hello World!");
	}

}