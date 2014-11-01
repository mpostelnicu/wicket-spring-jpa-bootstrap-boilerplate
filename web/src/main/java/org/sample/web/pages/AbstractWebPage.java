package org.sample.web.pages;

import javax.servlet.ServletContext;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;
import org.apache.wicket.util.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.agilecoders.wicket.core.Bootstrap;
import de.agilecoders.wicket.core.markup.html.bootstrap.behavior.BootstrapBaseBehavior;
import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationMessage;
import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationPanel;
import de.agilecoders.wicket.core.settings.IBootstrapSettings;
import de.agilecoders.wicket.core.settings.ITheme;
import de.agilecoders.wicket.webjars.request.resource.WebjarsCssResourceReference;


/**
 * @author mpostelnicu
 * 
 */
public abstract class AbstractWebPage extends WebPage {

	private static final long serialVersionUID = 1L;

	private static final String LOCALE_REQUEST_PARAMETER_NAME = "locale";
	
	public static final String PARAM_HEADLESS="headless";
	
	protected boolean headless=false;

    protected final Logger log = LoggerFactory.getLogger(getClass());

    protected ServletContext getServletContext() {
        return ((WebApplication)getApplication()).getServletContext();
    }

    public AbstractWebPage() {
		// TODO Auto-generated constructor stub
	}
    
    public AbstractWebPage(PageParameters parameters) {
    	super(parameters);
		if (!parameters.get(PARAM_HEADLESS).isNull() && parameters.get(PARAM_HEADLESS).toOptionalBoolean())
			headless=true;

    }
    
    protected NotificationPanel createFeedbackPanel() {
    	 NotificationPanel notificationPanel = new NotificationPanel("feedback");
    	 notificationPanel.setOutputMarkupId(true);
        return notificationPanel;
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        Bootstrap.renderHead(response);
        addCommonStyles(response);
        addCommonScripts(response);
        addSelect2Fix(response);
        addFontAwesome(response);
    }
    


    private void addFontAwesome(IHeaderResponse response) {
        response.render(CssHeaderItem.forReference(
                new WebjarsCssResourceReference("font-awesome/current/css/font-awesome.min.css")));
    }


    private void addCommonStyles(IHeaderResponse response) {
        response.render(CssHeaderItem.forUrl("css/style.css"));
    }

    private void addSelect2Fix(IHeaderResponse response) {
        response.render(CssHeaderItem.forUrl("css/select2-bootstrap.css"));
    }
    


    
    private void addCommonScripts(IHeaderResponse response) {
      //  response.render(JavaScriptHeaderItem.forUrl("scripts/script.js"));
    }

    @Override
    protected void onConfigure() {
        super.onConfigure();
        changeLocaleIfRequestedByRequestParameter();
        configureTheme(getPageParameters());
        //add(new HeaderResponseContainer("footer-container", "footer-container"));     
        add(new BootstrapBaseBehavior());
    }
    
    private void configureTheme(PageParameters pageParameters) {
        StringValue theme = pageParameters.get("theme");

        if (!theme.isEmpty()) {
            IBootstrapSettings settings = Bootstrap.getSettings(getApplication());
            settings.getActiveThemeProvider().setActiveTheme(theme.toString(""));
        }
    }
    
    protected ITheme activeTheme() {
        IBootstrapSettings settings = Bootstrap.getSettings(getApplication());

        return settings.getActiveThemeProvider().getActiveTheme();
    }



    private void changeLocaleIfRequestedByRequestParameter() {
        IRequestParameters queryParameters = getRequest().getQueryParameters();
        if (queryParameters.getParameterNames().contains(LOCALE_REQUEST_PARAMETER_NAME)) {
            //with "locale" GET parameter available LocaleFilter overrides getLocale() for request with that value
            getSession().setLocale(getRequest().getLocale());
        }
    }

    protected NotificationMessage createDefaultInfoNotificationMessage(IModel<String> messageModel) {
        return new NotificationMessage(messageModel)
                .hideAfter(Duration.seconds(5));
    }

    protected Label createPageTitleTag(String resourceKey) {
        Label label = new Label("pageTitle", new ResourceModel(resourceKey));
        label.setVisible(!headless);
		return label;
    }

    protected Label createPageHeading(String resourceKey) {
        Label label = new Label("pageHeading", new ResourceModel(resourceKey));
        label.setVisible(!headless);
		return label;
    }

    protected Label createPageMessage(String resourceKey) {
        Label label = new Label("pageMessage", new ResourceModel(resourceKey));
        label.setVisible(!headless);
        label.setEscapeModelStrings(false);
        return label;
    }
 

 
}
