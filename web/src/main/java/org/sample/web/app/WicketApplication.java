package org.sample.web.app;

import org.apache.wicket.Page;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.util.time.Duration;
import org.sample.web.pages.AbstractWebPage;
import org.sample.web.pages.HomePage;
import org.sample.web.pages.Login;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.wicketstuff.annotation.scan.AnnotatedMountScanner;

import de.agilecoders.wicket.core.Bootstrap;
import de.agilecoders.wicket.core.settings.BootstrapSettings;
import de.agilecoders.wicket.core.settings.ThemeProvider;
import de.agilecoders.wicket.themes.markup.html.bootstrap3.Bootstrap3Theme;
import de.agilecoders.wicket.themes.markup.html.google.GoogleTheme;
import de.agilecoders.wicket.themes.markup.html.metro.MetroTheme;
import de.agilecoders.wicket.themes.markup.html.wicket.WicketTheme;
import de.agilecoders.wicket.themes.settings.BootswatchThemeProvider;
import de.agilecoders.wicket.webjars.collectors.VfsJarAssetPathCollector;
import de.agilecoders.wicket.webjars.util.WicketWebjars;

/**
 * From AppFuse Wicket Frontend Application class.
 * 
 * @author Marcin Zajączkowski, 2010-09-02
 * @author mpostelnicu
 */
@Component
public class WicketApplication extends AuthenticatedWebApplication {

	private static final String BASE_PACKAGE_FOR_PAGES =  AbstractWebPage.class.getPackage().getName();
	private static final String DEFAULT_ENCODING = "UTF-8";

	protected final Logger log = LoggerFactory.getLogger(getClass());

	@Override
	protected void init() {
		super.init();
		registerSpringComponentInjector();
		initPageMounting();
		initBootstrap();
		initWebjars();

		getMarkupSettings().setDefaultMarkupEncoding(DEFAULT_ENCODING);
		getRequestCycleSettings().setResponseRequestEncoding(DEFAULT_ENCODING);

		if (getConfigurationType().equals(RuntimeConfigurationType.DEVELOPMENT)) {
			getMarkupSettings().setStripWicketTags(true);
			getMarkupSettings().setStripComments(true);
			getMarkupSettings().setCompressWhitespace(true);

			getDebugSettings().setAjaxDebugModeEnabled(true);
			getDebugSettings().setComponentUseCheck(true);
			getDebugSettings().setDevelopmentUtilitiesEnabled(true);

			getResourceSettings().setResourcePollFrequency(Duration.ONE_SECOND);
		}
	}

	private void initWebjars() {
		WicketWebjars.install(this);
        WicketWebjars.registerCollector(new VfsJarAssetPathCollector());
	}

	private void registerSpringComponentInjector() {
		getComponentInstantiationListeners().add(new SpringComponentInjector(this, getContext(), true));
	}

	private void initPageMounting() {
		// Hint from:
		// http://blog.xebia.com/2008/10/09/readable-url%E2%80%99s-in-wicket-an-introduction-to-wicketstuff-annotation/
		new AnnotatedMountScanner().scanPackage(BASE_PACKAGE_FOR_PAGES).mount(this);
	}


	private void initBootstrap() {		
		final ThemeProvider themeProvider = new BootswatchThemeProvider() {
			{
				add(new MetroTheme());
				add(new GoogleTheme());
				add(new WicketTheme());
				add(new Bootstrap3Theme());
				defaultTheme("united");
			}
		};
		BootstrapSettings settings = new BootstrapSettings();
		settings.setJsResourceFilterName("footer-container")
        .setThemeProvider(themeProvider);
		Bootstrap.install(this, settings);
	}

	@Override
	public Class<? extends Page> getHomePage() {
		return HomePage.class;
	}

	@Override
	protected Class<? extends AuthenticatedWebSession> getWebSessionClass() {
		return SSAuthenticatedWebSession.class;
	}

	@Override
	protected Class<? extends WebPage> getSignInPageClass() {
		return Login.class;
	}

	protected ApplicationContext getContext() {
		return WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
	}
}
