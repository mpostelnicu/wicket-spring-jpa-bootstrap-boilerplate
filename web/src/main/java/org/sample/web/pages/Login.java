package org.sample.web.pages;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.validation.FormComponentFeedbackBorder;
import org.apache.wicket.model.Model;
import org.sample.web.app.SSAuthenticatedWebSession;
import org.sample.web.components.PlaceholderBehavior;
import org.sample.web.components.RequiredBehavior;
import org.wicketstuff.annotation.mount.MountPath;

import de.agilecoders.wicket.core.markup.html.bootstrap.form.BootstrapForm;


/**
 * @author mpostelnicu
 * 
 */
@MountPath("/login")
public class Login extends HeaderFooter {

	private static final long serialVersionUID = 1L;


	class LoginForm extends BootstrapForm<Void> { 
  
    	private static final long serialVersionUID = 1L;
    	
    	private TextField<String> usernameField;
    	private TextField<String> passwordField;
    	 	
    	public void createAndAddToLoginFormUsernameAndPasswordFields() {
    	        add(new FormComponentFeedbackBorder("border").add(
    	                (usernameField = new RequiredTextField<String>("username", Model.of("")))));
    	        usernameField.add(new PlaceholderBehavior(getString("label.username")));
    	        usernameField.add(new RequiredBehavior());
    	        add(passwordField = new PasswordTextField("password", Model.of("")));
    	        passwordField.add(new PlaceholderBehavior(getString("label.password")));
    	        passwordField.add(new RequiredBehavior());
    	    }
    	  public LoginForm(String id) {
              super(id);
              IndicatingAjaxButton submit = new IndicatingAjaxButton("submit", Model.of("Submit")) {

				private static final long serialVersionUID = 1L;

				@Override
                  protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                       SSAuthenticatedWebSession session = SSAuthenticatedWebSession.getSSAuthenticatedWebSession();
                      if (session.signIn(LoginForm.this.usernameField.getModelObject(), LoginForm.this.passwordField.getModelObject())) {
                         setResponsePage(getApplication().getHomePage());
                      } else {
                          target.add(LoginForm.this);
                      }
                  }

                  @Override
                  protected void onError(AjaxRequestTarget target, Form<?> form) {
                      target.add(LoginForm.this);
                  }
              };
              add(submit);
          }
    }
    

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(createPageTitleTag("login.title"));
        add(createPageHeading("login.heading"));
        add(createPageMessage("login.message"));

        LoginForm loginForm = new LoginForm("loginForm");
        add(loginForm);
        loginForm.createAndAddToLoginFormUsernameAndPasswordFields();
    }

}
